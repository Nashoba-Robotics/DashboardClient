package edu.nr;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: colin
 * Date: 11/13/13
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class OverlapChecker
{
    private ArrayList<MovableComponent> components;
    public OverlapChecker(ArrayList<MovableComponent> components)
    {
        this.components = components;
    }
    public boolean overlapsOtherWidgetX(JComponent caller, Point newLocation)
    {
        for(Object c : components)
        {
            if(c != caller) //Make sure we aren't comparing this widget to itself
            {
                Point l1 = newLocation;
                Dimension d1 = caller.getSize();

                JComponent t2 = (JComponent)c;
                Point l2 = t2.getLocation();
                Dimension d2 = t2.getSize();

                //Prove no overlap of both rectangle widgets by contradiction. For an explanation, ask co1in
                if(l1.x > (l2.x + d2.width) || (l1.x+d1.width) < l2.x)
                {
                    //There is no overlap
                }
                else
                {
                    //There is overlap
                    return true;
                }
            }
        }
        return false;
    }
    public boolean overlapsOtherWidgetY(JComponent caller, Point newLocation)
    {
        for(Object c : components)
        {
            if(c != caller) //Make sure we aren't comparing this widget to itself
            {
                Point l1 = newLocation;
                Dimension d1 = caller.getSize();

                JComponent t2 = (JComponent)c;
                Point l2 = t2.getLocation();
                Dimension d2 = t2.getSize();

                //Prove no overlap of both rectangle widgets by contradiction. For an explanation, ask co1in
                if((l1.y + d1.height) < l2.y || l1.y > (l2.y + d2.height))
                {
                    //There is no overlap
                }
                else
                {
                    //There is overlap
                    return true;
                }
            }
        }
        return false;
    }

    public boolean overlapsOtherWidget(JComponent caller, Point newLocation)
    {
        for(Object c : components)
        {
            if(c != caller) //Make sure we aren't comparing this widget to itself
            {
                Point l1 = newLocation;
                Dimension d1 = caller.getSize();

                JComponent t2 = (JComponent)c;
                Point l2 = t2.getLocation();
                Dimension d2 = t2.getSize();

                //Prove no overlap of both rectangle widgets by contradiction. For an explanation, ask co1in
                if(l1.x > (l2.x + d2.width) || (l1.x+d1.width) < l2.x || (l1.y + d1.height) < l2.y || l1.y > (l2.y + d2.height))
                {
                    //There is no overlap
                }
                else
                {
                    //There is overlap
                    return true;
                }
            }
        }
        return false;
    }
}
