package edu.nr.Components;
import edu.nr.Main;
import edu.nr.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 3/3/14
 *         Time: 3:01 PM
 */
public class FieldView extends MovableComponent
{
	private final float HEIGHT=700;
	private final float WIDTH = (200f / 439f) * HEIGHT;
	private final double SCALE_FACTOR = 54d / (double)HEIGHT;
	private final double ROBOT_SCALE_FACTOR = (2.3d/SCALE_FACTOR) / 50d;
	private NetworkTable table;

	BufferedImage field, robot;
	public FieldView(ArrayList<Property> properties)
	{
		super(properties, true);
		removeMouseListener(mouseListener);
		removeMouseMotionListener(mouseListener);
		/*mouseListener.setOverlapCheckingEnabled(false);
		mouseListener.setResizingEnabled(false);*/

		FieldListener listener = new FieldListener();
		addMouseMotionListener(listener);
		addMouseListener(listener);

		setPreferredSize(new Dimension(Math.round(WIDTH), Math.round(HEIGHT)));
		setSize(new Dimension(Math.round(WIDTH), Math.round(HEIGHT)));

		try
		{
			field = ImageIO.read(new File("field_rough.png"));
			robot = ImageIO.read(new File("robot_rough_2.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		applyProperties(addingFromSave);
	}

	int x = 0, y = 0, angle = 0;

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;

		g.drawImage(field, 0, 0, Math.round(WIDTH), Math.round(HEIGHT), this);

		//Get the values here

		if(x < 0)
			x = 0;
		else if(x > WIDTH)
			x = Math.round(WIDTH);
		if(y < 0)
			y = 0;
		else if(y > HEIGHT)
			y = Math.round(HEIGHT);


		AffineTransform rotation = g2d.getTransform();

		rotation.rotate(Math.toRadians(angle), x + (robot.getWidth() / 2), y + (robot.getHeight() / 2));
		rotation.translate(x, y);
		rotation.scale(ROBOT_SCALE_FACTOR, ROBOT_SCALE_FACTOR);


		g2d.setTransform(rotation);

		g2d.drawImage(robot, 0, 0, this);
	}

	@Override
	public void setMovable(boolean movable)
	{
		this.isMovable = movable;
	}

	@Override
	protected ArrayList<Property> getDefaultProperties()
	{
		ArrayList<Property> tempProperties = new ArrayList<Property>();
		tempProperties.add(new Property(Property.Type.LOCATION, new Point(0,0)));
		//LEFT OFF just finished adding this method, keep on comparing the rest of these methods to make sure they are implemented properly
		//Need to implement the subtable for this object
		return tempProperties;
	}

	@Override
	public void applyProperties(boolean setSize)
	{
		setLocation((Point)Property.getPropertyFromType(Property.Type.LOCATION, properties).getData());
	}

	@Override
	public String getWidgetName()
	{
		return null;
	}

	@Override
	public void setValue(Object value)
	{
		super.setValue(value);
		Point p = (Point)value;
		setLocation(p);
		Property.getPropertyFromType(Property.Type.LOCATION, properties).setData(p.clone());
	}

	@Override
	public Object getValue()
	{
		return null;
	}

	@Override
	public String getTitle()
	{
		return "FieldView";
	}

	@Override
	public int getWidgetType()
	{
		return 1;
	}

	@Override
	public void attemptValueFetch()
	{

	}

	@Override
	public String[] getWidgetChoices()
	{
		return new String[0];
	}

	private class FieldListener implements MouseListener, MouseMotionListener
	{
		int screenX, screenY, myX, myY;
		@Override
		public void mouseClicked(MouseEvent e)
		{

		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			if(isMovable())
			{
				screenX = e.getXOnScreen();
				screenY = e.getYOnScreen();

				myX = getX();
				myY = getY();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{

		}

		@Override
		public void mouseEntered(MouseEvent e)
		{

		}

		@Override
		public void mouseExited(MouseEvent e)
		{

		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			if(isMovable())
			{
				int deltaX = e.getXOnScreen() - screenX;
				int deltaY = e.getYOnScreen() - screenY;

				Point myNewLocation = new Point(myX + deltaX, myY + deltaY);

				//Check to see if we were dragged
				setLocation(myNewLocation);
				Main.mainVar.repaint();
				Main.mainVar.invalidate();
				Property.getPropertyFromType(Property.Type.LOCATION, properties).setData(myNewLocation);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{

		}
	}
}
