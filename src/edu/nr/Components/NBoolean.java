package edu.nr.Components;

import edu.nr.Components.mouse_listeners.MyMouseListener;
import edu.nr.Main;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 1/18/14
 *         Time: 11:49 PM
 */
public class NBoolean extends MovableComponent
{
    private JLabel label;
    private JTextField field;
    ArrayList<MovableComponent> components;
    private Boolean currentValue = null;

    public NBoolean(ArrayList<MovableComponent> components, ArrayList<Property> properties, Main main)
    {
        this.main = main;
        this.components = components;
        this.properties = getDefaultProperties();

        if(properties != null)
        {
            PropertiesManager.loadPropertiesIntoArray(this.properties, properties);
        }
        label = new JLabel();
        label.setBorder(new EmptyBorder(0,0,0,0));

        field = new JTextField();
        applyProperties();

        setLayout(new BoxLayout(this, 0));
        setBorder(new EmptyBorder(1,1,1,1));

        add(label, BorderLayout.WEST);
        add(field, BorderLayout.EAST);

        MyMouseListener listener = new MyMouseListener(this, components, main);
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
                if(field.getText().compareToIgnoreCase("true") == 0)
                    NBoolean.this.main.network.putBoolean(getTitle(), true);
                else if(field.getText().compareToIgnoreCase("false") == 0)
                    NBoolean.this.main.network.putBoolean(getTitle(), false);
                else
                {
                    if(valueSet)
                    {
                        field.setText(currentValue + "");
                        NBoolean.this.main.network.putBoolean(getTitle(), currentValue);
                    }
                    else
                        field.setText("");
                }
            }
        }));
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
        field.setEnabled(!movable);
        isMovable = movable;
    }

    @Override
    public void applyProperties()
    {
        label.setText(" " + (String) Property.getPropertyFromType(Property.Type.NAME, properties).getData() + " ");
        field.setForeground((Color) Property.getPropertyFromType(Property.Type.FOREGROUND, properties).getData());
        setBackground((Color) Property.getPropertyFromType(Property.Type.BACKGROUND, properties).getData());
        setSize((Dimension) Property.getPropertyFromType(Property.Type.SIZE, properties).getData());
        setLocation((Point)Property.getPropertyFromType(Property.Type.LOCATION, properties).getData());

        //Load the font size
        label.setFont(new Font("Arial", Font.BOLD, (Integer) Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData() - 1));
        field.setFont(new Font("Arial", Font.PLAIN, (Integer) Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData()));
    }

    @Override
    public String getWidgetName()
    {
        return WidgetNames.BOOLEAN_NAME;
    }

    @Override
    public void setValue(Object o)
    {
        super.setValue(o);
        field.setText(((Boolean)o).booleanValue() + "");
        currentValue = (Boolean)o;
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
        Boolean b = main.network.getBoolean(getTitle());
        if(b != null)
            setValue(b);
    }

    public static int getStaticWidgetType()
    {
        return 1;
    }


}
