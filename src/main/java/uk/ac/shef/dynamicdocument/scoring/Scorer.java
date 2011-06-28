package uk.ac.shef.dynamicdocument.scoring;

import uk.ac.shef.dynamicdocument.DocumentTree;

public interface Scorer
{
	public void scoreDocument(DocumentTree tree);

	public void setStemmer(Stemmer stem);
}
