package uk.ac.shef.dcs.oak.budget;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import uk.ac.shef.dynamicdocument.document.Document;
import uk.ac.shef.dynamicdocument.gui.DynamicDocumentPanel;
import uk.ac.shef.dynamicdocument.modifier.BlankTextModifier;
import uk.ac.shef.dynamicdocument.modifier.WordShaderModifier;

public class BudgetView extends JFrame
{
	DynamicDocumentPanel docPanel;

	public BudgetView(Document doc)
	{
		docPanel = new DynamicDocumentPanel();
		docPanel.setDocument(doc);
		docPanel
				.setModifiers(new BlankTextModifier(), new WordShaderModifier());

		JScrollPane scroller = new JScrollPane(docPanel);
		scroller
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scroller, BorderLayout.CENTER);
		final JSlider slider = new JSlider();
		slider.setValue(100);
		slider.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				double nVal = slider.getValue() / 100.0;
				docPanel.setGamma(0.0, nVal);
			}
		});
		this.add(slider, BorderLayout.SOUTH);

		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
	}
}
