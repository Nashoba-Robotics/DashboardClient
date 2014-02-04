package edu.nr.Components;

import edu.nr.Main;
import edu.nr.properties.Property;
import edu.nr.util.Printer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Timer;

/**
 * @author co1in
 *         Date: 2/2/14
 *         Time: 4:39 PM
 */
public class NGraph extends MovableComponent
{
	private XYSeries series;
	ChartPanel plotPanel;
	private long startTime;
	private String name;
	private boolean graphStarted = false;
	private double currentValue;
	private double sleepTime;
	private boolean autoRefresh;

	public NGraph(ArrayList<MovableComponent> components, ArrayList<Property> properties, Main main, boolean addingFromSave)
	{
		super(components, properties, main, addingFromSave);
		series = new XYSeries("Field Name");
	}

	@Override
	public void setMovable(boolean movable)
	{
		isMovable = movable;
	}

	@Override
	protected ArrayList<Property> getDefaultProperties()
	{
		ArrayList<Property> tempProperties = new ArrayList<Property>();
		tempProperties.add(new Property(Property.Type.SIZE, new Dimension(300,300)));
		tempProperties.add(new Property(Property.Type.LOCATION, new Point(0,0)));
		tempProperties.add(new Property(Property.Type.NAME, "Graph"));
		tempProperties.add(new Property(Property.Type.AUTOREFRESH, true));
		tempProperties.add(new Property(Property.Type.GRAPH_AXIS_NAME, "Y Axis"));
		tempProperties.add(new Property(Property.Type.GRAPH_REFRESH_RATE, 100));

		return tempProperties;
	}

	@Override
	public void applyProperties(boolean setSize)
	{
		name = (String)Property.getPropertyFromType(Property.Type.NAME, properties).getData();
		setSize((Dimension)Property.getPropertyFromType(Property.Type.SIZE, properties).getData());
		setLocation((Point)Property.getPropertyFromType(Property.Type.LOCATION, properties).getData());
		String axisName = ((String)Property.getPropertyFromType(Property.Type.GRAPH_AXIS_NAME, properties).getData());
		sleepTime = (Integer)Property.getPropertyFromType(Property.Type.GRAPH_REFRESH_RATE, properties).getData();
		autoRefresh = (Boolean)Property.getPropertyFromType(Property.Type.AUTOREFRESH, properties).getData();

		XYDataset set = new XYSeriesCollection(series);
		plotPanel = new ChartPanel(ChartFactory.createXYLineChart(name, "Time (s)",axisName, set, PlotOrientation.VERTICAL, false, true, false ));

		this.removeAll();
		add(plotPanel, BorderLayout.CENTER);
	}

	@Override
	public void setValue(Object o)
	{
		super.setValue(o);
		if(!graphStarted)
		{
			startTime = System.currentTimeMillis();
			graphStarted = true;
			currentValue = (Double)o;
			if(autoRefresh)
				startTimerThread();
			else
			{
				long deltaTime = startTime - System.currentTimeMillis();
				double floatDelta = deltaTime;
				series.add(floatDelta, (Double)o);
			}
		}
		else
		{
			if(currentValue != (double)((Double)o))
			{
				long deltaTime = startTime - System.currentTimeMillis();
				double floatDelta = deltaTime;
				series.add(floatDelta, (Double)o);
				currentValue = (Double)o;
			}
		}
	}

	private Thread timerThread = null;
	private void startTimerThread()
	{
		timerThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while(autoRefresh)
				{
					long deltaTime = startTime - System.currentTimeMillis();
					double floatDelta = deltaTime;
					series.add(floatDelta, currentValue);

					try
					{
						Thread.sleep((long)sleepTime);
					}
					catch (InterruptedException e)
					{
						Printer.println("Timer thread was interrupted on graph: " + name);
					}
				}
				timerThread = null;
			}
		});
	}

	@Override
	public String getWidgetName()
	{
		return WidgetNames.NUMBER_NAME;
	}

	@Override
	public String getTitle()
	{
		return name;
	}

	@Override
	public int getWidgetType()
	{
		return 2;
	}

	@Override
	public void attemptValueFetch()
	{
		//Don't think the best thing to do is start the graph prematurely
		//However, if we wanted to, it would look something like this:

		/*Double d = main.network.getNumber(name);
		if(d != null)
			currentValue = d;*/
	}

}
