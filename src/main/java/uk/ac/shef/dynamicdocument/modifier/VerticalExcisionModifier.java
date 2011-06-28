package uk.ac.shef.dynamicdocument.modifier;

import uk.ac.shef.dynamicdocument.DocumentWord;

public class VerticalExcisionModifier implements TextModifier
{
	String EXCISION_TEXT = "";

	@Override
	public boolean modifyWord(DocumentWord word, double vgamma, double hgamma)
	{
		// if the vertical gamma says so then we say remove!
		if (word.getVerticalRank() < vgamma)
			if (word.getLayoutText().equals(EXCISION_TEXT))
				return false;
			else
			{
				word.setLayoutText(EXCISION_TEXT);
				return true;
			}
		else if (!word.getLayoutText().equals(EXCISION_TEXT))
			return false;
		else
		{
			word.setLayoutText(word.getText());
			return true;
		}

	}
}
