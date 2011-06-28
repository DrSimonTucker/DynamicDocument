package uk.ac.shef.dynamicdocument.modifier;

import javax.swing.text.MutableAttributeSet;

import uk.ac.shef.dynamicdocument.Word;

public class BlankModifier extends Modifier
{

	@Override
	protected String modifyWordImp(Word word, double gamma)
	{
		return word.getText();
	}

	@Override
	protected MutableAttributeSet modifyWordImp(Word word, MutableAttributeSet baseSet, double gamma, int[] startAndENd)
	{
		return baseSet;
	}

}
