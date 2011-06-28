package uk.ac.shef.dynamicdocument.modifier;

import javax.swing.text.MutableAttributeSet;

import uk.ac.shef.dynamicdocument.DocumentWord;

public class BlankAttributeModifier implements AttributeModifier
{
	@Override
	public MutableAttributeSet modifyWordImp(DocumentWord word, double vgamma, double hgamma, MutableAttributeSet attr)
	{
		return attr;
	}

}
