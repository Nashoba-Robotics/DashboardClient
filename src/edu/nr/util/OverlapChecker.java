package edu.nr.util;

import edu.nr.MovableComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author co1in
 * Date: 11/13/13
 * Time: 7:39 PM
 *
 * This class is a utility for the widgets to make sure they do not overlap another widget.
 * They call this class's methods statically when they are being dragged by the mouse
 */
public class OverlapChecker
{
    public static boolean overlapsOtherWidgetX(JComponent caller, JComponent compare, Point newLocation)
    {
        if(caller != compare) //Make sure we aren't comparing this widget to itself
        {
            Point l1 = newLocation;
            Dimension d1 = caller.getSize();

            Point l2 = compare.getLocation();
            Dimension d2 = compare.getSize();

            //Prove no overlap of both rectangle widgets by contradiction. For an explanation, ask co1in
            if(l1.x > (l2.x + d2.width) || (l1.x+d1.width) < l2.x)
            {
                //There is no overlap
                return false;
            }
            else
            {
                //There is overlap
                return true;
            }
        }
        return false;
    }
    public static boolean overlapsOtherWidgetY(JComponent caller, JComponent compare, Point newLocation)
    {
        if(compare != caller) //Make sure we aren't comparing this widget to itself
        {
            Point l1 = newLocation;
            Dimension d1 = caller.getSize();

            Point l2 = compare.getLocation();
            Dimension d2 = compare.getSize();

            //Prove no overlap of both rectangle widgets by contradiction. For an explanation, ask co1in
            if((l1.y + d1.height) < l2.y || l1.y > (l2.y + d2.height))
            {
                //There is no overlap
                return false;
            }
            else
            {
                //There is overlap
                return true;
            }
        }
        return false;
    }

    public static boolean overlapsOtherWidget(JComponent caller, JComponent compare, Point newLocation)
    {
        if(compare != caller) //Make sure we aren't comparing this widget to itself
        {
            Point l1 = newLocation;
            Dimension d1 = caller.getSize();

            Point l2 = compare.getLocation();
            Dimension d2 = compare.getSize();

            //Prove no overlap of both rectangle widgets by contradiction. For an explanation, ask co1in
            if(l1.x > (l2.x + d2.width) || (l1.x+d1.width) < l2.x || (l1.y + d1.height) < l2.y || l1.y > (l2.y + d2.height))
            {
                //There is no overlap
                return false;
            }
            else
            {
                //There is overlap
                return true;
            }
        }
        return false;
    }

    public static void checkForCollision(JComponent caller, ArrayList<MovableComponent> components, Point myNewLocation, Point oldLocation)
    {
        for(int i = 0; i < components.size(); i++)
        {
            JComponent component = (JComponent)components.get(i);
            //Check for overlap and, if they overlap, allow the mouse to still slide in the direction where the button has space
            if(OverlapChecker.overlapsOtherWidget(caller, component, myNewLocation))
            {
                if(OverlapChecker.overlapsOtherWidgetX(caller, component, myNewLocation) && !OverlapChecker.overlapsOtherWidgetX(caller, component, oldLocation))
                {
                    //Overlaps, and it came from a side
                    myNewLocation = new Point(oldLocation.x, myNewLocation.y);
                    i = -1;
                }
                else if(OverlapChecker.overlapsOtherWidgetY(caller, component, myNewLocation) && !OverlapChecker.overlapsOtherWidgetY(caller, component, oldLocation))
                {
                    //Overlaps, and it came from a top or bottom
                    myNewLocation = new Point(myNewLocation.x, oldLocation.y);
                    i = -1;
                }
            }
        }
        caller.setLocation(myNewLocation);
    }

    /*NOT FULLY IMPLEMENTED YET/ BUGGY
    public static boolean cameFromThisWidgetsSide(JComponent caller, JComponent compare, Point newLocation)
    {
        if(caller != compare) //Make sure we aren't comparing this widget to itself
        {
            Point l1 = newLocation;
            Dimension d1 = caller.getSize();

            Point l2 = compare.getLocation();
            Dimension d2 = compare.getSize();

            //Prove no overlap of both rectangle widgets by contradiction. For an explanation, ask co1in
            if(l1.x > (l2.x + d2.width))
            {
                return ((l1.x - (l2.x + d2.width)) < 5);
            }
            else if((l1.x+d1.width) < l2.x)
            {
                return ((l2.x - (l1.x+d1.width)) < 5);
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    public static boolean cameFromThisWidgetsLength(JComponent caller, JComponent compare, Point newLocation)
    {
        if(compare != caller) //Make sure we aren't comparing this widget to itself
        {
            Point l1 = newLocation;
            Dimension d1 = caller.getSize();

            Point l2 = compare.getLocation();
            Dimension d2 = compare.getSize();

            //Prove no overlap of both rectangle widgets by contradiction. For an explanation, ask co1in
            if((l1.y + d1.height) < l2.y )
            {
                return ((l2.y - (l1.y + d1.height)) < 5);
            }
            else if(l1.y > (l2.y + d2.height))
            {
                return ((l1.y - (l2.y + d2.height)) < 5);
            }
            else
            {
                return false;
            }
        }
        return false;
    }*/
}
