package uk.ac.shef.dynamicdocument.modifier;

import uk.ac.shef.dynamicdocument.DocumentTree;
import uk.ac.shef.dynamicdocument.DocumentWord;

public class WordExcisionModifier implements TextModifier
{
	String EXCISION_TEXT = "";

	@Override
	public boolean modifyWord(DocumentWord word, double vgamma, double hgamma)
	{
		// Check that we can manipulate this word
		if (word.getShowProcedure() == DocumentTree.SHOW && word.getHorizontalRank() <= hgamma)
		{
			if (word.getLayoutText().equals(EXCISION_TEXT))
				return false;
			else
			{
				word.setLayoutText(EXCISION_TEXT);
				return true;
			}
		}
		else if (word.getLayoutText().equals(word.getText()))
			return false;
		else
		{
			// Only alter text that needs it
			if (word.getShowProcedure() == DocumentTree.SHOW)
				word.setLayoutText(word.getText());

			return true;
		}

	}
}
