package edu.nr.Components;

import edu.nr.Components.mouse_listeners.MyMouseListener;
import edu.nr.Main;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.util.Printer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * User: co1in
 * Date: 11/13/13
 * Time: 10:22 PM
 */
public class NTextField extends MovableComponent
{
    private JTextField field;
    private JLabel label;

    public NTextField(ArrayList<MovableComponent> components, ArrayList<Property> loadedProperties, Main main)
    {
        this.main = main;
        label = new JLabel();
        label.setBorder(new EmptyBorder(0,0,0,0));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        field = new JTextField();
        field.setDisabledTextColor(Color.BLACK);

        properties = getDefaultProperties();
        if(loadedProperties != null)
        {
            loadProperties(loadedProperties);
        }
        applyProperties();

        setLayout(new BoxLayout(this, 0));
        setBorder(new EmptyBorder(1, 1, 1, 1));

        add(label, BorderLayout.WEST);
        add(field, BorderLayout.EAST);

        MyMouseListener listener = new MyMouseListener(NTextField.this, components, main);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        field.addMouseListener(listener);
        field.addMouseMotionListener(listener);

        field.setFocusTraversalKeysEnabled(false);
        field.addKeyListener(new TabListener(new Runnable()
        {
            @Override
            public void run()
            {
                NTextField.this.main.network.putString(getTitle(), field.getText());
            }
        }));
    }

    private void loadProperties(ArrayList<Property> loaded)
    {
        PropertiesManager.loadPropertiesIntoArray(properties, loaded);
    }

    public void applyProperties()
    {
        //Load the properties by using the Property classes function for getting a specific property out of our array by finding it's type,
        //Then set our values to the correctly casted version of the data from that property
        label.setText((String)Property.getPropertyFromType(Property.Type.NAME, properties).getData() + " ");
        field.setForeground((Color)Property.getPropertyFromType(Property.Type.FOREGROUND, properties).getData());
        setBackground((Color)Property.getPropertyFromType(Property.Type.BACKGROUND, properties).getData());
        setSize((Dimension) Property.getPropertyFromType(Property.Type.SIZE, properties).getData());
        setLocation((Point)Property.getPropertyFromType(Property.Type.LOCATION, properties).getData());

        //Load the font size
        label.setFont(new Font("Arial", Font.BOLD, (Integer)Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData()-1));
        field.setFont(new Font("Arial", Font.PLAIN, (Integer)Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData()));
    }

    private ArrayList<Property> getDefaultProperties()
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
    public void setMovable(boolean movable)
    {
        isMovable = movable;
        field.setEnabled(!movable);
    }

    @Override
    public String getWidgetName() {
        return WidgetNames.STRING_NAME;
    }

    public static String getStaticWidgetName()
    {
        return WidgetNames.STRING_NAME;
    }

    @Override
    public void setValue(Object o)
    {
        super.setValue(o);
        field.setText((String)o);
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
}
