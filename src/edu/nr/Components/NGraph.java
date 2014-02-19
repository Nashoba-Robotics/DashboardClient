package edu.nr.Components;

import edu.nr.Components.extras.WidgetInfo;
import edu.nr.properties.Property;
import edu.nr.util.Printer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	private double currentValue;
	private double sleepTime;
	private boolean autoRefresh;
	private JPopupMenu originalMenu;

	public NGraph(ArrayList<Property> properties, boolean addingFromSave)
	{
		super(properties, addingFromSave);
		series = new XYSeries("Field Name");

		applyProperties(addingFromSave);
	}

	@Override
	public void setMovable(boolean movable)
	{
		isMovable = movable;
		if(movable)
		{
			plotPanel.setMouseWheelEnabled(false);
			plotPanel.setMouseZoomable(false);
			plotPanel.setPopupMenu(mouseListener.rightClickMenu);
		}
		else
		{
			plotPanel.setMouseWheelEnabled(true);
			plotPanel.setMouseZoomable(true);
			plotPanel.setPopupMenu(originalMenu);
		}
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
		originalMenu = plotPanel.getPopupMenu();

		JMenuItem resetItem = new JMenuItem("Reset");
		resetItem.addActionListener(new ResetListener());

		originalMenu.add(resetItem, 0);

		plotPanel.addMouseListener(mouseListener);
		plotPanel.addMouseMotionListener(mouseListener);
		add(plotPanel, BorderLayout.CENTER);
	}

	private class ResetListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			series.clear();
		}
	}

	int graphStarted = 0;
	@Override
	public void setValue(Object o)
	{
		super.setValue(o);
		if(graphStarted == 1)
		{
			startTime = System.currentTimeMillis();
			graphStarted++;
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
		else if(graphStarted == 0)
		{
			graphStarted++;
			currentValue = (Double)o;
		}
		else
		{
			if(currentValue != ((Double)o))
			{
				long deltaTime = System.currentTimeMillis() - startTime;
				double floatDelta = deltaTime/1000f;
				series.add(floatDelta, (Double)o);
				currentValue = (Double)o;
			}
		}
	}

	private Thread timerThread = null;
	private void startTimerThread()
	{
		/*timerThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while(autoRefresh)
				{
					long deltaTime = System.currentTimeMillis() - startTime;
					double floatDelta = deltaTime;
					series.add(floatDelta/1000, currentValue);

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
		timerThread.start();*/
	}

	public void stopTimerThread()
	{
		autoRefresh = false;
	}

	@Override
	public String getWidgetName()
	{
		return WidgetInfo.NUMBER_NAME;
	}

	@Override
	public Object getValue()
	{
		return currentValue;
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

		/*Double d = mainVar.network.getNumber(name);
		if(d != null)
			currentValue = d;*/
	}

	@Override
	public String[] getWidgetChoices()
	{
		return WidgetInfo.numberNames;
	}
}
