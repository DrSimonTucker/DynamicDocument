package uk.ac.shef.importance.tfidf;

public interface IDFComputer<X>
{
	public int getMaxIDF();
	public int getIDF(X obj);
}
