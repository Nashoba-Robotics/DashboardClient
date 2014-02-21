package edu.nr.Components;

import edu.nr.properties.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 2/21/14
 *         Time: 8:58 AM
 */
public class BooleanBox extends MovableComponent
{
	JPanel colorPanel;
	JLabel title;
	public BooleanBox(ArrayList<Property> properties, boolean addingFromSave)
	{
		super(properties, addingFromSave);

		colorPanel = new JPanel();
		title = new JLabel();
	}

	@Override
	public void setMovable(boolean movable)
	{

	}

	@Override
	protected ArrayList<Property> getDefaultProperties()
	{
		ArrayList<Property> tempProperties = new ArrayList<Property>();
		tempProperties.add(new Property(Property.Type.SIZE, new Dimension(130,25)));
		tempProperties.add(new Property(Property.Type.LOCATION, new Point(0,0)));
		tempProperties.add(new Property(Property.Type.BACKGROUND, Color.WHITE));
		tempProperties.add(new Property(Property.Type.NAME, "Text Name"));
		tempProperties.add(new Property(Property.Type.WIDGET_TYPE, 1));
		tempProperties.add(new Property(Property.Type.FONT_SIZE, 14));

		return tempProperties;
	}

	@Override
	public void applyProperties(boolean setSize)
	{

	}

	@Override
	public String getWidgetName()
	{
		return null;
	}

	@Override
	public Object getValue()
	{
		return null;
	}

	@Override
	public String getTitle()
	{
		return null;
	}

	@Override
	public int getWidgetType()
	{
		return 0;
	}

	@Override
	public void attemptValueFetch()
	{

	}

	@Override
	public String[] getWidgetChoices()
	{
		return new String[0];
	}
}
