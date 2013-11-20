package edu.nr.Components;

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
public class NTextField extends JPanel implements MovableComponent
{
    private ArrayList<MovableComponent> components;
    private JTextField field;
    private JLabel label;
    ArrayList<Property> properties;
    private int widgetType = -1;

    public NTextField(ArrayList<MovableComponent> components, ArrayList<Property> loadedProperties)
    {
        this.components = components;

        label = new JLabel();
        label.setBorder(new EmptyBorder(0,0,0,0));
        field = new JTextField();
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
        properties = getDefaultProperties();
        PropertiesManager.loadPropertiesIntoArray(properties, loaded);
    }

    private void applyProperties()
    {
        //Load the properties by using the Property classes function for getting a specific property out of our array by finding it's type,
        //Then set our values to the correctly casted version of the data from that property
        label.setText((String)Property.getPropertyFromType(Property.Type.NAME, properties).getData());
        field.setText((String)Property.getPropertyFromType(Property.Type.VALUE, properties).getData());
        field.setForeground((Color)Property.getPropertyFromType(Property.Type.FOREGROUND, properties).getData());
        setBackground((Color)Property.getPropertyFromType(Property.Type.BACKGROUND, properties).getData());
        setSize((Dimension) Property.getPropertyFromType(Property.Type.SIZE, properties).getData());
        setLocation((Point)Property.getPropertyFromType(Property.Type.LOCATION, properties).getData());
        widgetType = (Integer)Property.getPropertyFromType(Property.Type.WIDGET_TYPE, properties).getData();

        //Load the font size
        label.setFont(new Font("Arial", Font.PLAIN, (Integer)Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData()-4));
        field.setFont(new Font("Arial", Font.PLAIN, (Integer)Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData()));
    }

    private ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Property.Type.SIZE, new Dimension(100,40)));
        tempProperties.add(new Property(Property.Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Property.Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Property.Type.BACKGROUND, Color.WHITE));
        tempProperties.add(new Property(Property.Type.NAME, "Text Name"));
        tempProperties.add(new Property(Property.Type.WIDGET_TYPE, 1));
        tempProperties.add(new Property(Property.Type.VALUE, "Example Text"));
        tempProperties.add(new Property(Property.Type.FONT_SIZE, 15));

        return tempProperties;
    }

    @Override
    public void setMovable(boolean movable)
    {
        setEnabled(!movable);
        field.setEnabled(!movable);
    }

    @Override
    public ArrayList<Property> getProperties() {
        return properties;
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
