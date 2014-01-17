package edu.nr.Components;

import edu.nr.Components.mouse_listeners.MyMouseListener;
import edu.nr.Main;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.util.Printer;

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
    private int widgetType = -1;
    private double number;

    public NNumberField(ArrayList<MovableComponent> components, ArrayList<Property> properties, Main main)
    {
        this.components = components;
        this.number = number;
        numberField = new JTextField();
        numberField.setText("" + number);
        label = new JLabel();

        loadProperties(properties);

        setLayout(new BorderLayout());

        MyMouseListener listener = new MyMouseListener(this, components, main);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        numberField.addMouseListener(listener);
        numberField.addMouseMotionListener(listener);
    }

    private void loadProperties(ArrayList<Property> loading)
    {
        properties = getDefaultProperties();
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
        if(o.getClass() == java.lang.Double.class)
            value = (Double)o;
        else if(o.getClass() == Integer.class)
            value = ((Integer)o).floatValue();
        else
            Printer.println("Couldn't get class for: " + o.getClass());
        numberField.setText(String.valueOf(value));
    }

    @Override
    public String getTitle()
    {
        return label.getText();
    }
}
