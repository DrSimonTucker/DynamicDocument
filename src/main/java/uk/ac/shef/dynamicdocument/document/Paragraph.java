package uk.ac.shef.dynamicdocument.document;

import uk.ac.shef.dynamicdocument.DocumentTree;
import uk.ac.shef.dynamicdocument.DocumentWordGenerator;

public class Paragraph extends DocumentTree
{
	public Paragraph(String in)
	{
		addSubTree(new Sentence(in));
		addSubTree(DocumentWordGenerator.generateCarriageReturn());
		addSubTree(DocumentWordGenerator.generateCarriageReturn());
	}

	public void addSentence(Sentence in)
	{
		addSubTree(in);
	}
}
