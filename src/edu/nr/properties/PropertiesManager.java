package edu.nr.properties;

import edu.nr.Components.*;
import edu.nr.Main;
import edu.nr.util.Printer;
import edu.nr.util.SettingsManager;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author co1in
 *         Date: 11/14/13
 *         Time: 10:34 PM
 */

import java.awt.Point;

public class PropertiesManager
{
    public static void loadElementsFromFile(String path, ArrayList<MovableComponent> components, Main main)
    {
        try
        {
            File xmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            Printer.println("Reading from: " + path);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            NodeList widgetList = root.getChildNodes();
            for(int i = 0; i < widgetList.getLength(); i++)
            {
                ArrayList<Property> properties = new ArrayList<Property>();
                Node node = widgetList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    String widgetClassName = element.getTagName();

                    properties.add(new Property(Property.Type.NAME, element.getAttribute(Property.Type.NAME.name())));

                    //My apologies for the intensively large amount of method calls that happen on each line in the following block of code :(
                    //I will try to document these lines more thoroughly to make up for it :D

                    //This line gets the location element from our widget, gets the x and y values, and plugs them into a new Point object that we will add to our properties list
                    Point locationPoint = new Point(Integer.parseInt(((Element)element.getElementsByTagName(Property.Type.LOCATION.name()).item(0)).getAttribute("x")), Integer.parseInt(((Element)element.getElementsByTagName(Property.Type.LOCATION.name()).item(0)).getAttribute("y")));
                    properties.add(new Property(Property.Type.LOCATION, locationPoint));

                    //get the width and height attributes from the element labeled Property.Type.SIZE.name() and put those values into a new dimension object for adding to a new property
                    Dimension dimensions = new Dimension(Integer.parseInt(((Element)element.getElementsByTagName(Property.Type.SIZE.name()).item(0)).getAttribute("width")), Integer.parseInt(((Element)element.getElementsByTagName(Property.Type.SIZE.name()).item(0)).getAttribute("height")));
                    properties.add(new Property(Property.Type.SIZE, dimensions));

                    //Load the three background colors
					Element backgroundElement = ((Element)element.getElementsByTagName(Property.Type.BACKGROUND.name()).item(0));
					if(backgroundElement != null)
					{
						int red = Integer.parseInt(((Element)element.getElementsByTagName(Property.Type.BACKGROUND.name()).item(0)).getAttribute("red"));
						int backBlue = Integer.parseInt(((Element)element.getElementsByTagName(Property.Type.BACKGROUND.name()).item(0)).getAttribute("blue"));
						int backGreen = Integer.parseInt(((Element)element.getElementsByTagName(Property.Type.BACKGROUND.name()).item(0)).getAttribute("green"));
						properties.add(new Property(Property.Type.BACKGROUND, new Color(red, backGreen, backBlue)));
					}

                    //Load the three foreground colors
					Element foregroundElement = ((Element)element.getElementsByTagName(Property.Type.FOREGROUND.name()).item(0));
					if(foregroundElement != null)
					{
						int red = Integer.parseInt(foregroundElement.getAttribute("red"));
						int backBlue = Integer.parseInt(foregroundElement.getAttribute("blue"));
						int backGreen = Integer.parseInt(foregroundElement.getAttribute("green"));
						properties.add(new Property(Property.Type.FOREGROUND, new Color(red, backGreen, backBlue)));
					}

                    properties.add(new Property(Property.Type.FONT_SIZE, Integer.parseInt(((Element)element.getElementsByTagName(Property.Type.FONT_SIZE.name()).item(0)).getAttribute(Property.Type.SIZE.name()))));

					if(element.getElementsByTagName(Property.Type.GRAPH_AXIS_NAME.name()).item(0) != null)
					{
						properties.add(new Property(Property.Type.GRAPH_AXIS_NAME, ((Element)element.getElementsByTagName(Property.Type.GRAPH_AXIS_NAME.name()).item(0)).getAttribute("name")));
					}
					if(element.getElementsByTagName(Property.Type.GRAPH_REFRESH_RATE.name()).item(0) != null)
					{
						properties.add(new Property(Property.Type.GRAPH_REFRESH_RATE, Integer.parseInt(((Element)element.getElementsByTagName(Property.Type.GRAPH_REFRESH_RATE.name()).item(0)).getAttribute("rate"))));
					}
					if(element.getElementsByTagName(Property.Type.AUTOREFRESH.name()).item(0) != null)
					{
						properties.add(new Property(Property.Type.AUTOREFRESH, Boolean.parseBoolean(((Element) element.getElementsByTagName(Property.Type.AUTOREFRESH.name()).item(0)).getAttribute("value"))));
					}

                    Element typeElement = ((Element)element.getElementsByTagName(Property.Type.WIDGET_TYPE.name()).item(0));
					int type = -1;
                    try
                    {
                        type = Integer.parseInt(typeElement.getAttribute("value"));
                    }
                    catch(NumberFormatException e)
                    {
                        Printer.println("Error parsing Type");
                    }

                    //Figure out which class to add
                    MovableComponent addingClass = null;
                    String name = element.getTagName();
                    if(name.equals(WidgetInfo.BUTTON_NAME))
                    {
                        addingClass = new NButton(components, properties, main, true);
                    }
                    else if (name.equals(WidgetInfo.STRING_NAME))
                    {
                        addingClass =  new NTextField(components, properties, main, true);
                    }
                    else if(name.equals(WidgetInfo.NUMBER_NAME))
                    {
						switch (type)
						{
							case 1:
								addingClass = new NNumberField(components, properties, main, true);
								break;
							case 2:

						}
                    }
                    else if(name.equals(WidgetInfo.BOOLEAN_NAME))
                    {
                        addingClass = new NBooleanField(components, properties, main, true);
                    }
                    else
                    {
                        Printer.println("ERROR: Adding class was null");
                    }

                    addingClass.setMovable(main.isEditable());

                    components.add(addingClass);
                }
            }
            main.repaint();
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            Printer.println("Couldn't locate save file: '" + path + "'");
            SettingsManager.deleteLastSavePath();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean writeAllPropertiesToFile(String path, ArrayList<MovableComponent> components, Main main)
    {
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("dashboard");

            doc.appendChild(rootElement);

            for(MovableComponent movableComponent : components)
            {
                Element widget = doc.createElement(movableComponent.getWidgetName());
                rootElement.appendChild(widget);
                ArrayList<Property> properties = movableComponent.getProperties();

                //Write Name
                Property p = Property.getPropertyFromType(Property.Type.NAME, properties);
				assert p != null;
                Attr attr = doc.createAttribute(Property.Type.NAME.name());
                attr.setValue(String.valueOf(p.getData()));
                widget.setAttributeNode(attr);

                //Add the location
                p = Property.getPropertyFromType(Property.Type.LOCATION, properties);
				if(p != null)
				{
					Element location = doc.createElement(Property.Type.LOCATION.name());
					Attr locX = doc.createAttribute("x");
					locX.setValue(String.valueOf(((Point) p.getData()).x));
					Attr locY = doc.createAttribute("y");
					locY.setValue(String.valueOf(((Point) p.getData()).y));
					location.setAttributeNode(locX);
					location.setAttributeNode(locY);
					widget.appendChild(location);
				}

                //Add the size
                p = Property.getPropertyFromType(Property.Type.SIZE, properties);
                Element size = doc.createElement(Property.Type.SIZE.name());
                Attr width = doc.createAttribute("width");
                width.setValue(String.valueOf(((Dimension)p.getData()).width));
                Attr height = doc.createAttribute("height");
                height.setValue(String.valueOf(((Dimension)p.getData()).height));
                size.setAttributeNode(width);
                size.setAttributeNode(height);
                widget.appendChild(size);

                //Add a background Color
				Color color = null;
                p = Property.getPropertyFromType(Property.Type.BACKGROUND, properties);
				if(p != null)
				{
					Element background = doc.createElement(Property.Type.BACKGROUND.name());
					color = (Color)p.getData();
					background.setAttribute("red", String.valueOf(color.getRed()));
					background.setAttribute("green", String.valueOf(color.getGreen()));
					background.setAttribute("blue", String.valueOf(color.getBlue()));
					widget.appendChild(background);
				}

                //Add the foreground Color
                p = Property.getPropertyFromType(Property.Type.FOREGROUND, properties);
				if(p != null)
				{
					Element foreground = doc.createElement(Property.Type.FOREGROUND.name());
					color = (Color)p.getData();
					foreground.setAttribute("red", String.valueOf(color.getRed()));
					foreground.setAttribute("green", String.valueOf(color.getGreen()));
					foreground.setAttribute("blue", String.valueOf(color.getBlue()));
					widget.appendChild(foreground);
				}

                //Add the type of widget
                Element type = doc.createElement(Property.Type.WIDGET_TYPE.name());
                type.setAttribute("value", String.valueOf(movableComponent.getWidgetType()));
                widget.appendChild(type);

                //Add the Font size
                p = Property.getPropertyFromType(Property.Type.FONT_SIZE, properties);
				if( p != null)
				{
					Element fontSize = doc.createElement(Property.Type.FONT_SIZE.name());
					fontSize.setAttribute(Property.Type.SIZE.name(), String.valueOf(p.getData()));
					widget.appendChild(fontSize);
				}
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            Printer.println("File saved!");


            return true;
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static void loadPropertiesIntoArray(ArrayList<Property> defaultProperties, ArrayList<Property> loadingProperties)
    {
        if(loadingProperties != null)
        {
            for(Property p : loadingProperties)
            {
                for(int i = 0; i < defaultProperties.size(); i++)
                {
                    if(p.getType().equals(defaultProperties.get(i).getType()))
                    {
                        defaultProperties.set(i, p);
                    }
                }
            }
        }
    }
}
