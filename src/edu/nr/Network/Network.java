package edu.nr.Network;

import edu.nr.util.Printer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.IRemote;
import edu.wpi.first.wpilibj.tables.IRemoteConnectionListener;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author co1in
 */
public class Network implements ITableListener
{
    private NetworkTable table;
    private final String DASHBOARD_NAME = "SmartDashboard";

    private OnMessageReceivedListener listener = null;

    private Network(){}

    public Network(String ip)
    {
        NetworkTable.setClientMode();
        NetworkTable.setIPAddress("localhost");
        //TODO Change above line to use team number
    }

    public void connect()
    {
        table = NetworkTable.getTable(DASHBOARD_NAME);
        table.addTableListener(this);
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
        if(listener != null)
        {
            listener.onMessageReceived(s, o);
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
}
