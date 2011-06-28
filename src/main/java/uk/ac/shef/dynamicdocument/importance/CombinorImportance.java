package uk.ac.shef.dynamicdocument.importance;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.ac.shef.dynamicdocument.Word;
import uk.ac.shef.dynamicdocument.WordContainer;

public class CombinorImportance implements ImportanceComputer
{
	public int getSize()
	{
		return indexMapper.keySet().size();
	}
	
	public WordContainer computeUnionImportance(WordContainer in)
	{
		List<Word> words = in.collapseToWords();
		for (Word word : words) 
		{
			word.setAlpha(1.0);
			word.setBeta(1.0);
			word.setManipulationLevel(Word.ALWAYS_APPLY);
		}
		
		for (WordSpreadComputer comp : indexMapper.values())
			comp.computeUnionImportance(in);
		
		return in;
	}
	
	public WordContainer computeImportance(WordContainer in)
	{
		//		Get a list of words
		List<Word> words = in.collapseToWords();
		
		for (int i = 0 ; i < words.size() ; i++)
			if (indexMapper.containsKey(i))
			{
				words.get(i).setAlpha(1.0);
				words.get(i).setBeta(1.0);
				words.get(i).setManipulationLevel(Word.ALWAYS_SHOW);
			}
			else
			{
				words.get(i).setAlpha(0.0);
				words.get(i).setBeta(0.0);
				words.get(i).setManipulationLevel(Word.ALWAYS_APPLY);
			}
		
		return in;
	}

	Map<Integer, WordSpreadComputer> indexMapper = new TreeMap<Integer, WordSpreadComputer>();
		
	public void clear()
	{
		indexMapper.clear();
	}
	
	public ImportanceComputer getImp(int in)
	{
		if (indexMapper.containsKey(in))
		{
			return indexMapper.get(in);
		}
		else
			return null;
	}
	
	public void addImportanceComputer(WordSpreadComputer in)
	{
		indexMapper.put(in.getIndex(), in);
	}
}
