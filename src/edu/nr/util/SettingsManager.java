package edu.nr.util;

import java.io.*;

/**
 * Created by colin on 1/15/14.
 */
public class SettingsManager
{
    public static void writeTeamNumber(String number)
    {
        File f = new File(teamFilePath());
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
        catch (IOException e)
        {
            Printer.println(e.toString());
        }
    }

    public static String getTeamNumber()
    {
        File f = new File(teamFilePath());
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
            String result = reader.readLine();
            if(result.length() != 4 && (!isNumbers(result)))
            {
                Printer.println("Error: " + "loaded team number is invalid");
                return "0000";
            }
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

    public static void writeSavePath(String path)
    {
        File f = new File(saveFilePath());
        if(!f.exists())
        {
            try
            {
                new File(folderPath()).mkdirs();
                f.createNewFile();
            }
            catch (Exception e)
            {
                Printer.println("Error: couldn't create new save file location for writing");
            }
        }

        try
        {
            PrintWriter writer = new PrintWriter(new FileWriter(f), true);
            writer.println(path);
            writer.close();
        }
        catch (IOException e)
        {
            Printer.println(e.toString());
        }
    }

    public static void deleteLastSavePath()
    {
        File f = new File(saveFilePath());
        if(f.exists())
        {
            try
            {
                PrintWriter writer = new PrintWriter(new FileWriter(f), true);
                writer.println("null");
                writer.close();
            }
            catch (IOException e)
            {
                Printer.println(e.toString());
            }
        }
    }

    public static String getLastSavePath()
    {
        File f = new File(saveFilePath());
        if(!f.exists())
        {
            return null;
        }
        else
        {
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String path = reader.readLine();
                if(path.equals("null"))
                    return null;
                else
                    return path;
            }
            catch (IOException e)
            {
                Printer.println(e.toString());
            }

        }
        return null;
    }

    private static boolean isNumbers(String s)
    {
        for(int i = 0; i < s.length(); i++)
        {
            if(!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }

    private static String homePath()
    {
        return System.getProperty("user.home") + File.separator;
    }

    private static String folderPath()
    {
        return homePath() + ".NRDashboard" + File.separator;
    }

    private static String teamFilePath()
    {
        return folderPath() + "team";
    }

    private static String saveFilePath()
    {
        return folderPath() + "save";
    }
}
