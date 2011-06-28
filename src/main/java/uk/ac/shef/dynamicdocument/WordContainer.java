package uk.ac.shef.dynamicdocument;

import java.util.List;

//This is any class that could contain
public interface WordContainer extends Comparable<WordContainer>
{
	public List<Word> collapseToWords();
}
