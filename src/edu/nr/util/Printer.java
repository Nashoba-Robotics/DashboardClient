package edu.nr.util;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by colin on 1/15/14.
 */
public class Printer
{
    private static PrintStream stream;
    public static void setOutputStream(PrintStream stream)
    {
        Printer.stream = stream;
    }

    public static void println(String message)
    {
        if(stream != null)
        {
            stream.println(message);
        }
    }

    public static void print(String message)
    {
        if(stream != null)
        {
            stream.print(message);
            stream.flush();
        }
    }
}
