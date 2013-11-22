package edu.nr.Components;

import edu.nr.Components.mouse_listeners.MyMouseListener;
import edu.nr.Main;
import edu.nr.MovableComponent;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.properties.Property.Type;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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

    private NButton(){}

    private ArrayList<MovableComponent> components;
    public NButton(ArrayList<MovableComponent> components, ArrayList<Property> properties)
    {
        this.components = components;
        initPropertiesArray();
        button = new JButton();

        setBackground(null);
        loadProperties(properties);
        applyProperties();

        setBorder(new EmptyBorder(1,1,1,1));
        setLayout(new BorderLayout());
        add(button, BorderLayout.CENTER);
        button.setFocusable(false);
        setBackground(Color.WHITE);

        setOpaque(false);

        setFocusable(false);

        MyMouseListener listener = new MyMouseListener(NButton.this, components);
        button.addMouseListener(listener);
        button.addMouseMotionListener(listener);

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
        PropertiesManager.loadPropertiesIntoArray(getProperties(), loadedProperties);
    }

    public void applyProperties()
    {
        applyProperties(getProperties());
    }

    private void applyProperties(ArrayList<Property> applyingProperties)
    {
        //TODO Change this way of getting values to match that of NTextField
        for(Property p : applyingProperties)
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

    private ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Type.SIZE, new Dimension(100,32)));
        tempProperties.add(new Property(Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Type.BACKGROUND, new Color(220,220,220)));
        tempProperties.add(new Property(Type.NAME, "Button"));//TODO Put actual name
        tempProperties.add(new Property(Type.WIDGET_TYPE, 1));
        tempProperties.add(new Property(Type.VALUE, null));
        tempProperties.add(new Property(Type.FONT_SIZE, 12));
        return tempProperties;
    }

    private boolean isMovable = false;
    @Override
    public void setMovable(boolean movable)
    {
        isMovable = movable;
        button.setEnabled(!movable);
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

    @Override
    public boolean isMovable()
    {
        return isMovable;
    }
}
