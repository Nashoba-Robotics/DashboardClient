package edu.nr.Components;
import edu.nr.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import javax.imageio.ImageIO;
import java.awt.*;
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
	public FieldView(ArrayList<Property> properties, boolean addingFromSave, NetworkTable table)
	{
		super(properties, addingFromSave);
		mouseListener.setOverlapCheckingEnabled(false);
		mouseListener.setResizingEnabled(false);
		this.table = table;

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
	}

	int x = 0, y = 0, angle = 0;

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
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


		AffineTransform rotation = new AffineTransform();

		rotation.setToScale(ROBOT_SCALE_FACTOR, ROBOT_SCALE_FACTOR);
		rotation.rotate(Math.toRadians(angle), x + (robot.getWidth() / 2), y + (robot.getHeight() / 2));
		rotation.translate(x, y);


		g2d.setTransform(rotation);

		g.drawImage(robot, 0, 0, this);
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
		return tempProperties;
	}

	@Override
	public void applyProperties(boolean setSize)
	{

	}

	@Override
	public String getWidgetName()
	{
		return null;
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
}
