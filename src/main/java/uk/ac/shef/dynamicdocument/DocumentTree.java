package uk.ac.shef.dynamicdocument;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import uk.ac.shef.dynamicdocument.modifier.AttributeModifier;
import uk.ac.shef.dynamicdocument.modifier.TextModifier;

public abstract class DocumentTree
{
	public static final int ALWAYS_SHOW = 3;
	public static final int IGNORE = 1;
	public static final int SCORE = 0;
	public static final int SHOW = 0;
	public static final int SHOW_WITH_PREVIOUS = 1;
	public static final int SHOW_WITH_SIBLINGS = 2;

	static int dTreeCount = 100;
	int caratEnd = -1;
	int caratStart = -1;

	boolean deleted = false;
	DocumentTree parent;
	int scoreProcedure = SCORE;

	boolean showing = false;

	int showProcedure = SHOW;

	List<DocumentTree> subtrees = new LinkedList<DocumentTree>();
	boolean toBeDeleted = false;

	public DocumentTree()
	{
		dTreeCount++;
	}

	protected void addSubTree(DocumentTree subtree)
	{
		subtree.setParent(this);
		subtrees.add(subtree);
	}

	protected void addSubTree(DocumentTree subtree, int position)
	{
		subtree.setParent(this);
		subtrees.add(position, subtree);
	}

	protected void addSubTreeAfter(DocumentTree subtree, DocumentTree after)
	{
		subtree.setParent(this);

		int loc = subtrees.indexOf(after);
		subtrees.add(loc + 1, subtree);
	}

	protected void addSubTreeFirst(DocumentTree subtree)
	{
		subtree.setParent(this);
		subtrees.add(0, subtree);
	}

	public void delete()
	{
		toBeDeleted = true;
	}

	public List<DocumentWord> deriveWords()
	{
		List<DocumentWord> words = new LinkedList<DocumentWord>();
		for (DocumentTree subtree : subtrees)
			words.addAll(subtree.deriveWords());
		return words;
	}

	public List<DocumentWord> deriveWords(int scoreProcedure)
	{
		List<DocumentWord> words = new LinkedList<DocumentWord>();
		for (DocumentTree subtree : subtrees)
			words.addAll(subtree.deriveWords(scoreProcedure));
		return words;
	}

	public int fixLocation(int caratLocation)
	{
		this.caratStart = caratLocation;
		for (DocumentTree subtree : subtrees)
			caratLocation = subtree.fixLocation(caratLocation);
		caratEnd = caratLocation;

		return caratEnd;
	}

	public int getCaratEnd()
	{
		return caratEnd;
	}

	public int getCaratStart()
	{
		return caratStart;
	}

	public DocumentWord getDocumentWord(int caratLocation)
	{
		for (DocumentTree subtree : subtrees)
			if (subtree.getCaratStart() <= caratLocation
					&& subtree.getCaratEnd() >= caratLocation)
				return subtree.getDocumentWord(caratLocation);

		return null;
	}

	public int getLayoutLength()
	{
		return caratEnd - caratStart;
	}

	/**
	 * Gets the parent of this subtree (returns null if this is the root tree
	 * 
	 * @return The parent tree or null
	 */
	public DocumentTree getParent()
	{
		return parent;
	}

	public int getScoreProcedure()
	{
		return scoreProcedure;
	}

	public int getShowProcedure()
	{
		return showProcedure;
	}

	protected List<DocumentTree> getSubTrees()
	{
		return subtrees;
	}

	public boolean isDeleted()
	{
		return deleted;
	}

	public boolean isShowing()
	{
		return showing;
	}

	/*
	 * public int layout(StyledDocument doc, int caratPoint, AttributeModifier
	 * aMod, TextModifier tMod, double gamma, boolean previousShown, boolean
	 * show) throws BadLocationException { //We start off and assume that the
	 * non existent previous has been shown int oldCaratPoint = 0; //Work out
	 * whether the siblings are shown show = siblingsShown(tMod, gamma); //Now
	 * layout the text for(int i = 0 ; i < subtrees.size() ; i++) {
	 * oldCaratPoint = caratPoint; caratPoint = subtrees.get(i).layout(doc,
	 * caratPoint,aMod,tMod,gamma,previousShown,show); //Quick method of seeing
	 * if the subtree was shown previousShown = (caratPoint != oldCaratPoint); }
	 * return caratPoint; }
	 */

	/**
	 * The layout algorithm
	 * 
	 * @param doc
	 *            The document subset to layout
	 * @param caratPoint
	 *            The carat point to start layout at
	 * @param aMod
	 *            The attribute modifier
	 * @param tMod
	 *            The text modifier
	 * @param vgamma
	 *            The vertical gamma value
	 * @param hgamma
	 *            The horizontal gamma value
	 * @param show
	 *            A flag indicating whether this element should be shown
	 * @return the carat point of following this document subset
	 * @throws BadLocationException
	 *             If a part of the text cannot be shown
	 */
	public int layout(final StyledDocument doc, final int caratPoint,
			final AttributeModifier aMod, final TextModifier tMod,
			final double vgamma, final double hgamma, final boolean show)
			throws BadLocationException
	{
		// First off - assume this isn't showing
		showing = false;

		caratStart = caratPoint;

		// If we're going to be deleted then remove us from the display
		if (toBeDeleted)
		{

			caratEnd = caratPoint;
			deleted = true;
			showing = false;

			// Remove all the previous words
			for (DocumentWord word : deriveWords())
			{
				word.setLayoutText("");
				word.layout(doc, caratPoint, aMod, tMod, vgamma, hgamma, false);
			}

			return caratEnd;
		}
		else
		{
			// First pass - layout the SHOW stuff and the SHOW_WITH_PREVIOUS
			int caratLocation = caratPoint;
			int showCount = 0;
			boolean prevLayedOut = false;

			for (int i = 0; i < subtrees.size(); i++)
			{
				DocumentTree currTree = subtrees.get(i);

				if (currTree.getShowProcedure() != SHOW_WITH_SIBLINGS)
					if (currTree.getShowProcedure() == SHOW
							|| currTree.getShowProcedure() == ALWAYS_SHOW
							|| prevLayedOut)
					{
						// Layout this sub tree
						caratLocation = currTree.layout(doc, caratLocation,
								aMod, tMod, vgamma, hgamma, show);
						if (currTree.isShowing())
						{
							showing = true;
							showCount++;
							prevLayedOut = true;
						}
						else
							prevLayedOut = false;
					}
					else
						// Hide this sub tree
						caratLocation = currTree.layout(doc, caratLocation,
								aMod, tMod, vgamma, hgamma, false);
				else
					caratLocation += currTree.getLayoutLength();
			}

			// Second pass - layout the SHOW_WITH_SIBLINGS if the siblings are
			// shown
			caratLocation = caratPoint;
			boolean nowShow = (showCount > 0) && show;
			for (int i = 0; i < subtrees.size(); i++)
			{
				DocumentTree currTree = subtrees.get(i);
				if (currTree.getShowProcedure() == SHOW_WITH_SIBLINGS)
				{
					// Layout this sub tree
					caratLocation = currTree.layout(doc, caratLocation, aMod,
							tMod, vgamma, hgamma, nowShow);
					if (currTree.isShowing())
					{
						showCount++;
						prevLayedOut = true;
						showing = true;
					}
				}
				else
					caratLocation += currTree.getLayoutLength();
			}

			// Third pass - deal with deletions
			List<DocumentTree> deletedSubs = new LinkedList<DocumentTree>();
			for (DocumentTree documentTree : subtrees)
				if (documentTree.isDeleted())
					deletedSubs.add(documentTree);
			subtrees.removeAll(deletedSubs);

			caratEnd = caratLocation;

			return caratLocation;
		}

	}

	public void reset()
	{
		this.showing = false;
		this.toBeDeleted = false;
		this.deleted = false;
		this.caratEnd = -1;
		this.caratStart = -1;
		for (DocumentTree subtree : subtrees)
			subtree.reset();
	}

	public void setCaratEnd(int caratEnd)
	{
		this.caratEnd = caratEnd;
	}

	public void setCaratStart(int caratStart)
	{
		this.caratStart = caratStart;
	}

	public void setParent(DocumentTree par)
	{
		parent = par;
	}

	public void setScoreProcedure(int proc)
	{
		scoreProcedure = proc;
	}

	public void setShowProcedure(int proc)
	{
		showProcedure = proc;
	}

	/**
	 * This indicates whether this tree should be scored (is it preamble /
	 * punctuation etc.)
	 * 
	 * @return boolean : true if this should be scored, false if not
	 */
	public boolean shouldScore()
	{
		if (scoreProcedure == IGNORE)
			return false;
		else if (parent == null)
			return true;
		else
			return parent.shouldScore();

	}

	public void undelete()
	{
		toBeDeleted = false;
	}
}
