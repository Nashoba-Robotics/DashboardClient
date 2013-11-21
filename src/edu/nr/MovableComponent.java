package edu.nr;

import edu.nr.properties.Property;

import javax.swing.*;
import java.util.ArrayList;

public abstract class MovableComponent extends JPanel
{
    ArrayList<Property> properties;
    protected void initPropertiesArray()
    {
        properties = new ArrayList<Property>();
    }

    public abstract boolean isMovable();
    public abstract void setMovable(boolean movable);
    public final ArrayList<Property> getProperties()
    {
        return properties;
    }
    protected final void setProperties(ArrayList<Property> properties)
    {
        this.properties = properties;
    }
    public abstract String getWidgetName();
    public abstract void applyWidgetType();
}
