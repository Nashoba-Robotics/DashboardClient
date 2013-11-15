package edu.nr.Components;

import edu.nr.MovableComponent;
import edu.nr.util.OverlapChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
    private ArrayList<MovableComponent> components;
    public MyMouseListener(JComponent caller, ArrayList<MovableComponent> components)
    {
        this.caller = caller;
        this.components = components;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(e.isPopupTrigger() && !caller.isEnabled())
        {
            doPop(e);
        }

        if(!caller.isEnabled())
        {
            screenX = e.getXOnScreen();
            screenY = e.getYOnScreen();

            myX = caller.getX();
            myY = caller.getY();
        }
    }

    private void doPop(MouseEvent e)
    {
        JPopupMenu menu = new JPopupMenu();
        menu.add(new JMenuItem("Properties"));
        menu.add(new JMenuItem("Remove Item"));
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        if(!caller.isEnabled())
        {
            int deltaX = e.getXOnScreen() - screenX;
            int deltaY = e.getYOnScreen() - screenY;

            Point oldLocation = caller.getLocation();

            Point myNewLocation = new Point(myX + deltaX, myY + deltaY);

            //Check to see if we were dragged
            OverlapChecker.checkForCollision(caller, components, myNewLocation, oldLocation);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
