package edu.nr.Components;

import edu.nr.Components.extras.TabListener;
import edu.nr.properties.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 1/20/14
 *         Time: 12:00 AM
 */
public abstract class MovableField extends MovableComponent
{
    protected JTextField field;
    protected JLabel label;

    private final int FIELD_MIN_WIDTH = 80;

    public MovableField(ArrayList<Property> properties, boolean addingFromSave)
    {
        super(properties, addingFromSave);

        setLayout(new BoxLayout(this, 0));

        field = new JTextField();
        field.addMouseListener(mouseListener);
        field.addMouseMotionListener(mouseListener);

        label = new JLabel();

		field.addKeyListener(new TabListener(new Runnable()
		{
			@Override
			public void run()
			{
				onTab();
			}
		}));
		field.setFocusTraversalKeysEnabled(false);

        add(label);
        add(field);
        applyProperties(addingFromSave);
    }

    private final Runnable tabRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            onTab();
        }
    };

    protected abstract void onTab();

    @Override
    public final void setMovable(boolean movable)
    {
        isMovable = movable;
        field.setEnabled(!movable);
    }

    @Override
    public final String getTitle()
    {
        return label.getText().trim();
    }

    @Override
    public final void applyProperties(boolean setSize)
    {
        label.setText(" " + Property.getPropertyFromType(Property.Type.NAME, properties).getData() + " ");
        field.setForeground((Color) Property.getPropertyFromType(Property.Type.FOREGROUND, properties).getData());
        setBackground((Color) Property.getPropertyFromType(Property.Type.BACKGROUND, properties).getData());
        if(setSize)
            setSize((Dimension) Property.getPropertyFromType(Property.Type.SIZE, properties).getData());
        else
        {
            int pref = FIELD_MIN_WIDTH;
            pref += label.getPreferredSize().width;
            setSize(new Dimension(pref, ((Dimension) Property.getPropertyFromType(Property.Type.SIZE, getDefaultProperties()).getData()).height));
        }
        setLocation((Point)Property.getPropertyFromType(Property.Type.LOCATION, properties).getData());

        //Load the font size
        label.setFont(new Font("Arial", Font.BOLD, (Integer) Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData() - 1));
        field.setFont(new Font("Arial", Font.PLAIN, (Integer) Property.getPropertyFromType(Property.Type.FONT_SIZE, properties).getData()));
    }
}
