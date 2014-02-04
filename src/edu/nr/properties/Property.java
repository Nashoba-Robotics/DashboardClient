package edu.nr.properties;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 11/14/13
 *         Time: 10:00 PM
 */
public class Property
{
    private Object data;
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
        /** The size of the font for this widget's text set by the user. Data type must be an Integer */FONT_SIZE,
		/**The Y axis value name for graph widgets*/ GRAPH_AXIS_NAME,
		/**The time interval that the graph should refresh at*/ GRAPH_REFRESH_RATE,
		/**Whether the graph should refresh itself or only refresh when new data is sent*/ AUTOREFRESH}

    public static Class[] typeClasses = {String.class, Dimension.class, Point.class, Color.class, Color.class, Integer.class, Object.class, Integer.class};

    public Property(Type propertyType, Object propertyData)
    {
        type = propertyType;
        data = propertyData;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public Type getType()
    {
        return type;
    }

    public Property clone()
    {
        return new Property(type, data);
    }

    public static Property getPropertyFromType(Type Object, ArrayList<Property> properties)
    {
        for(Property p : properties)
        {
            if(p.getType() == Object)
            {
                return p;
            }
        }
        return null;
    }
}
