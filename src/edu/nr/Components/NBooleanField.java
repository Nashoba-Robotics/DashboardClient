package edu.nr.Components;

import edu.nr.Main;
import edu.nr.properties.Property;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 1/18/14
 *         Time: 11:49 PM
 */
public class NBooleanField extends MovableField
{
    private Boolean currentValue = null;

    public NBooleanField(ArrayList<MovableComponent> components, ArrayList<Property> properties, boolean addingFromSave)
    {
        super(components, properties, addingFromSave);
    }

    @Override
    protected void onTab()
    {
        if(field.getText().compareToIgnoreCase("true") == 0)
            NBooleanField.this.main.network.putBoolean(getTitle(), true);
        else if(field.getText().compareToIgnoreCase("false") == 0)
            NBooleanField.this.main.network.putBoolean(getTitle(), false);
        else
        {
            if(valueSet)
            {
                field.setText(currentValue + "");
                NBooleanField.this.main.network.putBoolean(getTitle(), currentValue);
            }
            else
                field.setText("");
        }
    }

    protected ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Property.Type.SIZE, new Dimension(130,25)));
        tempProperties.add(new Property(Property.Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Property.Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Property.Type.BACKGROUND, Color.WHITE));
        tempProperties.add(new Property(Property.Type.NAME, "Text Name"));
        tempProperties.add(new Property(Property.Type.WIDGET_TYPE, 1));
        tempProperties.add(new Property(Property.Type.FONT_SIZE, 14));

        return tempProperties;
    }

    @Override
    public String getWidgetName()
    {
        return WidgetInfo.BOOLEAN_NAME;
    }

	@Override
	public Object getValue()
	{
		return currentValue;
	}

	@Override
    public void setValue(Object o)
    {
        super.setValue(o);
        field.setText(((Boolean)o).booleanValue() + "");
        currentValue = (Boolean)o;
    }

    @Override
    public int getWidgetType()
    {
        return 1;
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
}
