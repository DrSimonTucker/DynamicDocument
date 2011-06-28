package uk.ac.shef.dynamicdocument.scoring;

public class BasicStemmer implements Stemmer
{

	@Override
	public String stem(String word)
	{
		return word;
	}

}
