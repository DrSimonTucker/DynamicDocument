package uk.ac.shef.dynamicdocument.modifier;

import java.awt.Color;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;

import uk.ac.shef.dynamicdocument.Word;

public class ShaderModifier extends Modifier
{
	double DARKEST = 1.0;
	double LIGHTEST = 0.0;

	@Override
	protected String modifyWordImp(Word word, double gamma)
	{
		return word.getText();
	}

	@Override
	protected MutableAttributeSet modifyWordImp(Word word, MutableAttributeSet baseSet, double gamma, int[] startAndENd)
	{
		// Step one - should we modify
		if (word.getManipulationLevel() != Word.ALWAYS_SHOW && word.getNormRank() > gamma)
		{
			// Determine the color (use gray for now)
			int num = 255 - (int) (255 * ((word.getNormRank() * gamma) * (DARKEST - LIGHTEST) + LIGHTEST));
			Color nColor = Color.black;

			if (word.getNormRank() >= gamma)
				nColor = new Color(num, num, num);
			// nColor = Color.red;

			StyleConstants.setForeground(baseSet, nColor);
		}
		else
		{
			Color nColor = new Color(0, 0, 0);
			StyleConstants.setForeground(baseSet, nColor);
		}

		return baseSet;
	}

}
