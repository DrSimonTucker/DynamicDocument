package uk.ac.shef.dynamicdocument.keywords;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import uk.ac.shef.dynamicdocument.DocumentWord;
import uk.ac.shef.importance.tfidf.StopWord;

/**
 * A keyword selector which uses the word scores (and stop word presence) to
 * select keywords
 * 
 * @author simon
 * 
 */
public class WordScoreKeywordSelector implements KeywordSelector
{
	private final StopWord sw;

	public WordScoreKeywordSelector(StopWord stopWords)
	{
		this.sw = stopWords;
	}

	/**
	 * @see uk.ac.shef.dynamicdocument.keywords.KeywordSelector#getKeywords(java.util.Collection,
	 *      int)
	 */
	@Override
	public List<DocumentWord> getKeywords(Collection<DocumentWord> words, int numberOfWords)
	{
		// Get the non-stopwords
		List<DocumentWord> nonStopWords = new LinkedList<DocumentWord>();
		for (DocumentWord documentWord : words)
			if (!sw.isStopWord(documentWord.getText()))
				nonStopWords.add(documentWord);

		// Sort them by their TF*IDF Score
		Collections.sort(nonStopWords, new Comparator<DocumentWord>()
		{
			/**
			 * @see java.util.Comparator#compare(java.lang.Object,
			 *      java.lang.Object)
			 */
			@Override
			public int compare(DocumentWord o1, DocumentWord o2)
			{
				return o1.getScore().compareTo(o2.getScore());
			}

		});

		// Return the sublist of the relevant size
		return nonStopWords.subList(0, Math.min(numberOfWords, nonStopWords.size()));
	}

}
