package uk.ac.shef.dynamicdocument.gui;

import java.awt.Component;

import javax.swing.JPopupMenu;

import uk.ac.shef.dynamicdocument.DocumentWord;

/**
 * A simple pop up menu for use with the dynamic document display
 * 
 * @author simon
 * 
 */
public abstract class DynamicDocPopUp extends JPopupMenu
{
	/**
	 * Shows the popup menu
	 * 
	 * @param w
	 *            The word that is under the mouse upon popup
	 * @param c
	 *            The calling component
	 * @param x
	 *            The x location of the popup
	 * @param y
	 *            The y location of the popup
	 */
	public abstract void show(final DocumentWord w, final Component c, final int x, final int y);
}
