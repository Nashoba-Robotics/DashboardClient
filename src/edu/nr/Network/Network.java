package edu.nr.Network;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author co1in
 */
public class Network
{
    private String ip;
    private int port;

    private Socket crioSocket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean successfullyInitialized =false;

    private OnMessageReceivedListener listener = null;

    private Network(){}

    public Network(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    public void connect()
    {
        try
        {
            crioSocket = new Socket(ip, port);
            inputStream = crioSocket.getInputStream();
            outputStream = crioSocket.getOutputStream();
            successfullyInitialized = true;
            startListeningThread();
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void startListeningThread()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    byte[] input = new byte[1];
                    try
                    {
                        inputStream.read(input);
                        if(listener != null)
                        {
                            listener.onMessageReceived(input);
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        thread.start();
    }

    public void sendMessage(byte[] message)
    {
        try
        {
            outputStream.write(message);
            outputStream.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setOnMessageReceivedListener(OnMessageReceivedListener listener)
    {
        this.listener = listener;
    }

    public interface OnMessageReceivedListener
    {
        public void onMessageReceived(byte[] data);
    }
}
