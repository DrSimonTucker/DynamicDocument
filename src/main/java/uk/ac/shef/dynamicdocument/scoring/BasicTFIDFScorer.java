package uk.ac.shef.dynamicdocument.scoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import uk.ac.shef.dynamicdocument.DocumentTree;
import uk.ac.shef.dynamicdocument.DocumentWord;
import uk.ac.shef.importance.tfidf.StopWord;

public class BasicTFIDFScorer implements Scorer
{
	Collection<DocumentTree> corpus;

	Map<String, Double> idfScore = new TreeMap<String, Double>();

	String SEP = "~~";

	Stemmer stemmer = new BasicStemmer();

	StopWord sw;

	Map<String, Double> tfScore = new TreeMap<String, Double>();

	// SnowballStemmer stemmer = new englishStemmer();

	public BasicTFIDFScorer(Collection<DocumentTree> corpus)
	{
		this.corpus = corpus;
		buildIDFMap();
	}

	public BasicTFIDFScorer(InputStream is) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		for (String line = reader.readLine(); line != null; line = reader
				.readLine())
		{
			String[] elems = line.trim().split(SEP);
			idfScore.put(elems[0], Double.parseDouble(elems[1]));
		}
		reader.close();
	}

	protected void buildIDFMap()
	{
		if (corpus != null)
		{

			// Clear the previous IDF Map
			idfScore.clear();

			// This stores a temporary IDF count - from this we can compute the
			// IDF
			// score
			Map<String, Double> counts = new TreeMap<String, Double>();

			for (DocumentTree doc : corpus)
			{
				Set<String> uniqueAndRelevantWords = new TreeSet<String>();

				for (DocumentWord word : doc.deriveWords())
					if (word.shouldScore()
							&& !sw.isStopWord(word.getScoreWord()))
					{
						String text = word.getScoreWord();
						if (stemmer != null)
							text = stemmer.stem(word.getScoreWord());
						if (!uniqueAndRelevantWords.contains(text))
							uniqueAndRelevantWords.add(text);
					}

				// Update the IDF values
				for (String word : uniqueAndRelevantWords)
					if (!counts.containsKey(word))
						counts.put(word, 1.0);
					else
						counts.put(word, counts.get(word) + 1.0);
			}

			for (Entry<String, Double> entry : counts.entrySet())
				idfScore.put(entry.getKey(), computeIDFScore(entry.getValue(),
						corpus.size()));
		}
	}

	protected void buildTFMap(DocumentTree tree)
	{
		// This stores a temporary IDF count - from this we can compute the IDF
		// score
		Map<String, Double> counts = new TreeMap<String, Double>();
		int termCount = 0;

		for (DocumentWord word : tree.deriveWords())
			if (word.shouldScore() && !sw.isStopWord(word.getScoreWord()))
			{
				String text = stemmer.stem(word.getScoreWord());
				if (!counts.containsKey(text))
					counts.put(text, 1.0);
				else
					counts.put(text, counts.get(text) + 1.0);
				termCount++;
			}

		for (Entry<String, Double> entry : counts.entrySet())
			tfScore.put(entry.getKey(), computeTFScore(entry.getValue(),
					termCount));
	}

	protected double combine(double tf, double idf)
	{
		return tf * idf;
	}

	protected double computeIDFScore(double docCount, double corpusCount)
	{
		return corpusCount / docCount;
	}

	protected double computeTFScore(double wordCount, double termCount)
	{
		return wordCount / termCount;
	}

	public void saveIDFScores(OutputStream str) throws IOException
	{
		PrintStream ps = new PrintStream(str);
		for (String key : idfScore.keySet())
			ps.println(key + SEP + idfScore.get(key));
		ps.close();
	}

	public void scoreDocument(DocumentTree tree)
	{
		buildTFMap(tree);

		// Score the document tree
		for (DocumentWord word : tree.deriveWords())
			if (word.shouldScore() && !sw.isStopWord(word.getScoreWord()))
			{
				String text = word.getScoreWord();
				if (stemmer != null)
					text = stemmer.stem(word.getScoreWord());

				double tf = tfScore.get(text);
				double idf = 1.0;
				if (idfScore.containsKey(text))
					idf = idfScore.get(text);

				word.setScore(combine(tf, idf));

			}
			else if (sw.isStopWord(word.getScoreWord()))
				word.setScore(0.0);

	}

	public void setStemmer(Stemmer in)
	{
		stemmer = in;

		// Rebuild the IDF Map
		buildIDFMap();
	}

}
