package edu.nr.Network;

import edu.nr.Components.MovableComponent;
import edu.nr.Main;
import edu.nr.util.Printer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author co1in
 */
public class Network implements ITableListener
{
    private NetworkTable table, fieldTable;
    private final String DASHBOARD_NAME = "SmartDashboard";

    private OnMessageReceivedListener listener = null;

    private Network(){}

    public Network(String ip)
    {
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.17.68.2");
        //TODO Change above line to use team number
    }

    public void connect()
    {
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				fieldTable = NetworkTable.getTable("FieldCentric");
				fieldTable.addTableListener(Network.this);

				table = NetworkTable.getTable(DASHBOARD_NAME);
				table.addTableListener(Network.this);
				table.addSubTableListener(Network.this);

				getTable().addConnectionListener(new IRemoteConnectionListener()
				{
					@Override
					public void connected(IRemote iRemote)
					{
						Main.mainVar.setTitle("NRDashboard - Connected");
					}

					@Override
					public void disconnected(IRemote iRemote)
					{
						Main.mainVar.setTitle("NRDashboard - Disconnected");
					}
				}, true);

				//Try and get values for any blank components loaded from the save file
				for(MovableComponent comp : Main.mainVar.getComponentsList())
					comp.attemptValueFetch();
			}
		}).start();
    }

    public void putString(String key, String value)
    {
        table.putString(key, value);
    }

    public void putNumber(String key, Double value)
    {
        table.putNumber(key, value);
    }

    public void putBoolean(String key, Boolean value)
    {
        table.putBoolean(key, value);
    }

    public void setOnMessageReceivedListener(OnMessageReceivedListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void valueChanged(ITable iTable, String s, Object o, boolean b)
    {
        //Printer.println("MESSAGE: " + s + ": " + o);
        if(iTable == table)
		{
			if(listener != null)
			{
				listener.onMessageReceived(s, o);
			}
		}
		else if(iTable == fieldTable)
		{

		}
    }

    public interface OnMessageReceivedListener
    {
        public void onMessageReceived(String key, Object value);
    }

    public NetworkTable getTable()
    {
        return table;
    }

    public String getString(String key)
    {
        return table.getString(key, "");
    }

    public Boolean getBoolean(String key)
    {
        try
        {
            return table.getBoolean(key);
        }
        catch (TableKeyNotDefinedException e)
        {
            return null;
        }
    }

    public Double getNumber(String key)
    {
        try
        {
            return table.getNumber(key);
        }
        catch (TableKeyNotDefinedException e)
        {
            return null;
        }
    }

	public NetworkTable getNetworkSubTable(String name)
	{
		return (NetworkTable)table.getSubTable(name);
	}
}
