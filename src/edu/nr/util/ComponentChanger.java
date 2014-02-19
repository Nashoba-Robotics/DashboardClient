package edu.nr.util;

import edu.nr.Components.MovableComponent;
import edu.nr.Components.NGraph;
import edu.nr.Components.NNumberField;
import edu.nr.Components.extras.WidgetInfo;
import edu.nr.properties.Property;

import java.awt.*;

/**
 * @author co1in
 *         Date: 2/5/14
 *         Time: 12:19 PM
 */
public class ComponentChanger
{
	public static MovableComponent changeComponent(int index, MovableComponent from)
	{
		MovableComponent comp = null;
		if(from.getWidgetName().equals(WidgetInfo.NUMBER_NAME))
		{
			if(index == 1)
			{
				comp = new NNumberField(from.getProperties(), false);
			}
			else if(index == 2)
			{
				if(from instanceof NGraph)
					((NGraph)from).stopTimerThread();
				Dimension dimens = (Dimension) Property.getPropertyFromType(Property.Type.SIZE, from.getProperties()).getData();
				if(dimens.getHeight() < 300)
					dimens.height = 300;
				if(dimens.width < 300)
					dimens.width = 300;
				Property.getPropertyFromType(Property.Type.SIZE, from.getProperties()).setData(dimens);
				comp = new NGraph(from.getProperties(), false);
			}
		}

		if(comp == null)
			throw new UnsupportedOperationException("Error: Converting: " + from.getClass() + " to type " + index + " is not supported");

		if(from.getValue() != null)
			comp.setValue(from.getValue());

		comp.setMovable(from.isMovable());
		return comp;
	}
}
