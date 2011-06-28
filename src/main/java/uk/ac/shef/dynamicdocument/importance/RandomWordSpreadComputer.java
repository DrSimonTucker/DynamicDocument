package uk.ac.shef.dynamicdocument.importance;

import java.util.List;

import uk.ac.shef.dynamicdocument.Word;
import uk.ac.shef.dynamicdocument.WordContainer;

public class RandomWordSpreadComputer implements ImportanceComputer
{

	int randIndex = -1;

	public RandomWordSpreadComputer(WordContainer in)
	{
		List<Word> words = in.collapseToWords();
		// Pick one at random if we need to - make sure its got more than three
		// characters though
		do
			if (randIndex == -1 || words.get(randIndex).getText().length() < 3)
				randIndex = (int) (Math.random() * words.size());
		while (words.get(randIndex).getText().length() < 3);
	}

	public WordContainer computeImportance(WordContainer in)
	{
		// Get a list of words
		List<Word> words = in.collapseToWords();

		// Set the chosen word to be of maximal importance
		words.get(randIndex).setAlpha(1.0);
		words.get(randIndex).setBeta(1.0);
		words.get(randIndex).setManipulationLevel(Word.ALWAYS_SHOW);

		double maxDiff = Math.max(randIndex, words.size() - randIndex) + 1;

		// Spread out from this work in increasing importance
		for (int i = randIndex + 1; i < words.size(); i++)
		{
			words.get(i).setAlpha(1.0);
			words.get(i).setBeta((i - randIndex) / maxDiff);
		}
		for (int i = randIndex - 1; i >= 0; i--)
		{
			words.get(i).setAlpha(1.0);
			words.get(i).setBeta((randIndex - i) / maxDiff);
		}

		return in;
	}

	public int getIndex()
	{
		return randIndex;
	}

}
