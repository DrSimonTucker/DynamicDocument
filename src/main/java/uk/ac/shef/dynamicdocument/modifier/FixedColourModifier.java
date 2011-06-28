package uk.ac.shef.dynamicdocument.modifier;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;

import uk.ac.shef.dynamicdocument.Word;

public class FixedColourModifier extends Modifier
{
	Map<Double, Color> colorMap = new TreeMap<Double, Color>();
	
	Color[] colorWheel = new Color[] {Color.green,Color.red,Color.magenta,Color.blue, Color.cyan};
	
	public FixedColourModifier()
	{
		colorMap.put(1.0, Color.orange);
		colorMap.put(0.75,Color.red);
		colorMap.put(0.5,Color.blue);
		colorMap.put(0.25,Color.green);
		colorMap.put(0.0, Color.black);
	}
	
	protected MutableAttributeSet modifyWordImp(Word word, MutableAttributeSet baseSet, double gamma, int[] startAndEnd)
	{	
		//We always modifiy
		double score = word.getAlpha()*word.getBeta();
		
		if (!colorMap.containsKey(score))
		{		
			colorMap.put(score,colorWheel[colorMap.keySet().size()]);
		}
		
		if ((1-gamma) <= score)
			StyleConstants.setForeground(baseSet, colorMap.get(score));
		else
		{
			StyleConstants.setForeground(baseSet, Color.white);
		}
		
		return baseSet;
	}

	protected String modifyWordImp(Word word, double gamma)
	{
		return word.getText();
	}

}
