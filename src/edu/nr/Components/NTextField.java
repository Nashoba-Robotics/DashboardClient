package edu.nr.Components;

import edu.nr.Components.mouse_listeners.MyMouseListener;
import edu.nr.Main;
import edu.nr.properties.PropertiesManager;
import edu.nr.properties.Property;
import edu.nr.util.Printer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * User: co1in
 * Date: 11/13/13
 * Time: 10:22 PM
 */
public class NTextField extends MovableField
{
    public NTextField(ArrayList<MovableComponent> components, ArrayList<Property> loadedProperties, Main main, boolean addingFromSave)
    {
        super(components, loadedProperties, main, addingFromSave);
    }

    @Override
    protected void onTab()
    {
        NTextField.this.main.network.putString(getTitle(), field.getText());
    }

    private void loadProperties(ArrayList<Property> loaded)
    {
        PropertiesManager.loadPropertiesIntoArray(properties, loaded);
    }

    protected ArrayList<Property> getDefaultProperties()
    {
        ArrayList<Property> tempProperties = new ArrayList<Property>();
        tempProperties.add(new Property(Property.Type.SIZE, new Dimension(130,25)));
        tempProperties.add(new Property(Property.Type.LOCATION, new Point(0,0)));
        tempProperties.add(new Property(Property.Type.FOREGROUND, Color.BLACK));
        tempProperties.add(new Property(Property.Type.BACKGROUND, Color.WHITE));
        tempProperties.add(new Property(Property.Type.NAME, "Text Name"));
        tempProperties.add(new Property(Property.Type.WIDGET_TYPE, 1));
        tempProperties.add(new Property(Property.Type.FONT_SIZE, 14));

        return tempProperties;
    }

    @Override
    public String getWidgetName() {
        return WidgetNames.STRING_NAME;
    }

    public static String getStaticWidgetName()
    {
        return WidgetNames.STRING_NAME;
    }

    @Override
    public void setValue(Object o)
    {
        super.setValue(o);
        field.setText((String)o);
    }

    @Override
    public int getWidgetType()
    {
        return 1;
    }

    @Override
    public void attemptValueFetch()
    {
        setValue(main.network.getString(getTitle()));
    }
}
