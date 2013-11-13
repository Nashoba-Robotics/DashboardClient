package edu.nr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class Button
        extends JButton {

    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;

    public Button()
    {
        //setBorder(new LineBorder(Color.BLUE, 3));
        setBackground(Color.WHITE);
        setBounds(0, 0, 100, 100);
        setOpaque(true);
        setFocusable(false);
        setText("Button");
        setForeground(Color.BLACK);

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if(!isEnabled())
                {
                    screenX = e.getXOnScreen();
                    screenY = e.getYOnScreen();

                    myX = getX();
                    myY = getY();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }

        });
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e)
            {
                if(!isEnabled())
                {
                    int deltaX = e.getXOnScreen() - screenX;
                    int deltaY = e.getYOnScreen() - screenY;

                    setLocation(myX + deltaX, myY + deltaY);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) { }

        });
    }

}
