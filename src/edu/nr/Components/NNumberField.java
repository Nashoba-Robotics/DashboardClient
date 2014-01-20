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
public class NNumberField extends MovableComponent
{
    private ArrayList<MovableComponent> components;
    private double value;
    private JTextField numberField;
    private JLabel label;

    public NNumberField(ArrayList<MovableComponent> components, ArrayList<Property> properties, Main main)
    {
        this.components = components;
        this.main = main;
        numberField = new JTextField();
        label = new JLabel();
        label.setBorder(new EmptyBorder(0,0,0,0));

        loadProperties(properties);

        MyMouseListener listener = new MyMouseListener(this, components, main);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        numberField.addMouseListener(listener);
        numberField.addMouseMotionListener(listener);

        setLayout(new BoxLayout(this, 0));
        setBorder(new EmptyBorder(1, 1, 1, 1));
        add(label);
        add(numberField);
        applyProperties();

        numberField.setFocusTraversalKeysEnabled(false);
        numberField.addKeyListener(new TabListener(new Runnable()
        {
            @Override
            public void run()
            {
                Double newValue = null;
                try
                {
                    newValue = Double.parseDouble(numberField.getText());
                }
                catch (NumberFormatException e)
                {
                    if(valueSet)
                    {
                        numberField.setText(value + "");
                        NNumberField.this.main.network.putNumber(getTitle(), value);
                    }
                    else
                    {
                        numberField.setText("");
                    }
                    return;
                }
                NNumberField.this.main.network.putNumber(getTitle(), newValue);
            }
        }));
    }

    private void loadProperties(ArrayList<Property> loading)
    {
        properties = getDefaultProperties();
        PropertiesManager.loadPropertiesIntoArray(properties, loading);
    }

    private ArrayList<Property> getDefaultProperties()
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

        label.setText(" " + (String)Property.getPropertyFromType(Property.Type.NAME, properties).getData() + " ");
        setBackground((Color) Property.getPropertyFromType(Property.Type.BACKGROUND, properties).getData());
        setSize((Dimension) Property.getPropertyFromType(Property.Type.SIZE, properties).getData());
        setLocation((Point)Property.getPropertyFromType(Property.Type.LOCATION, properties).getData());
        numberField.setForeground((Color)Property.getPropertyFromType(Property.Type.FOREGROUND, properties).getData());

        label.setFont(new Font("Arial", Font.BOLD, (Integer)Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData()-1));
        numberField.setFont(new Font("Arial", Font.PLAIN, (Integer)Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData()));
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
        numberField.setText(String.valueOf(value));
    }

    @Override
    public String getTitle()
    {
        return label.getText().trim();
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
}
