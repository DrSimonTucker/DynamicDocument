package uk.ac.shef.dynamicdocument;

import java.util.List;
import java.util.Vector;

public class Word implements WordContainer
{
	public static final int ALWAYS_APPLY = 1;

	public static final int ALWAYS_SHOW = 2;

	public static final int DISTINCT = 2;

	public static final int FULL = 0;
	public static final int TO_FOLLOWING = 1;
	public static final int TO_PREVIOUS = 0;

	// THis tells us where the word should be placed in relation to the text
	// (useful for e.g. punctuation)
	protected int connect;
	// There are some words that are words but may not be processed by
	// importance stuff etc.
	protected int manipulationLevel;
	// The word itself
	protected String text;
	// The static importance of the unit
	double alpha;
	// The contextual importance of the unit
	double beta;
	boolean bold;

	double endTime = -1;

	double normRank;

	// This deals with the case that the word comes from a transcript -
	// otherwise we can ignore this
	double startTime = -2;

	public Word(String text, int manipulationLevel, int connect)
	{
		this.text = text;
		this.manipulationLevel = manipulationLevel;
		this.connect = connect;

		// Default is to show the word
		alpha = 1.0;
		beta = 1.0;
	}

	public List<Word> collapseToWords()
	{
		List<Word> ret = new Vector<Word>();
		ret.add(this);
		return ret;
	}

	@Override
	public int compareTo(WordContainer o)
	{
		if (this.getStartTime() == ((Word) o).getStartTime())
			return 0;
		else if (this.getStartTime() < ((Word) o).getStartTime())
			return 1;
		else
			return -1;
	}

	@Override
	public boolean equals(Object obj)
	{
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	public double getAlpha()
	{
		return alpha;
	}

	public double getBeta()
	{
		return beta;
	}

	public int getConnect()
	{
		return connect;
	}

	public double getEndTime()
	{
		return endTime;
	}

	public int getManipulationLevel()
	{
		return manipulationLevel;
	}

	public double getNormRank()
	{
		return normRank;
	}

	public double getStartTime()
	{
		return startTime;
	}

	public String getText()
	{
		return text;// + " (" +alpha*beta+ ") ";
	}

	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	public boolean isBold()
	{
		return bold;
	}

	public void offset(double timeInSeconds)
	{
		startTime -= timeInSeconds;
		endTime -= timeInSeconds;
	}

	public void setAlpha(double alpha)
	{
		this.alpha = alpha;
	}

	public void setBeta(double beta)
	{
		this.beta = beta;
	}

	public void setBold(boolean bold)
	{
		this.bold = bold;
	}

	public void setConnect(int connect)
	{
		this.connect = connect;
	}

	public void setEndTime(double endTime)
	{
		this.endTime = endTime;
	}

	public void setManipulationLevel(int level)
	{
		manipulationLevel = level;
	}

	public void setRank(int rank, double normRank)
	{
		this.normRank = normRank;
	}

	public void setStartTime(double startTime)
	{
		this.startTime = startTime;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public void setUnionAlpha(double alpha)
	{
		this.alpha = Math.min(alpha, this.alpha);
	}

	public void setUnionBeta(double beta)
	{
		this.beta = Math.min(beta, this.beta);
	}

}
