package uk.ac.shef.dynamicdocument.gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import uk.ac.shef.dynamicdocument.DocumentTree;
import uk.ac.shef.dynamicdocument.DocumentWord;
import uk.ac.shef.dynamicdocument.modifier.AttributeModifier;
import uk.ac.shef.dynamicdocument.modifier.BlankAttributeModifier;
import uk.ac.shef.dynamicdocument.modifier.BlankTextModifier;
import uk.ac.shef.dynamicdocument.modifier.PostLayoutModifier;
import uk.ac.shef.dynamicdocument.modifier.TextModifier;

public class DynamicDocumentPanel extends JTextPane
{
	private AttributeModifier aMod = new BlankAttributeModifier();
	private DocumentTree doc;
	private double hgamma = 0.0;
	private DocumentWord holdingWord = null;

	/** Collection of parties interested in selection events */
	private final Collection<DocumentPanelSelectionListener> listeners = new LinkedList<DocumentPanelSelectionListener>();

	private StyledDocument paneDoc;
	private final List<PostLayoutModifier> postMods = new LinkedList<PostLayoutModifier>();
	private TextModifier tMod = new BlankTextModifier();
	private double vgamma = 0.0;

	/**
	 * Constructor
	 */
	public DynamicDocumentPanel()
	{
		this.setEditable(false);

		// Deal with the listeners
		this.addMouseListener(new MouseAdapter()
		{
			/**
			 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
			 */
			@Override
			public void mousePressed(final MouseEvent e)
			{
				// Get the word pointed to by this event
				DocumentWord word = doc.getDocumentWord(viewToModel(new Point(e
						.getX(), e.getY())));

				// Inform the listeners
				for (DocumentPanelSelectionListener listener : listeners)
					listener.wordSelected(word, e);
			}

			/**
			 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseReleased(final MouseEvent e)
			{
				for (DocumentPanelSelectionListener listener : listeners)
					listener.wordReleased(e);
			}
		});
	}

	public void addPostLayoutModifier(PostLayoutModifier mod)
	{
		postMods.add(mod);
	}

	public void addSelectionListener(DocumentPanelSelectionListener listener)
	{
		listeners.add(listener);
	}

	public void fixHoldingWord(final boolean hold)
	{
		if (hold)
			holdingWord = getCentralWord();
		else
			holdingWord = null;
	}

	public void relayout()
	{
		// Reset the document to force a full layout
		fixHoldingWord(true);
		layoutDocument();
		scrollToHold();
		fixHoldingWord(false);
	}

	public void scrollToTop()
	{
		JViewport spane = (JViewport) this.getParent();
		int viewportHeight = spane.getHeight();
		int viewportWidth = spane.getWidth();
		final Rectangle scrollRect = new Rectangle(0, 0, viewportWidth,
				viewportHeight);

		// Scrolling must be done in Swing thread
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				scrollRectToVisible(scrollRect);
			}
		});
	}

	public void setDocument(final DocumentTree tree)
	{
		// Clear the current document
		try
		{
			if (paneDoc != null && paneDoc.getLength() > 0)
				paneDoc.remove(0, paneDoc.getLength());
			if (doc != null)
				doc.reset();
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}

		doc = tree;
		paneDoc = this.getStyledDocument();
		System.out.println("Laying out document");
		layoutDocument();
		System.out.println("Done layout");
	}

	public void setGamma(final double vgammaIn, final double hgammaIn)
	{
		long sTime = System.currentTimeMillis();
		if (hgamma != hgammaIn || vgamma != vgammaIn)
		{
			hgamma = hgammaIn;
			vgamma = vgammaIn;
			layoutDocument();
			// scrollToHold();
		}
		System.err.println("Layout time = "
				+ (System.currentTimeMillis() - sTime));
	}

	public void setModifiers(final TextModifier tMod,
			final AttributeModifier aMod)
	{
		this.tMod = tMod;
		this.aMod = aMod;
		layoutDocument();
	}

	private void layoutDocument()
	{
		if (paneDoc != null)
		{
			try
			{
				doc.layout(paneDoc, 0, aMod, tMod, vgamma, hgamma, true);
				doc.fixLocation(0);
			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}

			// Apply and post-layout modifiers
			List<DocumentWord> words = doc.deriveWords();
			for (DocumentWord documentWord : words)
				for (PostLayoutModifier mod : postMods)
				{
					AttributeSet as = mod.modify(documentWord);
					if (as != null)
						paneDoc.setCharacterAttributes(documentWord
								.getCaratStart(), documentWord
								.getLayoutLength(), as, false);
				}
		}
	}

	private void scrollToHold()
	{
		// Scroll to the holding word
		if (holdingWord != null)
			try
			{
				// Get information about the viewport
				JViewport spane = (JViewport) this.getParent();
				int viewportHeight = spane.getHeight();
				int viewportWidth = spane.getWidth();

				Rectangle viewRect = modelToView(holdingWord.getCaratMiddle());
				int windowCentre = (int) (viewRect.y + viewRect.getHeight() / 2);
				final Rectangle scrollRect = new Rectangle(0, windowCentre
						- viewportHeight / 2, viewportWidth, viewportHeight);

				// Scrolling must be done in Swing thread
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						scrollRectToVisible(scrollRect);
					}
				});

			}
			catch (BadLocationException e)
			{
				e.printStackTrace();
			}

	}

	protected DocumentWord getCentralWord()
	{
		// Find the centre of the panel
		JViewport spane = (JViewport) this.getParent();
		Point p = new Point(this.getWidth() / 2, spane.getViewPosition().y
				+ spane.getHeight() / 2);

		// Get the central carat location
		int caratLocation = viewToModel(p);
		DocumentWord centralWord = null;
		if (doc != null)
			centralWord = doc.getDocumentWord(caratLocation);
		return centralWord;
	}
}
