package uk.ac.shef.dynamicdocument.importance;

import java.util.List;

import uk.ac.shef.dynamicdocument.Word;
import uk.ac.shef.dynamicdocument.WordContainer;

public class BlankComputer implements ImportanceComputer
{

	public WordContainer computeImportance(WordContainer in)
	{
		List<Word> words = in.collapseToWords();
		for (Word word : words)
		{
			word.setAlpha(1.0);
			word.setBeta(1.0);
		}
		
		return in;
	}

}
