package edu.nr.Components;

import edu.nr.Components.extras.WidgetInfo;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.util.Printer;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 11/23/13
 *         Time: 11:47 PM
 */
public class NNumberField extends MovableField
{
    private double value;

    public NNumberField(ArrayList<Property> properties, boolean addingFromSave)
    {
        super(properties, addingFromSave);
    }

    private void loadProperties(ArrayList<Property> loading)
    {
        properties = getDefaultProperties();
        PropertiesManager.loadPropertiesIntoArray(properties, loading);
    }

    protected ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Property.Type.SIZE, new Dimension(130,25)));
        tempProperties.add(new Property(Property.Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Property.Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Property.Type.BACKGROUND, Color.WHITE));
        tempProperties.add(new Property(Property.Type.NAME, "Number"));
        tempProperties.add(new Property(Property.Type.FONT_SIZE, 12));

        return tempProperties;
    }

    @Override
    public String getWidgetName()
    {
        return WidgetInfo.NUMBER_NAME;
    }

	@Override
	public Object getValue()
	{
		return value;
	}


	@Override
    public void setValue(Object o)
    {
        super.setValue(o);
        if(o.getClass() == java.lang.Double.class)
            value = (Double)o;
        else
            Printer.println("Couldn't get class for: " + o.getClass());
        field.setText(String.valueOf(value));
    }

    @Override
    public int getWidgetType()
    {
        return 1;
    }

    @Override
    public void attemptValueFetch()
    {
        Double d = main.network.getNumber(getTitle());
        if(d != null)
            setValue(d);
    }

	@Override
	public String[] getWidgetChoices()
	{
		return WidgetInfo.numberNames;
	}

	@Override
    protected void onTab()
    {
        Double newValue = null;
        try
        {
            newValue = Double.parseDouble(field.getText());
            main.network.putNumber(getTitle(), newValue);
        }
        catch (NumberFormatException e)
        {
            if(valueSet)
            {
                field.setText(value + "");
                NNumberField.this.main.network.putNumber(getTitle(), value);
            }
            else
                field.setText("");
            return;
        }
    }
}
