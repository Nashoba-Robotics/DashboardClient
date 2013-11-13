package com.nashobarobotics;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

public class Main extends JFrame
{
    JCheckBox box;
    JPanel panel;
    ArrayList<Button> buttons = new ArrayList<Button>();
    public Main()
    {
        super("Dashboard Client Example");
        setSize(500, 500);

        addMenuBar();

        panel = new JPanel();
        add(panel);

        box = new JCheckBox();
        panel.add(box);
        box.setText("Movable Interface");
        box.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if(box.isSelected())
                {
                    for(Button b : buttons)
                        b.setEnabled(false);
                }
                else
                {
                    for(Button b: buttons)
                        b.setEnabled(true);
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new Main();
    }

    private void addMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem addButtonItem = new JMenuItem("Add Button");
        addButtonItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Button temp = new Button();
                if(box.isSelected())
                    temp.setEnabled(false);
                buttons.add(temp);
                temp.setSize(100,30);
                panel.add(temp);
                repaint();
            }
        });
        menu.add(addButtonItem);
        setJMenuBar(menuBar);
    }
}
