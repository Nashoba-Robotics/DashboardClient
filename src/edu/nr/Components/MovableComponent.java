package edu.nr.Components;

import edu.nr.Main;
import edu.nr.properties.Property;

import javax.swing.*;
import java.util.ArrayList;

public abstract class MovableComponent extends JPanel
{
    protected  ArrayList<Property> properties = null;
    protected boolean isMovable;
    protected boolean valueSet = false;
    protected Main main;

    public final boolean isMovable()
    {
        return isMovable;
    }
    public abstract void setMovable(boolean movable);
    public final ArrayList<Property> getProperties()
    {
        return properties;
    }
    protected final void setProperties(ArrayList<Property> properties)
    {
        this.properties = properties;
    }
    public abstract void applyProperties();
    public abstract String getWidgetName();
    public void setValue(Object o)
    {
        valueSet = true;
    }
    public abstract String getTitle();
    public abstract int getWidgetType();
    public abstract void attemptValueFetch();
}
