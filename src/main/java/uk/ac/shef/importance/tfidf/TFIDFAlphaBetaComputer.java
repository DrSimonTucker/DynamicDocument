package uk.ac.shef.importance.tfidf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import uk.ac.shef.importance.framework.AlphaBetaComputer;

public class TFIDFAlphaBetaComputer<X> implements AlphaBetaComputer<X>
{
	protected Map<String, Double> idfMap = new TreeMap<String, Double>();

	protected Map<String, Double> tfMap = new TreeMap<String, Double>();

	StopWord sw;

	public TFIDFAlphaBetaComputer()
	{
		// Do nothing
	}

	public TFIDFAlphaBetaComputer(InputStream file)
	{
		readFile(file);
	}

	public double computeAlpha(X obj)
	{
		if (notStopWord(obj.toString()))
			if (tfMap.containsKey(obj.toString()))
				return tfMap.get(obj.toString());

		return 0;
	}

	public double computeBeta(X obj)
	{
		if (notStopWord(obj.toString()))
			if (idfMap.containsKey(obj.toString()))
				return idfMap.get(obj.toString());

		return 1;
	}

	public double scoreObject(X obj)
	{
		if (tfMap.containsKey(obj.toString()))
			return tfMap.get(obj.toString()) * idfMap.get(obj.toString());

		return 1;
	}

	public void setSw(StopWord sw)
	{
		this.sw = sw;
	}

	protected boolean notStopWord(String term)
	{
		return sw == null || !sw.isStopWord(term);
	}

	protected void readFile(InputStream file)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(file));

			for (String line = reader.readLine(); line != null; line = reader.readLine())
			{
				String[] elems = line.split("\\s+");
				String prop = elems[0];
				double tf = Double.parseDouble(elems[1]);
				double idf = Double.parseDouble(elems[2]);

				tfMap.put(prop, tf);
				idfMap.put(prop, idf);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
