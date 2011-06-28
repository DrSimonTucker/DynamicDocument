package uk.ac.shef.dynamicdocument.keywords;

import java.util.Collection;
import java.util.List;

import uk.ac.shef.dynamicdocument.DocumentWord;

public interface KeywordSelector
{
	/**
	 * Get the keywords from a given set of words
	 * 
	 * @param words
	 *            The set of words to select from
	 * @param numberOfWords
	 *            The number of words to select
	 * @return An ordered list (best first) of the keywords
	 */
	public List<DocumentWord> getKeywords(Collection<DocumentWord> words, int numberOfWords);
}
