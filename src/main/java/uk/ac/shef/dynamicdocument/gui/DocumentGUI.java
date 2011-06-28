package uk.ac.shef.dynamicdocument.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DocumentGUI extends JFrame implements ChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DocumentPanel panel;

	JSlider slider;

	public DocumentGUI(DocumentPanel panel)
	{
		this.panel = panel;
		slider = new JSlider();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				layoutComponents();
			}
		});
	}

	public JSlider getSlider()
	{
		return slider;
	}

	public void setVal(int val)
	{
		slider.setValue(val);
	}

	public void stateChanged(ChangeEvent e)
	{
		panel.setGamma(1.0 - ((slider.getValue() + 0.0) / slider.getMaximum()));
	}

	private void layoutComponents()
	{
		slider.setValue(100);
		slider.addChangeListener(this);

		this.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
		this.getContentPane().add(slider, BorderLayout.SOUTH);
	}
}
