package edu.nr.Components;

import edu.nr.Components.mouse_listeners.MyMouseListener;
import edu.nr.Main;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
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

    private Rectangle getRectangle(int x, int y, int w, int h, int location, int distance)
    {
        switch (location)
        {
            case SwingConstants.NORTH:
                return new Rectangle(distance, y, w - distance*2, distance);
            case SwingConstants.SOUTH:
                return new Rectangle(distance, y + h - distance, w-distance*2, distance);
            case SwingConstants.WEST:
                return new Rectangle(x, distance, distance, h - distance*2);
            case SwingConstants.EAST:
                return new Rectangle(x + w - distance, distance, distance, h - distance*2);
            case SwingConstants.NORTH_WEST:
                return new Rectangle(x, y, distance, distance);
            case SwingConstants.NORTH_EAST:
                return new Rectangle(x + w - distance, y, distance, distance);
            case SwingConstants.SOUTH_WEST:
                return new Rectangle(x, y + h - distance, distance, distance);
            case SwingConstants.SOUTH_EAST:
                return new Rectangle(x + w - distance, y + h - distance, distance, distance);
        }
        return null;
    }

    int locations[] =
            {
                    SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.WEST,
                    SwingConstants.EAST, SwingConstants.NORTH_WEST,
                    SwingConstants.NORTH_EAST, SwingConstants.SOUTH_WEST,
                    SwingConstants.SOUTH_EAST
            };

    int cursors[] =
            {
                    Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR,
                    Cursor.E_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
                    Cursor.SW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
            };

    public int getCursor(int x, int y)
    {
        int w = getWidth();
        int h = getHeight();

        for (int i = 0; i < locations.length; i++)
        {
            Rectangle rect = getRectangle(0, 0, w, h, locations[i], 10);
            if (rect.contains(new Point(x,y)))
                return cursors[i];
        }

        return Cursor.MOVE_CURSOR;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        g.setColor(Color.black);
        if(isMovable())
            g.drawRect(3, 3, getWidth()-6, getHeight()-6);
    }
}
