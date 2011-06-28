package uk.ac.shef.importance.tfidf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This package computes IDF scores using wikipedia as a guide
 * 
 * @author simon
 * 
 */
public class WikiIDFComputer implements IDFComputer<String>
{
	public int getIDF(String word)
	{
		int count = 1;

		// Use UNIX grep to get the count!
		String file = "/home/simon/data/NonBackup/Wikipedia/countfs.txt";

		try
		{
			String comm = "grep ." + word + "$ " + file;
			Process p = Runtime.getRuntime().exec(comm);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			for (String line = stdInput.readLine(); line != null; line = stdInput.readLine())
				if (line.split("\\s+")[1].equalsIgnoreCase(word))
					count = Integer.parseInt(line.split("\\s+")[0]);
			stdInput.close();

			if (count == -1)
				for (String line = stdError.readLine(); line != null; line = stdError.readLine())
					count = Integer.parseInt(line.split("\\s+")[0]);
			stdError.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return count;
	}

	public int getMaxIDF()
	{
		return 57824889;
	}
}
