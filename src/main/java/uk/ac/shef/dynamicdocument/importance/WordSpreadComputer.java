package uk.ac.shef.dynamicdocument.importance;

import java.util.List;

import uk.ac.shef.dynamicdocument.Word;
import uk.ac.shef.dynamicdocument.WordContainer;

public class WordSpreadComputer implements ImportanceComputer
{

	int randIndex = -1;
	
	public int getIndex()
	{
		return randIndex;
	}
	
	boolean found = false;
	
	
	public boolean isFound()
	{
		return found;
	}

	String word;
	
	public String toString()
	{
		return "WSC: " + word + " @ " + randIndex;
	}
	
	public WordSpreadComputer(WordContainer in, String word)
	{
		this.word = word;
		List<Word> words = in.collapseToWords();
		
		for(int i = 0 ; i < words.size() ; i++)
			if (words.get(i).getText().equalsIgnoreCase(word))
			{
				randIndex = i;
				break;
			}
		
		if (randIndex == -1)
		{
			found = false;
		}
	}
	
	public WordSpreadComputer(WordContainer in, String word, int count)
	{
		this.word = word;
		List<Word> words = in.collapseToWords();
		
		for(int i = 0 ; i < words.size() ; i++)
			if (words.get(i).getText().equalsIgnoreCase(word))
			{
				if (count == 0)
				{
					randIndex = i;
					found = true;
					break;
				}
				else
					count--;
			}
		
		if (randIndex == -1)
		{
			found = false;
		}
	}
	
	public WordContainer computeImportance(WordContainer in)
	{
		//Get a list of words
		List<Word> words = in.collapseToWords();
		
		//Set the chosen word to be of maximal importance
		words.get(randIndex).setAlpha(1.0);
		words.get(randIndex).setBeta(1.0);
		words.get(randIndex).setManipulationLevel(Word.ALWAYS_SHOW);
		
		//double maxDiff = Math.max(randIndex,words.size()-randIndex)+1;
		double maxDiff = words.size();
		
		//Spread out from this work in increasing importance
		for(int i = randIndex+1 ; i < words.size() ; i++)
		{
			words.get(i).setAlpha(1.0);
			words.get(i).setBeta((i-randIndex)/maxDiff);
			words.get(i).setManipulationLevel(Word.ALWAYS_APPLY);
		}
		for(int i = randIndex-1 ; i >= 0 ; i--)
		{
			words.get(i).setAlpha(1.0);
			words.get(i).setBeta((randIndex-i)/maxDiff);
			words.get(i).setManipulationLevel(Word.ALWAYS_APPLY);
		}

		
		return in;
	}
	
	public WordContainer computeUnionImportance(WordContainer in)
	{
		//Get a list of words
		List<Word> words = in.collapseToWords();
		
		//Set the chosen word to be of maximal importance
		words.get(randIndex).setAlpha(1.0);
		words.get(randIndex).setBeta(1.0);
		words.get(randIndex).setManipulationLevel(Word.ALWAYS_SHOW);
		
		double maxDiff = Math.max(randIndex,words.size()-randIndex)+1;
		
		//Spread out from this work in increasing importance
		for(int i = randIndex+1 ; i < words.size() ; i++)
		{
			if (words.get(i).getManipulationLevel() != Word.ALWAYS_SHOW)
			{
				words.get(i).setUnionAlpha(1.0);
				words.get(i).setUnionBeta((i-randIndex)/maxDiff);
				words.get(i).setManipulationLevel(Word.ALWAYS_APPLY);
			}
		}
		for(int i = randIndex-1 ; i >= 0 ; i--)
		{
			if (words.get(i).getManipulationLevel() != Word.ALWAYS_SHOW)
			{
				words.get(i).setUnionAlpha(1.0);
				words.get(i).setUnionBeta((randIndex-i)/maxDiff);
				words.get(i).setManipulationLevel(Word.ALWAYS_APPLY);
			}
		}

		
		return in;
	}

}
