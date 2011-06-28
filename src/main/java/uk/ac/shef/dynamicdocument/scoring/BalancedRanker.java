package uk.ac.shef.dynamicdocument.scoring;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.ac.shef.dynamicdocument.DocumentTree;
import uk.ac.shef.dynamicdocument.DocumentWord;

public class BalancedRanker
{

	public static void setRankings(DocumentTree tree)
	{
		List<DocumentWord> baseWords = tree.deriveWords(DocumentTree.SCORE);
		final Map<String, Double> wordScore = new TreeMap<String, Double>();
		List<String> justWords = new LinkedList<String>();

		for (DocumentWord documentWord : baseWords)
			wordScore.put(documentWord.getScoreWord(), documentWord.getScore());

		justWords.addAll(wordScore.keySet());
		Collections.sort(justWords, new Comparator<String>()
		{
			/**
			 * @see java.util.Comparator#compare(java.lang.Object,
			 *      java.lang.Object)
			 */
			@Override
			public int compare(String o1, String o2)
			{
				return wordScore.get(o1).compareTo(wordScore.get(o2));
			}
		});

		Map<String, Double> wordRank = new TreeMap<String, Double>();
		for (int i = 0; i < justWords.size(); i++)
			wordRank.put(justWords.get(i), (i + 0.0) / justWords.size());

		for (DocumentWord word : baseWords)
			word.setHorizontalRank(wordRank.get(word.getScoreWord()));

	}
}
