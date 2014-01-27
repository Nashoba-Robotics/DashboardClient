package edu.nr.Components.mouse_listeners;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;

// ResizableBorder.java

public class ResizableBorder implements Border {
    private int dist = 8;

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

    public ResizableBorder(int dist) {
        this.dist = dist;
    }

    public Insets getBorderInsets(Component component) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    public void paintBorder(Component component, Graphics g, int x, int y,
                            int w, int h) {
        g.setColor(Color.black);
        g.drawRect(x + dist / 2, y + dist / 2, w - dist, h - dist);

        if (component.hasFocus())
        {
            for (int i = 0; i < locations.length; i++) {
                Rectangle rect = getRectangle(x, y, w, h, locations[i], dist);
                g.setColor(Color.WHITE);
                g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
                g.setColor(Color.BLUE);
                //g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        }
    }

    private Rectangle getRectangle(int x, int y, int w, int h, int location, int distance)
    {
        switch (location)
        {
            case SwingConstants.NORTH:
                return new Rectangle(x + w / 2 - dist / 2, y, distance, distance);
            case SwingConstants.SOUTH:
                return new Rectangle(x + w / 2 - dist / 2, y + h - dist, distance,
                        distance);
            case SwingConstants.WEST:
                return new Rectangle(x, y + h / 2 - dist / 2, distance, distance);
            case SwingConstants.EAST:
                return new Rectangle(x + w - dist, y + h / 2 - dist / 2, distance, distance);
            case SwingConstants.NORTH_WEST:
                return new Rectangle(x, y, distance, distance);
            case SwingConstants.NORTH_EAST:
                return new Rectangle(x + w - dist, y, distance, distance);
            case SwingConstants.SOUTH_WEST:
                return new Rectangle(x, y + h - dist, distance, distance);
            case SwingConstants.SOUTH_EAST:
                return new Rectangle(x + w - dist, y + h - dist, distance, distance);
        }
        return null;
    }

    public int getCursor(MouseEvent me)
    {
        Component c = me.getComponent();
        int w = c.getWidth();
        int h = c.getHeight();

        for (int i = 0; i < locations.length; i++)
        {
            Rectangle rect = getRectangle(0, 0, w, h, locations[i], 10);
            if (rect.contains(me.getPoint()))
                return cursors[i];
        }

        return Cursor.MOVE_CURSOR;
    }
}