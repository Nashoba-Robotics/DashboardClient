package edu.nr.Components;

import edu.nr.Components.mouse_listeners.MyMouseListener;
import edu.nr.Main;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.util.Printer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 11/23/13
 *         Time: 11:47 PM
 */
public class NNumberField extends MovableField
{
    private double value;

    public NNumberField(ArrayList<MovableComponent> components, ArrayList<Property> properties, Main main, boolean addingFromSave)
    {
        super(components, properties, main, addingFromSave);
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
        tempProperties.add(new Property(Property.Type.FONT_SIZE, 14));

        return tempProperties;
    }

    @Override
    public String getWidgetName()
    {
        return WidgetNames.NUMBER_NAME;
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
    protected void onTab()
    {
        Double newValue = null;
        try
        {
            newValue = Double.parseDouble(field.getText());
            NNumberField.this.main.network.putNumber(getTitle(), newValue);
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
