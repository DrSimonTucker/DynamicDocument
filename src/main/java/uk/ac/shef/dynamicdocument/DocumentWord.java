package uk.ac.shef.dynamicdocument;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import uk.ac.shef.dynamicdocument.modifier.AttributeModifier;
import uk.ac.shef.dynamicdocument.modifier.TextModifier;

public class DocumentWord extends DocumentTree implements
		Comparable<DocumentWord>, Cloneable
{
	static int id = 1;

	boolean bold = false;

	String cleanedText;

	double hRank = 1.0;

	String layoutText = "";

	int myid;

	boolean rankForced = false;
	double score = 1.0;

	// The word used in scoring may differ from the actual word itself
	String scoreWord;
	String text;

	double vRank = 1.0;

	public DocumentWord(String text)
	{
		this.text = text;
		this.myid = id;
		id++;
		scoreWord = text.toLowerCase();
		this.cleanedText = clean(text);
	}

	public DocumentWord(String word, String scoreWord)
	{
		this(word);
		this.scoreWord = scoreWord.toLowerCase();
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		super.clone();
		DocumentWord dw = new DocumentWord(text + " ");
		dw.setShowProcedure(DocumentTree.SHOW);
		dw.setScoreProcedure(DocumentTree.SCORE);
		return dw;
	}

	@Override
	public int compareTo(DocumentWord o)
	{
		return this.getText().compareTo(o.getText());
	}

	/**
	 * @see DocumentTree#deriveWords()
	 */
	@Override
	public List<DocumentWord> deriveWords()
	{
		LinkedList<DocumentWord> docWords = new LinkedList<DocumentWord>();
		docWords.add(this);
		return docWords;
	}

	@Override
	public List<DocumentWord> deriveWords(int score)
	{
		LinkedList<DocumentWord> docWords = new LinkedList<DocumentWord>();

		if (this.getScoreProcedure() == score)
			docWords.add(this);

		return docWords;
	}

	@Override
	public boolean equals(Object obj)
	{
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public int fixLocation(int caratLocation)
	{
		caratStart = caratLocation;
		caratEnd = caratLocation + layoutText.length();

		return caratEnd;
	}

	public void forceHorizontalRank(double rank)
	{
		setHorizontalRank(rank);
		rankForced = true;
	}

	public void forceVerticalRank(double rank)
	{
		setVerticalRank(rank);
		rankForced = true;
	}

	@Override
	public int getCaratEnd()
	{
		return caratEnd;
	}

	public int getCaratMiddle()
	{
		return (caratEnd + caratStart) / 2;
	}

	@Override
	public int getCaratStart()
	{
		return caratStart;
	}

	public String getCleanedText()
	{
		return cleanedText;
	}

	@Override
	public DocumentWord getDocumentWord(int caratLocation)
	{
		return this;
	}

	public double getHorizontalRank()
	{
		return hRank;
	}

	public int getID()
	{
		return myid;
	}

	public String getLayoutText()
	{
		return layoutText;
	}

	public Double getScore()
	{
		return score;
	}

	int oldShow = -1;

	public void hide()
	{
		oldShow = showProcedure;
		showProcedure = IGNORE;
	}

	public void show()
	{
		showProcedure = oldShow;
	}

	public String getScoreWord()
	{
		return scoreWord;
	}

	public String getText()
	{
		return text;
	}

	public double getVerticalRank()
	{
		return vRank;
	}

	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public int layout(final StyledDocument doc, final int caratPoint,
			final AttributeModifier aMod, final TextModifier tMod,
			final double vgamma, final double hgamma, final boolean show)
			throws BadLocationException
	{
		// Build the style to applied to the word
		MutableAttributeSet s = new SimpleAttributeSet();
		StyleConstants.setBold(s, bold);
		s = aMod.modifyWordImp(this, vgamma, hgamma, s);

		// Get the string associated with it
		boolean modified = false;

		/*
		 * The word is modified if: a) The modifier alters the text b) The show
		 * flag differs from the current state of the word
		 */
		if (show && showProcedure == DocumentTree.SHOW)
			modified = tMod.modifyWord(this, vgamma, hgamma);
		else if (showing != show)
		{
			modified = true;
			if (!show)
				layoutText = "";
			else if (showProcedure != DocumentTree.SHOW)
				layoutText = text;

			showing = show;
		}

		if (caratStart == -1 && showing)
		{
			// This means that the word hasn't been laid out yet
			doc.insertString(caratPoint, layoutText, s);
		}
		else if (modified)
		{
			// We're showing if the text hasn't been altered
			showing = (layoutText.equals(text));

			// The word has been altered - first remove the old word then
			// replace with the new one
			doc.remove(caratPoint, caratEnd - caratStart);
			doc.insertString(caratPoint, layoutText, s);
		}
		else
			// Now the word has been layed out - just alter the style constants
			doc
					.setCharacterAttributes(caratPoint, layoutText.length(), s,
							true);

		// Set the caratLocation stuff
		caratStart = caratPoint;
		caratEnd = caratStart + layoutText.length();

		return caratPoint + layoutText.length();
	}

	@Override
	public void reset()
	{
		super.reset();
		layoutText = "";
	}

	public void setBold(boolean bold)
	{
		this.bold = bold;
	}

	@Override
	public void setCaratEnd(int caratEnd)
	{
		this.caratEnd = caratEnd;
	}

	@Override
	public void setCaratStart(int caratStart)
	{
		this.caratStart = caratStart;
	}

	public void setHorizontalRank(double rank)
	{
		if (!rankForced)
			this.hRank = rank;
	}

	public void setLayoutText(String in)
	{
		layoutText = in;
	}

	public void setScore(double score)
	{
		this.score = score;
	}

	public void setVerticalRank(double rank)
	{
		// We don't force the vertical rank!
		this.vRank = rank;
	}

	private static String clean(final String text)
	{
		String[] rem = new String[]
		{ ".", "!", "," };
		String nText = text;
		for (String string : rem)
			nText = nText.replace(string, "");
		return nText.trim();
	}
}
