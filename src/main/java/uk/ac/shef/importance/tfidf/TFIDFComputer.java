package uk.ac.shef.importance.tfidf;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TFIDFComputer<X, Y extends Comparable<Y>>
{
	StopWordComputer<Y> comp;
	TFIDFConverter<X, Y> converter;
	// IDFSupplementor<Y> supplementor;

	Map<Y, Double> documentFrequency = new TreeMap<Y, Double>();
	Map<Y, Double> termFrequency = new TreeMap<Y, Double>();

	public void buildTFIDFFile(Collection<X> documents, X doc, OutputStream outStream, StopWordComputer<Y> swc,
			TFIDFConverter<X, Y> converter)
	{
		this.comp = swc;
		this.converter = converter;

		computeTermFrequency(doc);
		computeDocumentFrequency(documents);

		PrintWriter writer = new PrintWriter(new OutputStreamWriter(outStream));
		for (Y term : termFrequency.keySet())
		{
			double tf = Math.log(termFrequency.get(term) + 1) / Math.log(termFrequency.keySet().size());
			double idf = Math.log(documents.size() / documentFrequency.get(term));

			writer.println(term + " " + tf + " " + idf);
			// writer.println(tf*idf + " " + term);
		}
		writer.close();
	}

	public void buildTFIDFFile(Collection<X> documents, X doc, OutputStream outStream, StopWordComputer<Y> swc,
			TFIDFConverter<X, Y> converter, IDFComputer<Y> supp)
	{
		this.comp = swc;
		this.converter = converter;

		computeTermFrequency(doc);
		computeDocumentFrequency(documents);

		PrintWriter writer = new PrintWriter(new OutputStreamWriter(outStream));
		for (Y term : termFrequency.keySet())
		{
			double tf = Math.log(termFrequency.get(term) + 1) / Math.log(termFrequency.keySet().size());

			// Compute the idf using the supplementary computer
			double idf = Math.log((supp.getMaxIDF() + 0.0) / supp.getIDF(term));

			writer.println(term + " " + tf + " " + idf);
		}
		writer.close();
	}

	public void computeDocumentFrequency(Collection<X> elements)
	{
		for (X document : elements)
		{
			Set<Y> docReps = new TreeSet<Y>();
			for (Y elem : converter.getElements(document))
				if (!comp.isStopWord(elem))
					docReps.add(elem);

			for (Y elem : docReps)
			{
				if (!documentFrequency.containsKey(elem))
					documentFrequency.put(elem, 0.0);
				documentFrequency.put(elem, documentFrequency.get(elem) + 1.0);
			}
		}
	}

	public void computeTermFrequency(X doc)
	{
		for (Y elem : converter.getElements(doc))
			if (!comp.isStopWord(elem))
			{
				if (!termFrequency.containsKey(elem))
					termFrequency.put(elem, 0.0);
				termFrequency.put(elem, termFrequency.get(elem) + 1.0);
			}
	}
}
