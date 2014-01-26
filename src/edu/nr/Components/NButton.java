package edu.nr.Components;

import edu.nr.Components.mouse_listeners.MyMouseListener;
import edu.nr.Main;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.properties.Property.Type;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author co1in
 */
public class NButton extends MovableComponent
{

    private JButton button;

    public NButton(ArrayList<MovableComponent> components, ArrayList<Property> properties, Main main)
    {
        super(components, properties, main);

        this.main = main;
        button = new JButton();

        setBackground(null);

        if(properties == null)
        {
            this.properties = new ArrayList<Property>();
            loadProperties(properties);
        }
        else
        {
            this.properties = properties;
            applyProperties(false);
        }
        applyProperties(false);

        add(button, BorderLayout.CENTER);
        button.setFocusable(false);
        setBackground(Color.WHITE);

        button.addMouseListener(mouseListener);
        button.addMouseMotionListener(mouseListener);

        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO Talk to network here
            }
        });
    }

    protected void loadProperties(ArrayList<Property> loadedProperties)
    {
        this.setProperties(getDefaultProperties());
        PropertiesManager.loadPropertiesIntoArray(this.properties, loadedProperties);
    }

    public void applyProperties(boolean setSize)
    {
        applyProperties(this.properties);
    }

    public void applyProperties(ArrayList<Property> applyingProperties)
    {
        PropertiesManager.loadPropertiesIntoArray(properties, applyingProperties);
        //TODO Change this way of getting values to match that of NTextField
        for(Property p : properties)
        {
            Type type = p.getType();
            if(type == Type.SIZE)
            {
                Dimension d = (Dimension) p.getData();
                button.setSize(d);
                setSize(new Dimension((int)d.getWidth()-1, (int)d.getHeight()-1));
                validate();
            }
            else if(type == Type.LOCATION)
            {
                setLocation((Point)p.getData());
            }
            else if(type == Type.FOREGROUND)
            {
                button.setForeground((Color)p.getData());
            }
            else if(type == Type.BACKGROUND)
            {
                button.setBackground((Color)p.getData());
            }
            else if(type == Type.NAME)
            {
                button.setText((String)p.getData());
            }
            else if(type == Type.FONT_SIZE)
            {
                button.setFont(new Font("Arial",Font.PLAIN, ((Integer)p.getData())));
            }
        }
    }

    protected ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Type.SIZE, new Dimension(100,32)));
        tempProperties.add(new Property(Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Type.BACKGROUND, new Color(220,220,220)));
        tempProperties.add(new Property(Type.NAME, "Button"));//TODO Put actual name
        tempProperties.add(new Property(Type.WIDGET_TYPE, 1));
        tempProperties.add(new Property(Type.FONT_SIZE, 12));
        return tempProperties;
    }

    @Override
    public void setMovable(boolean movable)
    {
        isMovable = movable;
        button.setEnabled(!movable);
    }

    public String getWidgetName()
    {
        return WidgetNames.NUMBER_NAME;
    }

    @Override
    public void setValue(Object o)
    {
        super.setValue(o);
    }

    @Override
    public String getTitle()
    {
        return button.getText();
    }

    @Override
    public int getWidgetType() {
        return 1;
    }

    @Override
    public void attemptValueFetch()
    {
        //Nothing to do here
    }
}
