package uk.ac.shef.dynamicdocument.scoring;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.ac.shef.dynamicdocument.DocumentTree;
import uk.ac.shef.dynamicdocument.DocumentWord;

public class Ranker
{
	public static void setRankings(DocumentTree tree)
	{
		List<DocumentWord> baseWords = tree.deriveWords(DocumentTree.SCORE);

		// Order the base words by their score (lowest to highest
		Collections.sort(baseWords, new Comparator<DocumentWord>()
		{
			@Override
			public int compare(DocumentWord o1, DocumentWord o2)
			{
				return o1.getScore().compareTo(o2.getScore());
			}
		});

		// Do a linear rank of the sorted words
		for (int i = 0; i < baseWords.size(); i++)
			baseWords.get(i).setHorizontalRank(1 - (i / (baseWords.size() - 1.0)));
	}
}
