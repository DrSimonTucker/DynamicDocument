package uk.ac.shef.dynamicdocument.gui;

import java.awt.event.MouseEvent;

import uk.ac.shef.dynamicdocument.DocumentWord;

/**
 * For devices that want to listen out for changes
 * 
 * @author simon
 * 
 */
public interface DocumentPanelSelectionListener
{
	/** The word has been released by the given Mouse Event */
	void wordReleased(MouseEvent e);

	/** The word has been selected by the given MouseEvent */
	void wordSelected(DocumentWord word, MouseEvent e);
}
