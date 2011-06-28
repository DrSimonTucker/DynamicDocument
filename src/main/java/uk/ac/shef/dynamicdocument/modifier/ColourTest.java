package uk.ac.shef.dynamicdocument.modifier;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import uk.ac.shef.dynamicdocument.DocumentWord;

public class ColourTest implements PostLayoutModifier
{
	MutableAttributeSet redMod;

	public ColourTest()
	{
		redMod = new SimpleAttributeSet();
		StyleConstants.setForeground(redMod, Color.red);
	}

	/**
	 * Turn all the words with an "s" in red
	 * 
	 * @see uk.ac.shef.dynamicdocument.modifier.PostLayoutModifier#modify(uk.ac.shef.dynamicdocument.DocumentWord)
	 */
	@Override
	public AttributeSet modify(DocumentWord word)
	{
		if (word.getText().contains("s"))
			return redMod;
		else
			return null;
	}
}
