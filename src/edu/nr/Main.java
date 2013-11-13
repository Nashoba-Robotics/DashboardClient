package edu.nr;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main extends JFrame
{
    JCheckBox box;
    JPanel panel;
    ArrayList<edu.nr.Button> buttons = new ArrayList<edu.nr.Button>();
    public Main()
    {
        super("Dashboard Client Example");
        setSize(500, 500);

        addMenuBar();

        panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        box = new JCheckBox();
        panel.add(box);
        box.setText("Movable Interface");

        Dimension size = box.getPreferredSize();
        box.setBounds(100, 5, size.width, size.height);

        box.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if(box.isSelected())
                {
                    for(edu.nr.Button b : buttons)
                        b.setEnabled(false);
                }
                else
                {
                    for(edu.nr.Button b: buttons)
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
                edu.nr.Button temp = new edu.nr.Button();
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
