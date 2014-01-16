package edu.nr.util;

import java.io.*;

/**
 * Created by colin on 1/15/14.
 */
public class TeamNumberManager
{
    public static void writeTeamNumber(String number)
    {
        File f = new File(filePath());
        if(!f.exists())
        {
            try
            {
                new File(folderPath()).mkdirs();
                f.createNewFile();
            }
            catch (IOException e)
            {
                Printer.println("Error: " + "couldn't create new file for writing");
            }
        }

        try
        {
            FileWriter writer = new FileWriter(f);
            writer.write(number);
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTeamNumber()
    {
        File f = new File(filePath());
        if(!f.exists())
        {
            try
            {
                new File(folderPath()).mkdirs();
                f.createNewFile();
                FileWriter writer = new FileWriter(f);
                writer.write("1768");
                writer.flush();
                writer.close();
            }
            catch (IOException e)
            {
                Printer.println("Couldn't create file");
            }

        }
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            return reader.readLine();

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        throw new RuntimeException("Error: couldn't load team number from file");
    }

    private static String homePath()
    {
        return System.getProperty("user.home") + File.separator;
    }

    private static String folderPath()
    {
        return homePath() + ".NRDashboard" + File.separator;
    }

    private static String filePath()
    {
        return folderPath() + "team";
    }
}
