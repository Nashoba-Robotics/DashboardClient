package edu.nr.Components;

import edu.nr.Components.mouse_listeners.MyMouseListener;
import edu.nr.Main;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public abstract class MovableComponent extends JPanel
{
    public MovableComponent(ArrayList<MovableComponent> components, ArrayList<Property> properties, Main main)
    {
        this.main = main;

        this.properties = getDefaultProperties();
        PropertiesManager.loadPropertiesIntoArray(this.properties, properties);

        mouseListener = new MyMouseListener(this, main);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        setLayout(new BoxLayout(this, 0));
        setBorder(new EmptyBorder(1, 1, 1, 1));
    }

    protected  ArrayList<Property> properties = null;
    protected boolean isMovable;
    protected boolean valueSet = false;
    protected Main main;
    protected MyMouseListener mouseListener;

    public final boolean isMovable()
    {
        return isMovable;
    }
    public abstract void setMovable(boolean movable);
    protected abstract ArrayList<Property> getDefaultProperties();
    public final ArrayList<Property> getProperties()
    {
        return properties;
    }
    protected final void setProperties(ArrayList<Property> properties)
    {
        this.properties = properties;
    }
    public abstract void applyProperties(boolean setSize);
    public abstract String getWidgetName();
    public void setValue(Object o)
    {
        valueSet = true;
    }
    public boolean valueHasBeenSet()
    {
        return valueSet;
    }

    public abstract String getTitle();
    public abstract int getWidgetType();
    public abstract void attemptValueFetch();
}
