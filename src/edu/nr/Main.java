package edu.nr;

import edu.nr.Components.*;
import edu.nr.Network.Network;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.util.OverlapChecker;
import edu.nr.util.Printer;
import edu.nr.util.SaveManager;
import edu.nr.util.SettingsManager;
import edu.wpi.first.wpilibj.tables.IRemote;
import edu.wpi.first.wpilibj.tables.IRemoteConnectionListener;

import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//TODO Implement properties right click menu
//TODO Test out using JSCrollPane to handle items out of bounds
//TODO Add a 'new' option to the file menu
//TODO Add more widgets for numbers and boolean
//TODO Add a status indicator

/**
 * @author co1in
 */
public class Main extends JFrame
{
    public JPanel panel;
    private ArrayList<MovableComponent> components = new ArrayList<MovableComponent>();

    public static boolean somethingIsBeingPressed = false;
    public Network network;
    private SaveManager saveManager;

    private Network.OnMessageReceivedListener messageListener;

    public Main()
    {
        super("NRDashboard - Loading");

        //NOTE: This code replaces the sytem output stream with a dummy one.
        //This is done because NetworkTables is printing debug output to the standard output (which a good library should not be doing)
        //As a result, we take the standard input, hand it to our own static class (Printer), set systems output stream to a dummy outputstream, and use Printer for printing instead.
        //This essentially suppresses all output from NetworkTables
        PrintStream dummyStream    = new PrintStream(new OutputStream(){
            public void write(int b) {
                //NO-OP
            }
        });
        Printer.setOutputStream(System.out);
        System.setOut(dummyStream);

        panel = new JPanel();
        panel.setBackground(new Color(200,200,200));
        setSize(1000, 700);

        addMenuBar();

        panel.setLayout(null);
        add(panel);

        network = new Network(SettingsManager.getTeamNumber());
        createMessageReceivedListener();
        network.setOnMessageReceivedListener(messageListener);

        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                int n = JOptionPane.showConfirmDialog(
                        Main.this,
                        "Save before quitting?",
                        "Save",
                        JOptionPane.YES_NO_OPTION);
                if(n == 0)
                {
                    if(SettingsManager.getLastSavePath() != null)
                    {
                        PropertiesManager.writeAllPropertiesToFile(SettingsManager.getLastSavePath(), components, main);
                        System.exit(0);
                    }
                    else
                        saveManager.showSaveDialog(true);
                }
                else
                    System.exit(0);
            }
        });

        if(SettingsManager.getLastSavePath() != null)
        {
            PropertiesManager.loadElementsFromFile(SettingsManager.getLastSavePath(), components, this);
            for(MovableComponent m : components)
            {
                panel.add(m);
            }

            panel.repaint();
            panel.revalidate();
        }

        //Initiate the networkTables connection
        network.connect();
        //Update the title of the window to inform user if we are connected or not
        network.getTable().addConnectionListener(new IRemoteConnectionListener()
        {
            @Override
            public void connected(IRemote iRemote)
            {
                setTitle("NRDashboard - Connected");
            }

            @Override
            public void disconnected(IRemote iRemote)
            {
                setTitle("NRDashboard - Disconnected");
            }
        }, true);

        //Try and get values for any blank components loaded from the save file
        for(MovableComponent comp : components)
            comp.attemptValueFetch();
    }

    private void createMessageReceivedListener()
    {
        messageListener = new Network.OnMessageReceivedListener()
        {
            @Override
            public void onMessageReceived(String key, Object value)
            {
                ArrayList<Property> addingProperties = new ArrayList<Property>();
                addingProperties.add(new Property(Property.Type.NAME, key));

                if(widgetWithName(key) != -1)
                {
                    components.get(widgetWithName(key)).setValue(value);
                }
                else
                {
                    if(value instanceof Double || value instanceof Integer)
                    {
                        addComponent(new NNumberField(components, addingProperties, Main.this), true, value);
                    }
                    else if(value instanceof String)
                    {
                        addComponent(new NTextField(components, addingProperties, Main.this), true, value);
                    }
                    else if(value instanceof Boolean)
                    {
                        addComponent(new NBooleanField(components, addingProperties, Main.this), true, value);
                    }
                    else if(value instanceof Object[])
                    {
                        Printer.println("Received an array: " + ((Object[]) value).getClass());
                        /*Printer.print("Received an array: {");
                        Object[] values = (Object[])value;
                        for(int i = 0; i < values.length; i++)
                        {
                            if(i != 0)
                                Printer.print(", ");
                            Printer.print(values[i] + "");
                        }
                        Printer.println("}");*/
                    }
                    else
                    {
                        Printer.println("Received a thing: " + value.getClass());
                    }
                }
            }
        };
    }

    private int widgetWithName(String name)
    {
        for(int i = 0; i < components.size(); i++)
        {
            if(components.get(i).getTitle().equals(name.trim()))
            {
                return i;
            }
        }
        return -1;
    }

    public static Main main;
    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        main = new Main();
    }

    private JCheckBoxMenuItem movableComponents;
    private void addMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(SettingsManager.getLastSavePath() != null)
                {
                    PropertiesManager.writeAllPropertiesToFile(SettingsManager.getLastSavePath(), components, main);
                }
                else
                {
                    saveManager.showSaveDialog(false);
                }
            }
        });

        JMenuItem saveAsFile = new JMenuItem(("Save As"));
        saveAsFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveManager.showSaveDialog(false);
            }
        });

        JMenuItem setIp = new JMenuItem("Set Team Number");
        setIp.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showIpDialog();
            }
        });


        movableComponents = new JCheckBoxMenuItem();
        movableComponents.setText("Editable");
        movableComponents.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(movableComponents.isSelected())
                {
                    //setTitle("NRDashboard (Editable)");
                    for(MovableComponent b : components)
                        b.setMovable(true);
                }
                else
                {
                    //setTitle("NRDashboard");
                    for(MovableComponent b : components)
                        b.setMovable(false);
                }
            }
        });

        JMenuItem removeUnused = new JMenuItem("Remove Unused");
        removeUnused.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                for(int i = 0; i < components.size(); i++)
                {
                    if(!components.get(i).valueHasBeenSet())
                    {
                        removeWidget(components.get(i));
                        i--;
                    }
                }
            }
        });

        JMenuItem openFile = new JMenuItem("Open...");
        openFile.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                saveManager.showOpenDialog();
            }
        });

        viewMenu.add(movableComponents);
        viewMenu.add(removeUnused);

        menu.add(openFile);
        menu.add(saveFile);
        menu.add(saveAsFile);
        //menu.add(addButtonItem);
        menu.add(setIp);
        setJMenuBar(menuBar);
    }

    public void showIpDialog()
    {
        String s = (String)JOptionPane.showInputDialog(
                this,
                "Enter your team number: ",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                SettingsManager.getTeamNumber());
        if(s != null)
        {
            for(int i = 0; i < 4; i++)
            {
                if(!Character.isDigit(s.charAt(i)))
                {
                    Printer.println("Error: team number must only contain numbers");
                    return;
                }
            }
            if(s.length() != 4)
            {
                Printer.println("Error: team number must be 4 letters long");
                return;
            }
            setIpAddress(s);
        }

    }

    public void setIpAddress(String teamNumber)
    {
        SettingsManager.writeTeamNumber(teamNumber);

        String options[] = {"Restart" , "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
                "You must restart to apply changes",
                "Restart Required",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

        if(n == 0)
        {
            restart();
        }
    }

    private void restart()
    {

    }

    private void addComponent(MovableComponent temp, boolean checkForOverlaps, Object value)
    {
        temp.setValue(value);
        temp.setMovable(movableComponents.isSelected());
        components.add(temp);

        if(checkForOverlaps)
        {
            addWithOverlapChecking(temp, components);
        }
        else
        {
            panel.add(temp);
            repaint();
            revalidate();
        }
    }

    private void addWithOverlapChecking(MovableComponent adding, ArrayList<MovableComponent> components)
    {
        panel.add(adding);
        Property.getPropertyFromType(Property.Type.LOCATION, adding.getProperties()).setData(OverlapChecker.getOpenAddingLocation(adding, components));
        adding.applyProperties(false);
        repaint();
    }

    public void removeWidget(MovableComponent m)
    {
        components.remove(m);
        panel.remove(m);
        panel.repaint();
        panel.revalidate();
    }

    public boolean isEditable()
    {
        return movableComponents.isSelected();
    }
}
