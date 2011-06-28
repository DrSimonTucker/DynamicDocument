package uk.ac.shef.dynamicdocument.modifier;

import javax.swing.text.AttributeSet;

import uk.ac.shef.dynamicdocument.DocumentWord;

/**
 * The post layout modifier runs after the document has been laid out
 * 
 * @author simon
 * 
 */
public interface PostLayoutModifier
{
	/**
	 * Build the attribute set to modify the given word
	 * 
	 * @param word
	 * @return
	 */
	AttributeSet modify(DocumentWord word);
}
