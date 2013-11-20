package edu.nr;

import edu.nr.properties.Property;

import java.util.ArrayList;

public interface MovableComponent
{
    public boolean isMovable();
    public void setMovable(boolean movable);
    public ArrayList<Property> getProperties();
    public String getWidgetName();
    public void applyWidgetType();
}
