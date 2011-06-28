package uk.ac.shef.dynamicdocument.importance;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import uk.ac.shef.dynamicdocument.Word;
import uk.ac.shef.dynamicdocument.WordContainer;

public class RankComputer
{
	int rev = 1;

	public RankComputer()
	{
		this(true);
	}

	public RankComputer(boolean rev)
	{
		if (rev)
			this.rev = 1;
		else
			this.rev = -1;
	}

	public WordContainer deriveBalancedRanks(WordContainer cont)
	{
		List<Word> words = cont.collapseToWords();

		// Count the number of relevant levels
		Map<Double, List<Word>> rankingMap = new TreeMap<Double, List<Word>>();
		for (Word word : words)
			if (word.getConnect() == Word.DISTINCT && word.getManipulationLevel() != Word.ALWAYS_SHOW)
			{
				double score = word.getAlpha() * word.getBeta();
				if (!rankingMap.containsKey(score))
					rankingMap.put(score, new Vector<Word>());

				List<Word> rankList = rankingMap.get(score);
				rankList.add(word);
				rankingMap.put(score, rankList);
			}

		// Sort the scores
		List<Double> scores = new Vector<Double>(rankingMap.keySet());
		Collections.sort(scores, new Comparator<Double>()
		{

			public int compare(Double o1, Double o2)
			{
				return -1 * o1.compareTo(o2);
			}

		});

		// Put in the ranking
		for (int i = 0; i < scores.size(); i++)
			for (Word word : rankingMap.get(scores.get(i)))
				word.setRank(i, i / (scores.size() - 1.0));

		return cont;
	}

	public WordContainer deriveRanks(WordContainer cont)
	{
		List<Word> words = cont.collapseToWords();

		// Count the number of relevant words
		double relevantWords = 0;
		for (Word word : words)
			if (word.getConnect() == Word.DISTINCT && word.getManipulationLevel() != Word.ALWAYS_SHOW)
				relevantWords++;

		// Sort the words by their combined alpha-beta score
		Collections.sort(words, new Comparator<Word>()
		{
			public int compare(Word o1, Word o2)
			{
				if (o1.getAlpha() * o1.getBeta() > o2.getAlpha() * o2.getBeta())
					return rev * -1;
				else
					return rev * 1;
			}
		});

		// Put in the ranking
		int checkCount = 0;
		for (int i = 0; i < words.size(); i++)
			if (words.get(i).getConnect() == Word.DISTINCT && words.get(i).getManipulationLevel() != Word.ALWAYS_SHOW)
			{
				words.get(i).setRank(i, checkCount / relevantWords);
				checkCount++;
			}

		return cont;
	}
}
