package edu.nr;

import edu.nr.Components.MovableComponent;
import edu.nr.Components.NButton;
import edu.nr.Components.NNumberField;
import edu.nr.Components.NTextField;
import edu.nr.Network.Network;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.util.OverlapChecker;
import edu.nr.util.Printer;
import edu.nr.util.TeamNumberManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//TODO Implement properties right click menu
//TODO Add remove widget functionality to right click menu
//TODO Add widgets for: Boolean, Numbers, Graphs, Camera
//TODO Talk to Brandon about how he is sending data over bytes (for use in the Network class)
//TODO Finish XML Loading in PropertiesManager class
//TODO Test out using JSCrollPane to handle items out of bounds

/**
 * @author co1in
 */
public class Main extends JFrame
{
    JPanel panel;
    JFileChooser saveChooser, openChooser;
    private ArrayList<MovableComponent> components = new ArrayList<MovableComponent>();

    public static boolean somethingIsBeingPressed = false;
    public Network network;

    private Network.OnMessageReceivedListener messageListener;

    public Main()
    {
        super("NRDashboard");

        //NOTE: This code replaces the sytem output stream with a dummy one.
        //This is done because NetworkTables is printing output to the standard output (which a good library should not be doing)
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
        panel.setBackground(new Color(50,50,50));
        saveChooser = new JFileChooser();
        openChooser = new JFileChooser();
        setSize(1000, 700);

        addMenuBar();

        panel.setLayout(null);
        panel.setDoubleBuffered(true);
        add(panel);

        network = new Network(TeamNumberManager.getTeamNumber());
        createMessageReceivedListener();
        network.setOnMessageReceivedListener(messageListener);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        network.connect();
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

                MovableComponent existingComponent = null;
                if(widgetWithName(key) != -1)
                    existingComponent = components.get(widgetWithName(key));

                if(existingComponent != null)
                {
                    existingComponent.setValue(value);
                }
                else
                {
                    if(value.getClass() == java.lang.Double.class || value.getClass() == java.lang.Integer.class)
                    {
                        addNumber(addingProperties, true, (Double)value);
                    }
                    else if(value.getClass() == java.lang.String.class)
                    {
                        addTextField(addingProperties, true, (String)value);
                    }
                }
            }
        };
    }

    private int widgetWithName(String name)
    {
        for(int i = 0; i < components.size(); i++)
        {
            if(components.get(i).getTitle().equals(name))
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

        /*JMenuItem addButtonItem = new JMenuItem("Add Button");
        addButtonItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addButton(null, true);
            }
        });
        */
        JMenuItem addFieldItem = new JMenuItem("Add Text Field");
        addFieldItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addTextField(null, true, "");
            }
        });

        JMenuItem saveFile = new JMenuItem(("Save Layout"));
        saveFile.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showSaveDialog();
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
                    setTitle("Dashboard Client (Editable)");
                    for(MovableComponent b : components)
                        b.setMovable(true);
                }
                else
                {
                    setTitle("Dashboard Client");
                    for(MovableComponent b : components)
                        b.setMovable(false);
                }
            }
        });

        JMenuItem openFile = new JMenuItem("Open...");
        openFile.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showOpenDialog();
            }
        });

        viewMenu.add(movableComponents);

        menu.add(openFile);
        menu.add(saveFile);
        menu.add(addFieldItem);
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
                TeamNumberManager.getTeamNumber());
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
        TeamNumberManager.writeTeamNumber(teamNumber);

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

    private void addTextField(ArrayList<Property> properties, boolean checkForOverlaps, String value)
    {
        NTextField temp = new NTextField(components, properties, main);
        temp.setValue(value);
        if(movableComponents.isSelected())
            temp.setMovable(true);
        else
            temp.setMovable(false);
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

    private void addButton(ArrayList<Property> properties, boolean checkForOverlaps)
    {
        NButton temp = new NButton(components, properties, main);
        if(movableComponents.isSelected())
            temp.setMovable(true);
        else
            temp.setMovable(false);
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

    private void addNumber(ArrayList<Property> properties, boolean checkForOverlaps, double value)
    {
        NNumberField temp = new NNumberField(components, properties, this);
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
        adding.applyProperties();
        repaint();
    }

    private void showSaveDialog()
    {
        saveChooser.setDialogTitle("Choose a save file");
        int returnValue = saveChooser.showSaveDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            PropertiesManager.writeAllPropertiesToFile(saveChooser.getSelectedFile().getPath(), components, main);
        }
        else
        {
            Printer.println("save aborted");
        }
    }

    private void showOpenDialog()
    {
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
        openChooser.setFileFilter(xmlfilter);
        openChooser.setDialogTitle("Select a file to open");
        int returnValue = openChooser.showOpenDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            components.clear();
            panel.removeAll();
            PropertiesManager.loadElementsFromFile(openChooser.getSelectedFile().getPath(), components, main);

            for(MovableComponent m : components)
            {
                panel.add(m);
            }
            panel.repaint();
            panel.revalidate();
        }
        else
        {
            Printer.println("Open aborted");
        }

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
