package edu.nr.Components.mouse_listeners;

import edu.nr.Components.MovableComponent;
import edu.nr.Main;
import edu.nr.util.ComponentChanger;
import edu.nr.util.Printer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author co1in
 *         Date: 2/7/14
 *         Time: 7:53 PM
 */
public class RightClickMenu extends JPopupMenu
{
	private MovableComponent caller;
	public RightClickMenu(MovableComponent caller)
	{
		this.caller = caller;

		JMenuItem propertiesItem = new JMenuItem("Properties");
		propertiesItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Printer.println("Properties not Implemented Yet");
			}
		});
		add(propertiesItem);

		JMenu changeMenu = new JMenu("Change to");
		String[] widgetChoices = caller.getWidgetChoices();
		for(int i = 0; i < widgetChoices.length; i++)
		{
			JMenuItem item = new JMenuItem();
			item.setText(widgetChoices[i]);
			if((i+1) == caller.getWidgetType())
				item.setEnabled(false);
			item.addActionListener(new MyMenuActionListener(i+1));
			changeMenu.add(item);
		}
		add(changeMenu);

		JMenuItem removeItem = new JMenuItem("Remove Item");
		removeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.mainVar.removeWidget(RightClickMenu.this.caller);
			}
		});

		add(removeItem);
	}

	private class MyMenuActionListener implements ActionListener
	{
		private int index;
		public MyMenuActionListener(int index)
		{
			this.index = index;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			MovableComponent newComponent = ComponentChanger.changeComponent(index, caller);
			Main.mainVar.changeComponent(caller, newComponent);
		}
	}
}
