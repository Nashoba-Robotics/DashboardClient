package edu.nr.util;

import edu.nr.Components.MovableComponent;
import edu.nr.Main;
import edu.nr.properties.PropertiesManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 1/19/14
 *         Time: 11:45 PM
 */
public class SaveManager
{
    JFileChooser saveChooser, openChooser;
    private ArrayList<MovableComponent> components;
    FileNameExtensionFilter xmlfilter;

    public SaveManager(ArrayList<MovableComponent> components)
    {
        this.components = components;

        saveChooser = new JFileChooser();
        saveChooser.setDialogTitle("Choose a save file");

        //Create the openDialog for opening files, and set the filter to only look at xml files
        openChooser = new JFileChooser();
        xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
        openChooser.setFileFilter(xmlfilter);
        openChooser.setDialogTitle("Select a file to open");
    }

    public void showSaveDialog(boolean exitAfterSave)
    {
        int returnValue = saveChooser.showSaveDialog(Main.mainVar);
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            String finalSavePath = saveChooser.getSelectedFile().getPath();
            if(!finalSavePath.endsWith(".xml"))
                finalSavePath += ".xml";
            PropertiesManager.writeAllPropertiesToFile(finalSavePath, components);
            SettingsManager.writeSavePath(finalSavePath);
            if(exitAfterSave)
                System.exit(0);
        }
        else
        {
            Printer.println("save aborted");
            if(exitAfterSave)
                System.exit(0);
        }
    }

    public void showOpenDialog()
    {

        int returnValue = openChooser.showOpenDialog(Main.mainVar);
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            components.clear();
            Main.mainVar.panel.removeAll();
            PropertiesManager.loadElementsFromFile(openChooser.getSelectedFile().getPath(), components);

            for(MovableComponent m : components)
            {
                Main.mainVar.panel.add(m);
            }
            Main.mainVar.panel.repaint();
            Main.mainVar.panel.revalidate();
        }
        else
        {
            Printer.println("Open aborted");
        }
    }
}
