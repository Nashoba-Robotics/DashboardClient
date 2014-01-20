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
    private Main main;

    public SaveManager(ArrayList<MovableComponent> components, Main main)
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
        int returnValue = saveChooser.showSaveDialog(main);
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            String finalSavePath = saveChooser.getSelectedFile().getPath();
            if(!finalSavePath.endsWith(".xml"))
                finalSavePath += ".xml";
            PropertiesManager.writeAllPropertiesToFile(finalSavePath, components, main);
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

        int returnValue = openChooser.showOpenDialog(main);
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            components.clear();
            main.panel.removeAll();
            PropertiesManager.loadElementsFromFile(openChooser.getSelectedFile().getPath(), components, main);

            for(MovableComponent m : components)
            {
                main.panel.add(m);
            }
            main.panel.repaint();
            main.panel.revalidate();
        }
        else
        {
            Printer.println("Open aborted");
        }

    }
}
