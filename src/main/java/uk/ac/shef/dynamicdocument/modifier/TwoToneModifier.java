package uk.ac.shef.dynamicdocument.modifier;

import java.awt.Color;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;

import uk.ac.shef.dynamicdocument.Word;

public class TwoToneModifier extends Modifier
{
	public static final int MAX = 0;
	public static final int MIN = 999999999;

	private int maxCarat = MAX;
	private int minCarat = MIN;

	public TwoToneModifier()
	{
	}

	public int getMaxCarat()
	{
		return maxCarat;
	}

	public int getMinCarat()
	{
		return minCarat;
	}

	public void reset(int min, int max)
	{
		maxCarat = max;
		setMinCarat(min);
	}

	public void setMinCarat(int min)
	{
		minCarat = min;
	}

	@Override
	protected String modifyWordImp(Word word, double gamma)
	{
		return word.getText();
	}

	@Override
	protected MutableAttributeSet modifyWordImp(Word word, MutableAttributeSet baseSet, double gamma, int[] startAndEnd)
	{
		// Step one - should we modify
		if (word.getManipulationLevel() != Word.ALWAYS_SHOW && gamma >= word.getNormRank())
		{
			// Determine the color (use gray for now)
			Color nColor = Color.white;

			StyleConstants.setForeground(baseSet, nColor);
		}
		else
		{
			if (word.getBeta() < 1 && word.getText().trim().length() > 0)
				if (startAndEnd[0] != startAndEnd[1])
				{
					setMinCarat(Math.min(startAndEnd[0], minCarat));
					maxCarat = Math.max(startAndEnd[1], maxCarat);
				}
			StyleConstants.setForeground(baseSet, Color.black);
		}

		if (word.getBeta() == 1.0)
			// This is a key word - set the min and max carats to include this
			// one
			StyleConstants.setForeground(baseSet, Color.black);

		return baseSet;
	}
}
