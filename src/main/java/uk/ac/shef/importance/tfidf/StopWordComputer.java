package uk.ac.shef.importance.tfidf;

public interface StopWordComputer<Y>
{
	public boolean isStopWord(Y elem);
}
