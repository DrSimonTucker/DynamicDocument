package uk.ac.shef.dynamicdocument.document;

import uk.ac.shef.dynamicdocument.DocumentTree;

public class Document extends DocumentTree
{
	public void addParagraph(Paragraph in)
	{
		addSubTree(in);
	}
}
