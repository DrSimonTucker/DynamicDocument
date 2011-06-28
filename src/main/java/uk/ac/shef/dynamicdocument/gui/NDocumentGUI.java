package uk.ac.shef.dynamicdocument.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.ac.shef.dynamicdocument.DocumentTree;
import uk.ac.shef.dynamicdocument.SimpleDocumentTree;
import uk.ac.shef.dynamicdocument.modifier.ExcisionModifier;
import uk.ac.shef.dynamicdocument.modifier.Modifier;

public class NDocumentGUI extends JFrame implements ChangeListener
{
	/**
	 * Generated serial ID
	 */
	private static final long serialVersionUID = 7390426386111839566L;
	Modifier dispModifier = new ExcisionModifier();
	DynamicDocumentPanel panel = new DynamicDocumentPanel();

	JSlider slider;

	public NDocumentGUI()
	{
		initGUI();
	}

	public void setDocument(DocumentTree dynDisc)
	{
		panel.setDocument(dynDisc);
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		double gammaValue = 1.0 - (slider.getValue() / 100.0);
		panel.setGamma(gammaValue, gammaValue);
	}

	private void initGUI()
	{
		JScrollPane scroller = new JScrollPane(panel);
		slider = new JSlider(0, 100, 100);
		slider.addChangeListener(this);

		this.getContentPane().add(scroller, BorderLayout.CENTER);
		this.getContentPane().add(slider, BorderLayout.SOUTH);

		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		NDocumentGUI gui = new NDocumentGUI();
		gui.setSize(500, 500);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);

		gui.setDocument(new SimpleDocumentTree());
	}
}
