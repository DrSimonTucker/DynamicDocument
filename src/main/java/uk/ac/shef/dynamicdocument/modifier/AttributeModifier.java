package uk.ac.shef.dynamicdocument.modifier;

import java.io.Serializable;

import javax.swing.text.MutableAttributeSet;

import uk.ac.shef.dynamicdocument.DocumentWord;

public interface AttributeModifier extends Serializable
{
	public MutableAttributeSet modifyWordImp(DocumentWord word, double vgamma, double hgamma, MutableAttributeSet attr);
}
