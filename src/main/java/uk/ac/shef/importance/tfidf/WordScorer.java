package uk.ac.shef.importance.tfidf;

import java.util.Collection;
import java.util.Map;

/**
 * An interface describing a process that scores words
 * 
 * @author simon
 * 
 */
public interface WordScorer
{
	/**
	 * Method for scoring the words
	 * 
	 * @param words
	 *            A collection of words to score
	 * @return A map detailing the scores
	 */
	Map<String, Double> computeScores(Collection<String> words);
}
