package uk.ac.shef.dynamicdocument;

public class WordGenerator
{	
	public static Word generateFullStop()
	{
		Word fs = new Word(".",Word.FULL,Word.TO_PREVIOUS);
		fs.setAlpha(0.0);
		fs.setBeta(0.0);
		return fs;
	}
	
	public static Word generateLineBreak()
	{
		Word lb = new Word("\n",Word.ALWAYS_SHOW,Word.TO_PREVIOUS);
		lb.setAlpha(0.0);
		lb.setBeta(0.0);
		return lb;
	}
	
	public static Word generateDoubleLineBreak()
	{
		Word lb = new Word("\n\n",Word.ALWAYS_SHOW,Word.TO_FOLLOWING);
		lb.setBeta(0.0);
		lb.setAlpha(0.0);
		return lb;
	}
}
