package uk.ac.shef.dynamicdocument.modifier;

import java.io.Serializable;

import uk.ac.shef.dynamicdocument.DocumentWord;

public interface TextModifier extends Serializable
{
	public boolean modifyWord(DocumentWord word, double vgamma, double hgamma);
}
