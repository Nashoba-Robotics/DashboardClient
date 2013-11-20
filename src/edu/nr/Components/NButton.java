package edu.nr.Components;

import edu.nr.Main;
import edu.nr.MovableComponent;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.properties.Property.Type;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author co1in
 */
public class NButton extends JButton implements MovableComponent
{

    private int id = -1;

    private ArrayList<Property> properties = new ArrayList<Property>();
    private Main main;

    private NButton(){}

    private ArrayList<MovableComponent> components;
    public NButton(ArrayList<MovableComponent> components, ArrayList<Property> properties)
    {
        this.components = components;
        this.id = id;

        loadProperties(properties);
        applyProperties();

        setBorder(new LineBorder(Color.BLACK, 1));
        //setBackground(Color.WHITE);
        //setOpaque(true);
        setSize(100, 30);
        setFocusable(false);
        setForeground(Color.BLACK);

        MyMouseListener listener = new MyMouseListener(NButton.this, components);
        addMouseListener(listener);
        addMouseMotionListener(listener);

        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO Talk to network here
            }
        });
    }

    private void loadProperties(ArrayList<Property> loadedProperties)
    {
        properties = getDefaultProperties();
        PropertiesManager.loadPropertiesIntoArray(properties, loadedProperties);
    }

    public void applyProperties()
    {
        applyProperties(properties);
    }

    public void applyProperties(ArrayList<Property> applyingProperties)
    {
        //TODO Change this way of getting values to match that of NTextField
        for(Property p : applyingProperties)
        {
            Type type = p.getType();
            if(type == Type.SIZE)
            {
                setSize((Dimension)p.getData());
            }
            else if(type == Type.LOCATION)
            {
                setLocation((Point)p.getData());
            }
            else if(type == Type.FOREGROUND)
            {
                setForeground((Color)p.getData());
            }
            else if(type == Type.BACKGROUND)
            {
                setBackground((Color)p.getData());
            }
            else if(type == Type.NAME)
            {
                setText((String)p.getData());
            }
            else if(type == Type.FONT_SIZE)
            {
                setFont(new Font("Arial",Font.PLAIN, ((Integer)p.getData())));
            }
        }
    }

    private ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Type.SIZE, new Dimension(100,30)));
        tempProperties.add(new Property(Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Type.BACKGROUND, new Color(220,220,220)));
        tempProperties.add(new Property(Type.NAME, "Button"));
        tempProperties.add(new Property(Type.WIDGET_TYPE, 1));
        tempProperties.add(new Property(Type.VALUE, null));
        tempProperties.add(new Property(Type.FONT_SIZE, 12));
        return tempProperties;
    }

    public ArrayList<Property> getProperties()
    {
        return properties;
    }

    @Override
    public void setMovable(boolean movable)
    {
        setEnabled(!movable);
    }

    public String getWidgetName()
    {
        return "button";
    }

    @Override
    public void applyWidgetType()
    {
        //Nothing to do here because button only has one type
    }
}
