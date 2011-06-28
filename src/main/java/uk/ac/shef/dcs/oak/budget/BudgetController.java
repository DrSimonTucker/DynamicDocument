package uk.ac.shef.dcs.oak.budget;

import java.io.File;

import uk.ac.shef.dynamicdocument.document.Document;

public class BudgetController
{
	public void run()
	{
		// Load the data
		System.out.println("Parsing document");
		Document doc = SimpleDocumentProducer.produceDocument(new File(
				"resources/speeches/main.2010.txt"));
		BudgetView view = new BudgetView(doc);
		view.setVisible(true);
	}

	public static void main(String[] args)
	{
		BudgetController cont = new BudgetController();
		cont.run();
	}
}
