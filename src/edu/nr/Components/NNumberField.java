package edu.nr.Components;

import edu.nr.MovableComponent;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 11/23/13
 *         Time: 11:47 PM
 */
public class NNumberField extends MovableComponent
{
    private ArrayList<MovableComponent> components;
    private double value;
    private JTextField numberField;
    private JLabel label;
    private JProgressBar progressBar;
    private int widgetType = -1;
    private int maxValue = 0, minValue = 100;

    public NNumberField(ArrayList<MovableComponent> components, ArrayList<Property> properties)
    {
        this.components = components;
        numberField = new JTextField();
        label = new JLabel();
        progressBar = new JProgressBar();

        loadProperties(properties);

        setLayout(new BorderLayout());
    }

    private void loadProperties(ArrayList<Property> loading)
    {
        if(properties == null)
        {
            properties = getDefaultProperties();
        }
        PropertiesManager.loadPropertiesIntoArray(properties, loading);
    }

    private ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Property.Type.SIZE, new Dimension(130,45)));
        tempProperties.add(new Property(Property.Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Property.Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Property.Type.BACKGROUND, Color.WHITE));
        tempProperties.add(new Property(Property.Type.NAME, "Number"));
        tempProperties.add(new Property(Property.Type.WIDGET_TYPE, 1));
        tempProperties.add(new Property(Property.Type.FONT_SIZE, 14));

        return tempProperties;
    }

    @Override
    public void setMovable(boolean movable)
    {
        isMovable = movable;
        numberField.setEnabled(!movable);
    }

    @Override
    public void applyProperties()
    {
        applyProperties(properties);
    }

    public void applyProperties(ArrayList<Property> applyingProperties)
    {
        PropertiesManager.loadPropertiesIntoArray(properties, applyingProperties);

        widgetType = (Integer)Property.getPropertyFromType(Property.Type.WIDGET_TYPE, properties).getData();
        if(widgetType == 1)
        {
            this.removeAll();
            this.add(label, BorderLayout.NORTH);
            this.add(numberField, BorderLayout.SOUTH);
        }
        else if(widgetType == 2)
        {
            this.removeAll();
            this.add(label, BorderLayout.NORTH);
            this.add(progressBar, BorderLayout.SOUTH);
        }
        label.setText((String)Property.getPropertyFromType(Property.Type.NAME, properties).getData());
        setBackground((Color)Property.getPropertyFromType(Property.Type.BACKGROUND, properties).getData());
        setSize((Dimension) Property.getPropertyFromType(Property.Type.SIZE, properties).getData());
        setLocation((Point)Property.getPropertyFromType(Property.Type.LOCATION, properties).getData());
        numberField.setForeground((Color)Property.getPropertyFromType(Property.Type.FOREGROUND, properties).getData());

        label.setFont(new Font("Arial", Font.BOLD, (Integer)Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData()-1));
        numberField.setFont(new Font("Arial", Font.PLAIN, (Integer)Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData()));
    }

    @Override
    public String getWidgetName()
    {
        return "number";
    }

    @Override
    public void applyWidgetType()
    {

    }

    @Override
    public void setValue(Object o)
    {
        value = (Double)o;
        numberField.setText(String.valueOf(value));
        progressBar.setValue((int)value);
    }
}
