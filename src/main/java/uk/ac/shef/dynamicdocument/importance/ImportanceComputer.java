package uk.ac.shef.dynamicdocument.importance;

import uk.ac.shef.dynamicdocument.WordContainer;

public interface ImportanceComputer
{
	public WordContainer computeImportance(WordContainer in);
}
