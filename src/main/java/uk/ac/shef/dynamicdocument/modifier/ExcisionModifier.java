package uk.ac.shef.dynamicdocument.modifier;

import javax.swing.text.MutableAttributeSet;

import uk.ac.shef.dynamicdocument.Word;

public class ExcisionModifier extends Modifier
{

	@Override
	protected MutableAttributeSet modifyWordImp(Word word, MutableAttributeSet baseSet, double gamma, int[] startAndENd)
	{
		return baseSet;
	}

	protected MutableAttributeSet modifyWordImp(Word word, MutableAttributeSet baseSet, double gamma)
	{
		//We do nothing to this
		return baseSet;
	}

	protected String modifyWordImp(Word word, double gamma)
	{
		//Check that we can manipulate this word
		if (word.getManipulationLevel() == Word.ALWAYS_SHOW)
			return word.getText();

		if (word.getNormRank() > gamma)
		{
			return "";
		}
		else
			return word.getText();
	}


}
