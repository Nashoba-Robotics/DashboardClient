package edu.nr.util;

import edu.nr.Components.FieldView;
import edu.nr.Components.extras.TabListener;
import edu.nr.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author co1in
 *         Date: 3/11/14
 *         Time: 4:20 PM
 */
public class FieldFrame extends JFrame implements Runnable
{
	private FieldView fieldView;
	JTextField yField, xField, angleField;
	JLabel x, y, a;

	public FieldFrame()
	{
		super("FieldCentric Properties");

		this.fieldView = Main.fieldCentric;

		setLayout(new GridLayout(3, 2));

		Dimension d = new Dimension(100, 30);

		TabListener l = new TabListener(this);

		x = new JLabel("  Start X Position");
		add(x);

		xField = new JTextField();
		xField.addKeyListener(l);
		xField.setPreferredSize(d);
		xField.setFocusTraversalKeysEnabled(false);
		xField.setText(String.valueOf(fieldView.getStart().x));
		add(xField);

		y = new JLabel("  Start Y Position");
		add(y);

		yField = new JTextField();
		yField.addKeyListener(l);
		yField.setPreferredSize(d);
		yField.setFocusTraversalKeysEnabled(false);
		yField.setText(String.valueOf(fieldView.getStart().y));
		add(yField);

		a = new JLabel("  Start Angle Position");
		add(a);

		angleField = new JTextField();
		angleField.setFocusTraversalKeysEnabled(false);
		angleField.setPreferredSize(d);
		angleField.addKeyListener(l);
		angleField.setText(String.valueOf(fieldView.getStartAngle()));
		add(angleField);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void run()
	{
		try
		{
			fieldView.setStart(new Point(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText())), Integer.parseInt(angleField.getText()));
		}
		catch (NumberFormatException e)
		{
			System.err.println("Error: invalid field start location");
		}
	}
}
