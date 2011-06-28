package uk.ac.shef.dynamicdocument;

import java.util.List;
import java.util.Vector;

/**
 * This represents a sentence (i.e. a list of words) - can be subclassed for
 * other similar semantic units
 * 
 * @author simon
 * 
 */
public class WordSet implements WordContainer
{
	protected List<WordContainer> set = new Vector<WordContainer>();

	public List<Word> collapseToWords()
	{
		List<Word> sent = new Vector<Word>();

		sent.addAll(preamble().collapseToWords());
		for (WordContainer cont : set)
			sent.addAll(cont.collapseToWords());
		sent.addAll(postamble().collapseToWords());

		return sent;
	}

	@Override
	public int compareTo(WordContainer o)
	{
		// Quasi random sorting
		return -1;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof WordSet)
			return this.compareTo((WordSet) o) == 0;
		else
			return false;
	}

	public List<WordContainer> getChildren()
	{
		return set;
	}

	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	public WordContainer postamble()
	{
		// Sentences end with a full stop
		return new Blank();
	}

	public WordContainer preamble()
	{
		// Sentences have no preamble
		return new Blank();
	}

}
