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

    public enum Type{NAME, SIZE, LOCATION, FOREGROUND, BACKGROUND, ID, WIDGET_TYPE}

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
