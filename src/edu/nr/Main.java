package edu.nr;

import edu.nr.Components.Button;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

        JMenuItem addButtonItem = new JMenuItem("Add Button");
        addButtonItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Button temp = new Button(components);
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


        movableComponents = new JCheckBoxMenuItem();
        movableComponents.setText("Edit Interface");
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

        menu.add(movableComponents);
        menu.add(addButtonItem);
        setJMenuBar(menuBar);
    }
}
