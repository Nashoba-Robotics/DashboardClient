package edu.nr.Components.extras;

import edu.nr.util.Printer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author co1in
 *         Date: 1/19/14
 *         Time: 6:27 PM
 */
public class TabListener implements KeyListener
{
    private Runnable r;
    public TabListener(Runnable r)
    {
        this.r = r;
    }
    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(r != null)
        {
            if(e.getKeyCode() == KeyEvent.VK_TAB)
                r.run();
        }
        else
        {
            Printer.print("Error: runnable given to tablistener was null!");
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
