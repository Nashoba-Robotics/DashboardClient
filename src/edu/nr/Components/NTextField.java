package edu.nr.Components;

import edu.nr.MovableComponent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * User: co1in
 * Date: 11/13/13
 * Time: 10:22 PM
 */
public class NTextField extends JPanel implements MovableComponent
{
    private int id = -1;
    private ArrayList<MovableComponent> components;
    private JTextField field = new JTextField();
    private JLabel label;

    public NTextField(ArrayList<MovableComponent> components, int id, String title)
    {
        this.id = id;
        this.components = components;

        setLayout(new GridLayout(2,1));
        setBounds(0, 0, 100, 100);
        setBorder(new LineBorder(Color.BLACK, 1));
        //setBackground(Color.WHITE);

        label = new JLabel(title);
        field.setText("Text");

        add(label);
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setForeground(Color.BLACK);
        add(field);

        MyMouseListener listener = new MyMouseListener(NTextField.this, components);
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    @Override
    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public void setMovable(boolean movable)
    {
        setEnabled(!movable);
        field.setEnabled(!movable);
    }
}
