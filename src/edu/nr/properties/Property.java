package edu.nr.properties;

import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 11/14/13
 *         Time: 10:00 PM
 */
public class Property<T>
{
    private T data;
    private Type type;

    /**
     * Types of data that can be stored for a widget
     */
    public enum Type{/** The name for the widget (from the server). Data type must be String */NAME,
        /** The size of the widget set by the user. Data type must be java.awt.Dimension */SIZE,
        /** The location of the widget set by the user. Data type must be java.awt.Point*/LOCATION,
        /** The foreground color of the widget set by the user. Data type must be java.awt.Color */FOREGROUND,
        /** The background color of the widget set by the user. Data type must be java.awt.Color */BACKGROUND,
        /** The type of widget for this particular data type set by the user. (Example: changing a number value widget from a text field to a dial or progress bar widget. Data type must be Integer) */WIDGET_TYPE,
        /** The actual data value for this widget set by the server or user. Data type varies depending on what class is using it.*/VALUE,
        /** The size of the font for this widget's text set by the user. Data type must be an Integer */FONT_SIZE}

    public Property(Type propertyType, T propertyData)
    {
        type = propertyType;
        data = propertyData;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public Type getType()
    {
        return type;
    }

    public Property clone()
    {
        return new Property<T>(type, data);
    }

    public static Property getPropertyFromType(Type t, ArrayList<Property> properties)
    {
        for(Property p : properties)
        {
            if(p.getType() == t)
            {
                return p;
            }
        }
        return null;
    }
}
