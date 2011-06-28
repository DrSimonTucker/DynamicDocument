package uk.ac.shef.importance.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;

public class StopWord implements StopWordComputer<String>
{
	private static StopWord singleton;

	private static String sync = "SYNC";

	private final Set<String> stopWords = new TreeSet<String>();

	private StopWord()
	{
		// Blocking constructor
	}

	public boolean isStopWord(String elem)
	{
		return stopWords.contains(elem);
	}

	private void buildStopWords(File f) throws IOException
	{
		buildStopWords(new FileInputStream(f));
	}

	private void buildStopWords(InputStream str) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(str));
		for (String line = reader.readLine(); line != null; line = reader
				.readLine())
			stopWords.add(line.trim());
		reader.close();
	}

	public static StopWord getStopWords(File stopwordLocation)
	{
		synchronized (sync)
		{
			if (singleton == null)
			{
				singleton = new StopWord();

				try
				{
					singleton.buildStopWords(stopwordLocation);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return singleton;
	}

	public static StopWord getStopWords(InputStream str)
	{
		synchronized (sync)
		{
			if (singleton == null)
			{
				singleton = new StopWord();
				try
				{
					singleton.buildStopWords(str);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return singleton;
	}
}
