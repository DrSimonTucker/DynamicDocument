package uk.ac.shef.dynamicdocument;

import java.util.List;
import java.util.Vector;

public class Blank implements WordContainer
{

	public List<Word> collapseToWords()
	{
		return new Vector<Word>();
	}

	@Override
	public int compareTo(WordContainer o)
	{
		// Quasi-random sorting
		return -1;
	}

	@Override
	public boolean equals(Object obj)
	{
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
