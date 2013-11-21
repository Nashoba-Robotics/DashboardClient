package edu.nr.Components.mouse_listeners;

import edu.nr.Components.NButton;
import edu.nr.Main;
import edu.nr.MovableComponent;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.util.OverlapChecker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author co1in
 * Date: 11/13/13
 * Time: 10:25 PM
 */
public class MyMouseListener implements MouseListener, MouseMotionListener
{
    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;

    private JComponent caller;
    private Color oldButtonColor;
    private boolean callerIsButton = false;
    private ArrayList<MovableComponent> components;
    public MyMouseListener(JComponent caller, ArrayList<MovableComponent> components)
    {
        this.caller = caller;
        callerIsButton = (caller.getClass() == NButton.class);
        System.out.println("Caller is button");
        this.components = components;
    }
    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(((MovableComponent)caller).isMovable())
        {
            if(e.isMetaDown() || e.isPopupTrigger())
            {
                doPop(e);
            }
            else if(((MovableComponent)caller).isMovable())
            {
                screenX = e.getXOnScreen();
                screenY = e.getYOnScreen();

                myX = caller.getX();
                myY = caller.getY();
            }
        }
        else
        {
            if(callerIsButton)
            {
                oldButtonColor = caller.getBackground();
                caller.setBackground(Color.blue);
            }
        }
    }

    private void doPop(MouseEvent e)
    {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem propertiesItem = new JMenuItem("Properties");
        propertiesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Properties not Implemented Yet");
            }
        });
        menu.add(propertiesItem);

        JMenuItem removeItem = new JMenuItem("Remove Item");
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Removing Items not Implemented Yet");
            }
        });

        menu.add(removeItem);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        Main.main.getContentPane().invalidate();
        Main.main.repaint();

        if(callerIsButton && ((MovableComponent)caller).isMovable())
        {
            caller.setBackground(oldButtonColor);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        if(((MovableComponent)caller).isMovable())
        {
            caller.setBorder(new LineBorder(Color.WHITE, 1, false));
        }
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        if(((MovableComponent)caller).isMovable())
        {
            caller.setBorder(new EmptyBorder(0,0,0,0));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        if(((MovableComponent)caller).isMovable())
        {
            int deltaX = e.getXOnScreen() - screenX;
            int deltaY = e.getYOnScreen() - screenY;

            Point oldLocation = caller.getLocation();

            Point myNewLocation = new Point(myX + deltaX, myY + deltaY);

            //Check to see if we were dragged
            OverlapChecker.checkForCollision(caller, components, myNewLocation, oldLocation);
            Main.main.repaint();
            Main.main.invalidate();
            Property.getPropertyFromType(Property.Type.LOCATION, ((MovableComponent)caller).getProperties()).setData(caller.getLocation());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {

    }
}