package edu.nr.Components.mouse_listeners;

import edu.nr.Components.NButton;
import edu.nr.Main;
import edu.nr.Components.MovableComponent;
import edu.nr.properties.Property;
import edu.nr.util.OverlapChecker;
import edu.nr.util.Printer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author co1in
 * Date: 11/13/13
 * Time: 10:25 PM
 */
public class MyMouseListener extends MouseInputAdapter
{
    private int cursor;
    private Point startPos = null;
    private final int MIN_WIDTH = 30;

    private MovableComponent caller;
    private Main main;
    public MyMouseListener(MovableComponent caller, Main main)
    {
        this.main = main;
        this.caller = caller;
    }

    private boolean isPressed = false;
    private boolean mouseIsIn = false;

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(((MovableComponent)caller).isMovable())
        {
            if(e.isMetaDown() || e.isPopupTrigger())
            {
                doPop(e);
            }
            else
            {
                Point delta = getDeltaPoint(e);

                cursor = caller.getCursor(delta.x, delta.y);
                startPos = (Point)delta.clone();
                caller.requestFocus();
                caller.repaint();
            }
        }
        main.somethingIsBeingPressed = true;
        isPressed = true;
    }

    private Point getDeltaPoint(MouseEvent e)
    {
        Point screen = e.getLocationOnScreen();
        Point callerLocation = caller.getLocationOnScreen();
        return new Point(screen.x - callerLocation.x, screen.y - callerLocation.y);
    }

    private void doPop(MouseEvent e)
    {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem propertiesItem = new JMenuItem("Properties");
        propertiesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Printer.println("Properties not Implemented Yet");
            }
        });
        menu.add(propertiesItem);

        JMenuItem removeItem = new JMenuItem("Remove Item");
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                main.removeWidget((MovableComponent)caller);
            }
        });

        menu.add(removeItem);

        Point delta = getDeltaPoint(e);
        menu.show(e.getComponent(), delta.x, delta.y);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        main.repaint();
        isPressed = false;
        main.somethingIsBeingPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        mouseIsIn = true;
        /*if(((MovableComponent)caller).isMovable() && !Main.somethingIsBeingPressed)
        {
            caller.setBorder(new LineBorder(Color.GREEN, 1, false));
        }*/
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        //New
        mouseIsIn = false;
        if(((MovableComponent)caller).isMovable() && !isPressed)
        {
            caller.setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void mouseDragged(MouseEvent me)
    {
        if((caller).isMovable())
        {
            if (startPos != null)
            {
                int x = caller.getX();
                int y = caller.getY();
                int w = caller.getWidth();
                int h = caller.getHeight();

                Point delta = getDeltaPoint(me);
                int dx = delta.x - startPos.x;
                int dy = delta.y - startPos.y;

                switch (cursor)
                {
                    case Cursor.N_RESIZE_CURSOR:
                        if (!(h - dy < MIN_WIDTH))
                        {
                            caller.setBounds(x, y + dy, w, h - dy);
                            resize();
                        }
                        break;

                    case Cursor.S_RESIZE_CURSOR:
                        if (!(h + dy < MIN_WIDTH))
                        {
                            caller.setBounds(x, y, w, h + dy);
                            startPos = (Point)delta.clone();
                            resize();
                        }
                        break;

                    case Cursor.W_RESIZE_CURSOR:
                        if (!(w - dx < MIN_WIDTH))
                        {
                            caller.setBounds(x + dx, y, w - dx, h);
                            resize();
                        }
                        break;

                    case Cursor.E_RESIZE_CURSOR:
                        if (!(w + dx < MIN_WIDTH))
                        {
                            caller.setBounds(x, y, w + dx, h);
                            startPos = (Point)delta.clone();
                            resize();
                        }
                        break;

                    case Cursor.NW_RESIZE_CURSOR:
                        if (!(w - dx < MIN_WIDTH) && !(h - dy < MIN_WIDTH))
                        {
                            caller.setBounds(x + dx, y + dy, w - dx, h - dy);
                            resize();
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < MIN_WIDTH) && !(h - dy < MIN_WIDTH))
                        {
                            caller.setBounds(x, y + dy, w + dx, h - dy);
                            startPos = new Point(delta.x, startPos.y);
                            resize();
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < MIN_WIDTH) && !(h + dy < MIN_WIDTH))
                        {
                            caller.setBounds(x + dx, y, w - dx, h + dy);
                            startPos = new Point(startPos.x, delta.y);
                            resize();
                        }
                        break;

                    case Cursor.SE_RESIZE_CURSOR:
                        if (!(w + dx < MIN_WIDTH) && !(h + dy < MIN_WIDTH)) {
                            caller.setBounds(x, y, w + dx, h + dy);
                            startPos = (Point)delta.clone();
                            resize();
                        }
                        break;

                    case Cursor.MOVE_CURSOR:
                        Rectangle bounds = caller.getBounds();
                        bounds.translate(dx, dy);
                        caller.setBounds(bounds);
                        resize();
                }
                caller.setCursor(Cursor.getPredefinedCursor(cursor));
            }
        }
    }

    private void resize()
    {
        main.revalidate();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        Point delta = getDeltaPoint(e);
        //New
        if (caller.hasFocus() && (caller).isMovable())
        {
            caller.setCursor(Cursor.getPredefinedCursor(caller.getCursor(delta.x, delta.y)));
            //Printer.println("Location on Comp: " + (delta.x) + "  :  " + (delta.y));
        }
    }
}
