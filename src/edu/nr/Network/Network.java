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
public class Network implements ITableListener, IRemoteConnectionListener
{
    private NetworkTable table;
    private final String DASHBOARD_NAME = "NRDashboard";

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
        table.addConnectionListener(this, true);
    }

    public void putString()
    {

    }

    public void putNumber()
    {

    }

    public void putBoolean()
    {

    }

    public void setOnMessageReceivedListener(OnMessageReceivedListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void valueChanged(ITable iTable, String s, Object o, boolean b)
    {
        Printer.println("MESSAGE: " + s + ": " + o);
        if(listener != null)
        {
            listener.onMessageReceived(s, o);
        }
    }

    @Override
    public void connected(IRemote iRemote)
    {
        Printer.println("Connected to Server");
    }

    @Override
    public void disconnected(IRemote iRemote)
    {
        Printer.println("Disconnected from Server");
        //TODO Add indicator light
    }

    public interface OnMessageReceivedListener
    {
        public void onMessageReceived(String key, Object value);
    }
}
