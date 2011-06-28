package uk.ac.shef.dynamicdocument;

public class DocumentWordGenerator
{
	public static DocumentWord generateCarriageReturn()
	{
		DocumentWord dWord = new DocumentWord("\n");
		dWord.setScoreProcedure(DocumentTree.IGNORE);
		dWord.setShowProcedure(DocumentTree.SHOW_WITH_SIBLINGS);
		return dWord;
	}

	public static DocumentWord generateSpace()
	{
		DocumentWord dWord = new DocumentWord(" ");
		dWord.setScoreProcedure(DocumentTree.IGNORE);
		dWord.setShowProcedure(DocumentTree.SHOW_WITH_PREVIOUS);
		return dWord;
	}

	public static DocumentWord generateConnector()
	{
		DocumentWord dWord = new DocumentWord(" ");
		dWord.setScoreProcedure(DocumentTree.IGNORE);
		dWord.setShowProcedure(DocumentTree.SHOW_WITH_PREVIOUS);
		return dWord;
	}

	public static DocumentWord generatePunc(String punc)
	{
		DocumentWord dWord = new DocumentWord(punc);
		dWord.setScoreProcedure(DocumentTree.IGNORE);
		dWord.setShowProcedure(DocumentTree.SHOW_WITH_PREVIOUS);
		return dWord;
	}
}
