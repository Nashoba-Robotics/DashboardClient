package edu.nr;

import edu.nr.Components.NButton;
import edu.nr.Components.NTextField;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//TODO Add a properties right click menu to widgets
//TODO Add remove widget functionality to right click menu
//TODO When many widgets are being added, don't add them on top of each other
//TODO Add widgets for: Boolean, Numbers, Graphs, Camera
//TODO Talk to Brandon about how he is sending data over bytes (for use in the Network class)
//TODO Finish XML Loading and Writing in PropertiesManager class

/**
 * @author co1in
 */
public class Main extends JFrame
{
    JPanel panel;
    JFileChooser saveChooser, openChooser;
    private ArrayList<MovableComponent> components = new ArrayList<MovableComponent>();
    public Main()
    {
        super("Dashboard Client");
        panel = new JPanel();
        panel.setBackground(new Color(50,50,50));
        saveChooser = new JFileChooser();
        openChooser = new JFileChooser();
        setSize(1000, 700);

        addMenuBar();

        panel.setLayout(null);
        panel.setDoubleBuffered(true);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
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

        JMenuItem addButtonItem = new JMenuItem("Add Button");
        addButtonItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addButton();
            }
        });

        JMenuItem addFieldItem = new JMenuItem("Add Text Field");
        addFieldItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addTextField();
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
        menu.add(addButtonItem);
        setJMenuBar(menuBar);
    }

    private void addTextField()
    {
        NTextField temp = new NTextField(components, null);
        if(movableComponents.isSelected())
            temp.setMovable(true);
        else
            temp.setMovable(false);
        components.add(temp);
        panel.add(temp);
        repaint();
        revalidate();
    }

    private void addButton()
    {
        ArrayList<Property> properties = new ArrayList<Property>();
        properties.add(new Property(Property.Type.SIZE, new Dimension(200,200)));
        NButton temp = new NButton(components, null);
        if(movableComponents.isSelected())
            temp.setMovable(true);
        else
            temp.setMovable(false);
        components.add(temp);
        panel.add(temp);
        repaint();
    }

    private void showSaveDialog()
    {
        saveChooser.setDialogTitle("Choose a save file");
        int returnValue = saveChooser.showSaveDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            PropertiesManager.writeAllPropertiesToFile(saveChooser.getSelectedFile().getPath(), components);
        }
        else
        {
            System.out.println("save aborted");
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
            PropertiesManager.loadElementsFromFile(openChooser.getSelectedFile().getPath());
        }
        else
        {
            System.out.println("Open aborted");
        }

    }
}
