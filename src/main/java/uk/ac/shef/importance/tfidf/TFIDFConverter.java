package uk.ac.shef.importance.tfidf;

import java.util.Collection;

public interface TFIDFConverter<X,Y extends Comparable<Y>>
{
	public Collection<Y> getElements(X obj);
	public Y postProcess(Y obj);
}
