package uk.ac.shef.importance.tfidf;

import java.util.Collection;
import java.util.Map;

/**
 * Class that uses TFIDF to score words
 * 
 * @author simon
 * 
 */
public class TFIDFScorer implements WordScorer
{
	/** Underlying mechanism for scoring the words */
	// private TFIDFAlphaBetaComputer<String> baseScorer;
	/**
	 * @see uk.ac.shef.importance.tfidf.WordScorer#computeScores(java.util.Collection)
	 */
	@Override
	public final Map<String, Double> computeScores(final Collection<String> words)
	{
		return null;
	}

}
