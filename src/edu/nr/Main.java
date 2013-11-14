package edu.nr;

import edu.nr.Components.NButton;
import edu.nr.Components.NTextField;

import javax.swing.*;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//TODO Add a properties right click menu to widgets
//TODO ADd remove widget functionality to right click menu
//TODO When many widgets are being added, don't add them on top of each other
//TODO Add widgets for: Boolean, Numbers, Graphs, Camera
//TODO Talk to Brandon about how he is sending data over bytes (for use in the Network class)

/**
 * @author co1in
 */
public class Main extends JFrame
{
    JPanel panel;
    private ArrayList<MovableComponent> components = new ArrayList<MovableComponent>();
    public Main()
    {
        super("Dashboard Client");
        setSize(500, 500);

        addMenuBar();

        panel = new JPanel();
        panel.setLayout(null);
        panel.setDoubleBuffered(true);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new Main();
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
                NButton temp = new NButton(components, -1);
                if(movableComponents.isSelected())
                    temp.setMovable(true);
                else
                    temp.setMovable(false);
                components.add(temp);
                temp.setSize(100,30);
                panel.add(temp);
                repaint();
            }
        });

        JMenuItem addFieldItem = new JMenuItem("Add Text Field");
        addFieldItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                NTextField temp = new NTextField(components, -1, "Text Field Label");
                if(movableComponents.isSelected())
                    temp.setMovable(true);
                else
                    temp.setMovable(false);
                components.add(temp);
                temp.setSize(150,50);
                panel.add(temp);
                repaint();
                revalidate();
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
                    for(MovableComponent b : components)
                        b.setMovable(true);
                }
                else
                {
                    for(MovableComponent b : components)
                        b.setMovable(false);
                }
            }
        });

        viewMenu.add(movableComponents);

        menu.add(addFieldItem);
        menu.add(addButtonItem);
        setJMenuBar(menuBar);
    }
}
