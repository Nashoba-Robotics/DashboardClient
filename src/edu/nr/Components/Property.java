package edu.nr.Components;

/**
 * @author co1in
 *         Date: 11/14/13
 *         Time: 10:00 PM
 */
public class Property<T>
{
    private T data;
    private String title;
    public Property(String propertyTitle, T propertyData)
    {
        title = propertyTitle;
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

    public String getTitle()
    {
        return title;
    }

    public Property clone()
    {
        return new Property<T>(title, data);
    }
}
