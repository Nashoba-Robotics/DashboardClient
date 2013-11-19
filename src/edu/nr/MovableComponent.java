package edu.nr;

import edu.nr.properties.Property;

import java.util.ArrayList;

public interface MovableComponent
{
    public void setId(int id);
    public int getId();
    public void setMovable(boolean movable);
    public ArrayList<Property> getProperties();
    public String getWidgetName();
    public void applyWidgetType();
}
