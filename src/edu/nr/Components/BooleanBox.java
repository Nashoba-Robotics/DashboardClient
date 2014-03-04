package edu.nr.Components;

import edu.nr.Components.extras.WidgetInfo;
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
	private boolean value;
	private final int BOX_MIN_DIMEN = 25;

	public BooleanBox(ArrayList<Property> properties, boolean addingFromSave)
	{
		super(properties, addingFromSave);

		colorPanel = new JPanel();
		title = new JLabel();

		add(colorPanel);
		add(title);

		applyProperties(addingFromSave);
	}

	@Override
	public void setMovable(boolean movable)
	{
		isMovable = movable;
	}

	@Override
	protected ArrayList<Property> getDefaultProperties()
	{
		ArrayList<Property> tempProperties = new ArrayList<Property>();
		tempProperties.add(new Property(Property.Type.SIZE, new Dimension(130,25)));
		tempProperties.add(new Property(Property.Type.LOCATION, new Point(0,0)));
		tempProperties.add(new Property(Property.Type.BACKGROUND, Color.WHITE));
		tempProperties.add(new Property(Property.Type.NAME, "Boolean Name"));
		tempProperties.add(new Property(Property.Type.WIDGET_TYPE, 2));
		tempProperties.add(new Property(Property.Type.FONT_SIZE, 14));

		return tempProperties;
	}

	@Override
	public void applyProperties(boolean setSize)
	{
		title.setText(" " + Property.getPropertyFromType(Property.Type.NAME, properties).getData() + " ");
		if(setSize)
			setSize((Dimension)Property.getPropertyFromType(Property.Type.SIZE, properties).getData());
		else
		{
			int textWidth = title.getPreferredSize().width;
			//Use the preferred width of the views, and the height from our default properties
			setSize(new Dimension(textWidth + BOX_MIN_DIMEN, ((Dimension)Property.getPropertyFromType(Property.Type.SIZE, properties).getData()).height));
		}
		setBackground((Color) Property.getPropertyFromType(Property.Type.BACKGROUND, properties).getData());
		setLocation((Point) Property.getPropertyFromType(Property.Type.LOCATION, properties).getData());

		title.setFont(new Font("Arial", Font.BOLD, (Integer) Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData() - 1));
	}

	@Override
	public String getWidgetName()
	{
		return WidgetInfo.BOOLEAN_NAME;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public String getTitle()
	{
		return title.getText().trim();
	}

	@Override
	public int getWidgetType()
	{
		return 2;
	}

	@Override
	public void attemptValueFetch()
	{
		Boolean b = main.network.getBoolean(getTitle());
		if(b != null)
			setValue(b);
	}

	@Override
	public String[] getWidgetChoices()
	{
		return WidgetInfo.booleanNames;
	}

	@Override
	public void setValue(Object o)
	{
		super.setValue(o);
		Boolean b = (Boolean)o;
		this.value = b;
		applyValue();
	}

	private void applyValue()
	{
		if(value)
		{
			colorPanel.setBackground(new Color(0,150,0));
		}
		else
		{
			colorPanel.setBackground(new Color(250,0,0));
		}
	}
}
