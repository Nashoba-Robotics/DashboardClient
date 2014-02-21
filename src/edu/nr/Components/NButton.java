package edu.nr.Components;

import edu.nr.Components.extras.WidgetInfo;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.properties.Property.Type;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author co1in
 */
public class NButton extends MovableComponent implements ITableListener
{

    private JButton button;
	private NetworkTable table;
	private JLabel label;

    public NButton(NetworkTable table, ArrayList<Property> properties, boolean addingFromSave)
    {
        super(properties, addingFromSave);
		if(table != null)
		{
			this.table = table;
		}
		else
		{
			//TODO Add loading from save option
		}
		button = new JButton();
		label = new JLabel();

		add(label, BorderLayout.WEST);
        add(button, BorderLayout.EAST);

        button.setFocusable(false);
        setBackground(Color.WHITE);

        button.addMouseListener(mouseListener);
        button.addMouseMotionListener(mouseListener);

        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
				boolean settingValue = button.getText().equals(BUTTON_START_TEXT);
				setButtonValue(settingValue);
				NButton.this.table.putBoolean("running", settingValue);
            }
        });
		setButtonValue(false);

		applyProperties(addingFromSave);
		this.table.addTableListener(this, true);
    }

    public void applyProperties(boolean setSize)
    {
        applyProperties(this.properties, setSize);
    }

    public void applyProperties(ArrayList<Property> applyingProperties, boolean setSize)
    {
        PropertiesManager.loadPropertiesIntoArray(properties, applyingProperties);
        for(Property p : properties)
        {
            Type type = p.getType();
            if(type == Type.SIZE)
            {
				if(setSize)
				{
					Dimension d = (Dimension) p.getData();
					setSize(new Dimension((int)d.getWidth()-1, (int)d.getHeight()-1));
					setMaximumSize(new Dimension((int)d.getWidth()-1, (int)d.getHeight()-1));
					validate();
				}
				else
				{
					int width = button.getPreferredSize().width + label.getPreferredSize().width;
					Dimension d = new Dimension(width + 5, ((Dimension)Property.getPropertyFromType(Type.SIZE, getDefaultProperties()).getData()).height);
					setSize(d);
					setMaximumSize(d);
					setPreferredSize(d);
				}
            }
            else if(type == Type.LOCATION)
            {
                setLocation((Point)p.getData());
            }
            else if(type == Type.FOREGROUND)
            {
                label.setForeground((Color)p.getData());
            }
            else if(type == Type.BACKGROUND)
            {
                setBackground((Color)p.getData());
            }
            else if(type == Type.NAME)
            {
                label.setText(" " + (String)p.getData());
            }
            else if(type == Type.FONT_SIZE)
            {
                button.setFont(new Font("Arial",Font.PLAIN, ((Integer)p.getData())));
            }
        }
    }

    protected ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Type.SIZE, new Dimension(100,32)));
        tempProperties.add(new Property(Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Type.BACKGROUND, new Color(220,220,220)));
        tempProperties.add(new Property(Type.NAME, "Button"));//TODO Put actual name
        tempProperties.add(new Property(Type.WIDGET_TYPE, 1));
        tempProperties.add(new Property(Type.FONT_SIZE, 9));
        return tempProperties;
    }

    @Override
    public void setMovable(boolean movable)
    {
        isMovable = movable;
        button.setEnabled(!movable);
    }

    public String getWidgetName()
    {
        return WidgetInfo.BUTTON_NAME;
    }

	@Override
	public Object getValue()
	{
		return null;
	}

	@Override
    public void setValue(Object o)
    {
        super.setValue(o);
    }

    @Override
    public String getTitle()
    {
        return label.getText().trim();
    }

    @Override
    public int getWidgetType() {
        return 1;
    }

    @Override
    public void attemptValueFetch()
    {
        //Nothing to do here
    }

	@Override
	public String[] getWidgetChoices()
	{
		return new String[0];
	}

	private final String BUTTON_START_TEXT = "start";
	private void setButtonValue(boolean value)
	{
		if(value)
		{
			button.setText("stop");
			button.setForeground(new Color(200, 0, 0));
		}
		else
		{
			button.setText(BUTTON_START_TEXT);
			button.setForeground(new Color(0, 150, 0));
		}
	}

	@Override
	public void valueChanged(ITable iTable, String name, Object value, boolean isNewValue)
	{
		if(value instanceof String)
		{
			if(name.equals("name"))
			{
				label.setText(" " + (String)value);
			}
		}
		else if(value instanceof Boolean)
		{
			if(name.equals("running"))
			{
				boolean realValue = (Boolean)value;
				setButtonValue(realValue);
			}
		}
	}
}
