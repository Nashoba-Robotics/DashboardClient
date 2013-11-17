package edu.nr.Components;

import edu.nr.Main;
import edu.nr.MovableComponent;
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
        setBackground(Color.WHITE);
        setOpaque(true);
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
                //Talk to network here
            }
        });
    }

    private void loadProperties(ArrayList<Property> loadedProperties)
    {
        for(Property p : getDefaultProperties())
        {
            properties.add(p);
        }
        if(loadedProperties != null)
        {
            for(Property p : loadedProperties)
            {
                for(int i = 0; i < properties.size(); i++)
                {
                    if(p.getType().equals(properties.get(i).getType()))
                    {
                        properties.set(i, p);
                    }
                }
            }
        }
    }

    private void applyProperties()
    {
        for(Property p : properties)
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
            else if(type == Type.ID)
            {
                this.id = (Integer)p.getData();
            }
            else if(type == Type.NAME)
            {
                setText((String)p.getData());
            }
        }
    }

    private ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Type.SIZE, new Dimension(100,30)));
        tempProperties.add(new Property(Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Type.BACKGROUND, Color.WHITE));
        tempProperties.add(new Property(Type.NAME, "Button"));
        tempProperties.add(new Property(Type.ID, -1));
        tempProperties.add(new Property(Type.WIDGET_TYPE, 1));

        return tempProperties;
    }

    public ArrayList<Property> getProperties()
    {
        return properties;
    }

    @Override
    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public int getId()
    {
        return id;
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
}
