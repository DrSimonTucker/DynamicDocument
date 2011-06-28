package uk.ac.shef.importance.tfidf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import uk.ac.shef.importance.framework.AlphaBetaComputer;

/**
 * A TFIDF Computer that accepts dynamic entry of terms (but not for idf
 * computation)
 * 
 * @author simon
 * 
 */
public class DynamicTFIDFComputer implements AlphaBetaComputer<String>
{
	/** The fixed idfMap */
	private final Map<String, Double> idfMap;

	/** The number of words read so far */
	private int numberOfWords = 0;

	/** Stop words */
	private final StopWord sw;

	/** The dynamic tfMap */
	private final Map<String, Integer> tfMap = new TreeMap<String, Integer>();

	/**
	 * Default Constructor
	 */
	public DynamicTFIDFComputer()
	{
		// Using an empty sw and idf map
		idfMap = null;
		sw = null;
	}

	/**
	 * Constructor
	 * 
	 * @param idfStream
	 *            The IDF file stream
	 * @param stopWords
	 *            Stop Words to use
	 */
	public DynamicTFIDFComputer(final InputStream idfStream, final String separator, final StopWord stopWords)
	{
		this.sw = stopWords;
		idfMap = readIDFMap(idfStream, separator);
	}

	/**
	 * Method for adding a word to the TF Map
	 * 
	 * @param word
	 *            The word to be added
	 */
	public final void addWord(final String word)
	{
		numberOfWords++;

		if (!tfMap.containsKey(word))
			tfMap.put(word, 1);
		else
			tfMap.put(word, tfMap.get(word) + 1);
	}

	/**
	 * @see uk.ac.shef.importance.framework.AlphaBetaComputer#computeAlpha(java.lang.Object)
	 */
	@Override
	public final double computeAlpha(final String obj)
	{
		if (tfMap.containsKey(obj) && (sw != null && sw.isStopWord(obj)))
			return (tfMap.get(obj) + 0.0) / numberOfWords;
		else
			return 0;
	}

	/**
	 * @see uk.ac.shef.importance.framework.AlphaBetaComputer#computeBeta(java.lang.Object)
	 */
	@Override
	public final double computeBeta(final String obj)
	{
		if (idfMap.containsKey(obj) && (sw != null && sw.isStopWord(obj)))
			return idfMap.get(obj);
		else
			return 0;
	}

	/**
	 * @see uk.ac.shef.importance.framework.AlphaBetaComputer#scoreObject(java.lang.Object)
	 */
	@Override
	public final double scoreObject(final String obj)
	{
		return computeAlpha(obj) * computeBeta(obj);
	}

	/**
	 * Method for building the IDF Map from a suitable input stream - should
	 * point to a file containing terms and idf values (separated by whitespace)
	 * 
	 * @param stream
	 *            The IDF File input stream
	 * @return A map pointing from the word to the IDF score
	 */
	private Map<String, Double> readIDFMap(final InputStream stream, String separator)
	{
		Map<String, Double> tIDFMap = new TreeMap<String, Double>();

		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			for (String line = reader.readLine(); line != null; line = reader.readLine())
			{
				String[] elems = line.trim().split(separator);
				if (elems.length == 2)
					tIDFMap.put(elems[0], Double.parseDouble(elems[1]));
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return tIDFMap;
	}
}
