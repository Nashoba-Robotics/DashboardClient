package edu.nr.Components;
import edu.nr.Components.extras.WidgetInfo;
import edu.nr.Main;
import edu.nr.properties.Property;
import edu.nr.util.Printer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

import javax.imageio.ImageIO;
import javax.swing.*;
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
public class FieldView extends MovableComponent implements ITableListener
{
	//Field is 24.6 x 54
	private final float HEIGHT = 700; //Just a height in pixels that looks like a good size
	private final float WIDTH = (24.6f / 54f) * HEIGHT; //Multiply the ratio of width to height by our height to get calculated width
	private final double SCALE_FACTOR = 54d / (double)HEIGHT; //A scale factor in feet/px
	private final double ROBOT_SIZE = (2.3d/SCALE_FACTOR) / 50d; //Robot is 2.3ft in real life, and divide by the scale factor to get calculated robot px. Then divide by 50 because our starting value of px is 50 for our png file.

	private NetworkTable table;

	private double startx = 0, starty = 0, startAngle;

	BufferedImage field, robot;
	JButton b;
	public FieldView(ArrayList<Property> properties)
	{
		super(properties, true);
		//setLayout(null);
		removeMouseListener(mouseListener);
		removeMouseMotionListener(mouseListener);

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				table = NetworkTable.getTable("FieldCentric");
				table.addTableListener(FieldView.this, true);
			}
		}).start();

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
			robot = ImageIO.read(new File("robot_rough_3.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		applyProperties(addingFromSave);
	}

	double x = 0, y = 0, angle = 0;

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		Graphics2D g2d = (Graphics2D)g;

		g.drawImage(field, 0, 0, Math.round(WIDTH), Math.round(HEIGHT), this);

		//Apply the transformation to get x in feet to x in pixels (and same for y)
		int newx = (int)Math.round((1/SCALE_FACTOR * (x + startx)));
		int newy = (int)Math.round((1/SCALE_FACTOR * (y + starty)));

		//Printer.println("x: " + x + "\ty: " + y + "\tangle: " + angle);

		if(newx < 0)
			newx = 0;
		else if(newx > WIDTH)
			newx = Math.round(WIDTH);
		if(newy < 0)
			newy = 0;
		else if(newy > HEIGHT)
			newy = Math.round(HEIGHT);


		AffineTransform rotation = g2d.getTransform();

		double transx = newx - (ROBOT_SIZE*25), transy = newy - (ROBOT_SIZE*25);
		rotation.translate(transx,transy );


		rotation.rotate(Math.toRadians(angle + startAngle), ROBOT_SIZE*25, ROBOT_SIZE*25);
		rotation.scale(ROBOT_SIZE, ROBOT_SIZE);

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
		return WidgetInfo.FIELD_NAME;
	}

	@Override
	public void setValue(Object value)
	{
		super.setValue(value);
		Point p = (Point)value;
		System.out.println("Don't think I was using this!");

		//Property.getPropertyFromType(Property.Type.LOCATION, properties).setData(p.clone());
	}

	@Override
	public Object getValue()
	{
		return null;//new Point(x, y);
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

	@Override
	public void valueChanged(ITable iTable, String name, Object value, boolean b)
	{
		if(value instanceof Double || value instanceof Integer)
		{
			if(name.equals("x"))
			{
				y = -(Double)value;
			}
			else if(name.equals("y"))
			{
				x = -(Double)value;
			}
			else if(name.equals("angle"))
			{
				angle = (Double)value * 180 / Math.PI;
			}
		}

		repaint();
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

	public Point getStart()
	{
		return null;
		//return new Point(startx, starty);
	}

	public double getStartAngle()
	{
		return startAngle;
	}

	public void setStart(double x, double y, int angle)
	{
		this.startx = x;
		this.starty = y;
		this.angle = angle;

		repaint();
	}
}
