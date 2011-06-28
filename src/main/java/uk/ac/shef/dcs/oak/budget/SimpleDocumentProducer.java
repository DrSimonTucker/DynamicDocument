package uk.ac.shef.dcs.oak.budget;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import uk.ac.shef.dynamicdocument.document.Document;
import uk.ac.shef.dynamicdocument.document.Paragraph;

public class SimpleDocumentProducer
{
	public static Document produceDocument(File doc)
	{
		Document ret = new Document();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(doc));
			for (String line = reader.readLine(); line != null; line = reader
					.readLine())
			{
				if (line.trim().length() > 0)
				{
					ret.addParagraph(new Paragraph(line.trim()));
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return ret;
	}
}
