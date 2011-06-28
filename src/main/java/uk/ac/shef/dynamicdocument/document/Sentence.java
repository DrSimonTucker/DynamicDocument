package uk.ac.shef.dynamicdocument.document;

import uk.ac.shef.dynamicdocument.DocumentTree;
import uk.ac.shef.dynamicdocument.DocumentWord;
import uk.ac.shef.dynamicdocument.DocumentWordGenerator;

public class Sentence extends DocumentTree
{
	public Sentence(String sent)
	{
		String[] words = sent.split("\\s+");
		DocumentWord wrd = new DocumentWord(words[0]);
		wrd.setHorizontalRank(0.1);
		addSubTree(wrd);
		for (int i = 1; i < words.length; i++)
		{
			addSubTree(DocumentWordGenerator.generateSpace());
			addSubTree(new DocumentWord(words[i]));
		}
	}
}
