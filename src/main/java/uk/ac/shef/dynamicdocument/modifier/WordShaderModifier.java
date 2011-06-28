package uk.ac.shef.dynamicdocument.modifier;

import java.awt.Color;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;

import uk.ac.shef.dynamicdocument.DocumentTree;
import uk.ac.shef.dynamicdocument.DocumentWord;

public class WordShaderModifier implements AttributeModifier
{
	static Color noShow = new Color(200, 200, 200);
	static Color show = new Color(0, 0, 0);

	@Override
	public MutableAttributeSet modifyWordImp(DocumentWord word, double vgamma, double hgamma, MutableAttributeSet attr)
	{
		// Step one - should we modify
		if (word.getShowProcedure() == DocumentTree.SHOW)
		{
			if (word.getHorizontalRank() >= hgamma)
				StyleConstants.setForeground(attr, noShow);
			else
				StyleConstants.setForeground(attr, show);
		}
		else
			StyleConstants.setForeground(attr, show);

		return attr;
	}

}
