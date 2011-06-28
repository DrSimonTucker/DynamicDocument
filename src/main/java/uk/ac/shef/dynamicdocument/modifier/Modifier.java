package uk.ac.shef.dynamicdocument.modifier;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;

import uk.ac.shef.dynamicdocument.Word;

/**
 * So the modifier knows about the gamma level of the interface and modifies a)
 * the text of the unit or b) it's representation or c) both accordingly
 * 
 * @author simon
 * 
 */
public abstract class Modifier implements Serializable
{
	Map<Word, Color> colorMap = new HashMap<Word, Color>();
	Map<Word, Boolean> modifiedMap = new HashMap<Word, Boolean>();

	public boolean modify(Word word, double gamma)
	{
		// Determines if the word should be modified
		return (word.getManipulationLevel() != Word.ALWAYS_SHOW && gamma >= word.getNormRank());
	}

	public String modifyWord(Word word, double gamma)
	{
		modifiedMap.put(word, modify(word, gamma));
		return modifyWordImp(word, gamma);
	}

	public MutableAttributeSet modifyWord(Word word, MutableAttributeSet baseSet, double gamma, int[] startAndEnd)
	{
		modifiedMap.put(word, modify(word, gamma));
		MutableAttributeSet toRet = modifyWordImp(word, baseSet, gamma, startAndEnd);
		Color col = (Color) toRet.getAttribute(StyleConstants.ColorConstants.Foreground);

		if (!colorMap.containsKey(word))
		{
			colorMap.put(word, col);
			return toRet;
		}

		colorMap.put(word, col);
		return toRet;
	}

	public boolean wordModified(Word word, double gamma)
	{
		// return true;
		return !modifiedMap.containsKey(word) || (modifiedMap.get(word) != modify(word, gamma));
	}

	protected abstract String modifyWordImp(Word word, double gamma);

	protected abstract MutableAttributeSet modifyWordImp(Word word, MutableAttributeSet baseSet, double gamma,
			int[] startAndENd);
}
