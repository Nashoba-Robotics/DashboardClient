package edu.nr;

import edu.nr.properties.Property;

import javax.swing.*;
import java.util.ArrayList;

public abstract class MovableComponent extends JPanel
{
    protected  ArrayList<Property> properties = null;
    protected boolean isMovable;

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
    public abstract void applyWidgetType();
    public abstract void setValue(Object o);
}
