package edu.nr.Components.extras;

import edu.nr.properties.Property;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 2/9/14
 *         Time: 12:06 PM
 */
public class PropertiesEditor extends JFrame
{
	private ArrayList<Property> properties;
	public PropertiesEditor(ArrayList<Property> properties)
	{
		super("Edit Properties");
		this.properties = properties;
	}

	private class TitleEntry extends JPanel
	{
		private String title;
		public TitleEntry(Property property)
		{
			this.title = title;
			JLabel label = new JLabel(title);
			add(label, BorderLayout.WEST);
			setBorder(new LineBorder(Color.BLACK, 1));
		}
	}

	private class PropertyEntry extends JPanel
	{
		private Property value;
		public PropertyEntry(Property value)
		{
			this.value = value;
		}
	}
}
