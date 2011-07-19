package uk.ac.shef.dynamicdocument.document;

import uk.ac.shef.dynamicdocument.Word;

public class DynamicWord extends Word
{
	public DynamicWord(String word, int lev, int connect, int index)
	{
		super(word,lev,connect);
		setIndex(index);
	}

	public DynamicWord(String word, int index)
	{
		this(word,false,index);
	}
	
	public DynamicWord(String word, boolean bold, int index)
	{
		this("",0,0,index);
		
		setText(word);
		setConnect(Word.DISTINCT);
		setManipulationLevel(Word.ALWAYS_APPLY);
		setBold(bold);
	}
	
	public void setFontSize(int size)
	{
		super.setFontSize(size);
	}
}
