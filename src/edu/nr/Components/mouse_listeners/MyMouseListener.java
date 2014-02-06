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
        if(caller.isMovable())
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

		JMenu changeMenu = new JMenu("Change to");
		String[] widgetChoices = caller.getWidgetChoices();
		for(int i = 0; i < widgetChoices.length; i++)
		{
			JMenuItem item = new JMenuItem();
			item.setText(widgetChoices[i]);
			if((i+1) == caller.getWidgetType())
				item.setEnabled(false);
			item.addActionListener(new MyMenuActionListener(i+1));
			changeMenu.add(item);
		}
		menu.add(changeMenu);

        JMenuItem removeItem = new JMenuItem("Remove Item");
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                main.removeWidget(caller);
            }
        });

        menu.add(removeItem);

        Point delta = getDeltaPoint(e);
        menu.show(e.getComponent(), delta.x, delta.y);
    }

	private class MyMenuActionListener implements ActionListener
	{
		private int index;
		public MyMenuActionListener(int index)
		{
			this.index = index;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{

		}
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
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        //New
        mouseIsIn = false;
        if((caller).isMovable() && !isPressed)
        {
            caller.setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void mouseDragged(MouseEvent me)
    {
        if((caller).isMovable() && !(me.isPopupTrigger()) && !(me.isMetaDown()))
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
                        /*OLD Rectangle bounds = caller.getBounds();
                        bounds.translate(dx, dy);
                        caller.setBounds(bounds);*/
						Point callerPoint = caller.getLocation();
						Point newLocation = new Point();
						newLocation.x = callerPoint.x + dx;
						newLocation.y = callerPoint.y + dy;
						OverlapChecker.checkForCollision(caller, main.getComponentsList(), newLocation, callerPoint);
                        resize();
						Property.getPropertyFromType(Property.Type.LOCATION, (caller).getProperties()).setData(caller.getLocation());
                }
                caller.setCursor(Cursor.getPredefinedCursor(cursor));
				Property.getPropertyFromType(Property.Type.SIZE, (caller).getProperties()).setData(caller.getSize());
            }
        }
    }

    private void resize()
    {
        main.revalidate();
		main.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if ((caller).isMovable() && !(e.isPopupTrigger()) && !(e.isMetaDown()))
        {
			Point delta = getDeltaPoint(e);
            caller.setCursor(Cursor.getPredefinedCursor(caller.getCursor(delta.x, delta.y)));
        }
    }
}
