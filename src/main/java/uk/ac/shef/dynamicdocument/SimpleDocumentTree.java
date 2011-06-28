package uk.ac.shef.dynamicdocument;

public class SimpleDocumentTree extends DocumentTree
{
	public SimpleDocumentTree()
	{

		DocumentWord word = new DocumentWord("I");
		DocumentWord word2 = new DocumentWord("am");
		DocumentWord word3 = new DocumentWord("simple");

		this.addSubTree(new SimpleDocumentPreamble("DONKEY:"));
		this.addSubTree(word);
		this.addSubTree(DocumentWordGenerator.generateSpace());
		this.addSubTree(word2);
		this.addSubTree(DocumentWordGenerator.generateSpace());
		this.addSubTree(word3);
		this.addSubTree(DocumentWordGenerator.generateCarriageReturn());
		this.addSubTree(new SimpleDocumentStatement("STATEY", 0.5));
		// this.addSubTree(word3);
		// this.addSubTree(word4);
		// this.addSubTree(word5);
	}
}

class SimpleDocumentPreamble extends DocumentTree
{
	public SimpleDocumentPreamble(String text)
	{
		this.setShowProcedure(DocumentTree.SHOW_WITH_SIBLINGS);

		DocumentWord word = new DocumentWord(text);
		this.addSubTree(word);
		this.addSubTree(DocumentWordGenerator.generateSpace());
	}
}

class SimpleDocumentStatement extends DocumentTree
{
	public SimpleDocumentStatement(String text, double rank)
	{
		DocumentWord word = new DocumentWord(text);
		word.setHorizontalRank(rank);

		this.addSubTree(word);
		this.addSubTree(DocumentWordGenerator.generateCarriageReturn());
	}
}
