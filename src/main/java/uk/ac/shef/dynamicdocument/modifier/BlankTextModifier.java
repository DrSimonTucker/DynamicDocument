package uk.ac.shef.dynamicdocument.modifier;

import uk.ac.shef.dynamicdocument.DocumentWord;

public class BlankTextModifier implements TextModifier
{

	@Override
	public boolean modifyWord(DocumentWord word, double vgamma, double hgamma)
	{
		if (!word.getLayoutText().equals(word.getText()))
		{
			word.setLayoutText(word.getText());
			return true;
		}
		else
			return false;
	}
}
