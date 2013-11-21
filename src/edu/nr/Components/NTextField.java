package edu.nr.Components;

import edu.nr.Components.mouse_listeners.MyMouseListener;
import edu.nr.MovableComponent;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * User: co1in
 * Date: 11/13/13
 * Time: 10:22 PM
 */
public class NTextField extends MovableComponent
{
    private ArrayList<MovableComponent> components;
    private JTextField field;
    private JLabel label;
    private int widgetType = -1;

    public NTextField(ArrayList<MovableComponent> components, ArrayList<Property> loadedProperties)
    {
        this.components = components;
        initPropertiesArray();

        label = new JLabel();
        label.setBorder(new EmptyBorder(0,0,0,0));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        field = new JTextField();
        //field.setPreferredSize(new Dimension(100,25));
        field.setDisabledTextColor(Color.BLACK);

        loadProperties(loadedProperties);
        applyProperties();

        setLayout(new GridLayout(2,1));
        setBorder(new LineBorder(Color.BLACK, 1));
        setBackground(new Color(200,200,200));

        add(label);
        add(field);

        MyMouseListener listener = new MyMouseListener(NTextField.this, components);
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    private void loadProperties(ArrayList<Property> loaded)
    {
        setProperties(getDefaultProperties());
        PropertiesManager.loadPropertiesIntoArray(getProperties(), loaded);
    }

    private void applyProperties()
    {
        //Load the properties by using the Property classes function for getting a specific property out of our array by finding it's type,
        //Then set our values to the correctly casted version of the data from that property
        label.setText((String)Property.getPropertyFromType(Property.Type.NAME, getProperties()).getData());
        field.setText((String)Property.getPropertyFromType(Property.Type.VALUE, getProperties()).getData());
        field.setForeground((Color)Property.getPropertyFromType(Property.Type.FOREGROUND, getProperties()).getData());
        setBackground((Color)Property.getPropertyFromType(Property.Type.BACKGROUND, getProperties()).getData());
        setSize((Dimension) Property.getPropertyFromType(Property.Type.SIZE, getProperties()).getData());
        setLocation((Point)Property.getPropertyFromType(Property.Type.LOCATION, getProperties()).getData());
        widgetType = (Integer)Property.getPropertyFromType(Property.Type.WIDGET_TYPE, getProperties()).getData();

        //Load the font size
        label.setFont(new Font("Arial", Font.BOLD, (Integer)Property.getPropertyFromType(Property.Type.FONT_SIZE, getProperties()).getData()-1));
        field.setFont(new Font("Arial", Font.PLAIN, (Integer)Property.getPropertyFromType(Property.Type.FONT_SIZE, getProperties()).getData()));
    }

    private ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Property.Type.SIZE, new Dimension(130,50)));
        tempProperties.add(new Property(Property.Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Property.Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Property.Type.BACKGROUND, Color.WHITE));
        tempProperties.add(new Property(Property.Type.NAME, "Text Name"));
        tempProperties.add(new Property(Property.Type.WIDGET_TYPE, 1));
        tempProperties.add(new Property(Property.Type.VALUE, "Example Text"));
        tempProperties.add(new Property(Property.Type.FONT_SIZE, 14));

        return tempProperties;
    }

    @Override
    public boolean isMovable() {
        return isMovable;
    }

    private boolean isMovable = false;
    @Override
    public void setMovable(boolean movable)
    {
        isMovable = movable;
        field.setEnabled(!movable);
    }

    @Override
    public String getWidgetName() {
        return "text-field";
    }

    @Override
    public void applyWidgetType()
    {

    }
}
