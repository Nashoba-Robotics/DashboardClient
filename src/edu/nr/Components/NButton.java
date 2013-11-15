package edu.nr.Components;

import edu.nr.Main;
import edu.nr.MovableComponent;

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
                    if(p.getTitle().equals(properties.get(i).getTitle()))
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
            String name = p.getTitle();
            if(name.equals("size"))
            {
                setSize((Dimension)p.getData());
            }
            else if(name.equals("location"))
            {
                setLocation((Point)p.getData());
            }
            else if(name.equals("foreground"))
            {
                setForeground((Color)p.getData());
            }
            else if(name.equals("background"))
            {
                setBackground((Color)p.getData());
            }
            else if(name.equals("id"))
            {
                this.id = (Integer)p.getData();
            }
            else if(name.equals("name"))
            {
                setText((String)p.getData());
            }
        }
    }

    private ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property("size", new Dimension(100,30)));
        tempProperties.add(new Property("location", new Point(0,0)));
        tempProperties.add(new Property("foreground", Color.BLACK));
        tempProperties.add(new Property("background", Color.WHITE));
        tempProperties.add(new Property("name", "Button"));
        tempProperties.add(new Property("id", -1));

        return tempProperties;
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
}
