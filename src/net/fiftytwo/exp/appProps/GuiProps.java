/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.appProps;

import java.util.Properties;
import java.awt.Rectangle;
import java.awt.Dimension;

import org.apache.log4j.Category;

/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GuiProps extends EnhancedProps
{
	private static GuiProps _instance;
	private static final String FILENAME = "guiProps.properties";
	private static Category cat = Category.getInstance("GuiProps");

	public static synchronized GuiProps getInstance()
	{
		if (_instance == null)
		{
			_instance = new GuiProps();
			_instance.loadProps(FILENAME);  //if this fails, it's OK, we'll get a log message and we can just continue
		}
		return _instance;
	}
	
	protected GuiProps()
	{
		super();
	}

	protected GuiProps(Properties defaults)
	{
		super(defaults);
	}
	
	public void save()
	{
		this.saveProps(FILENAME);
	}
		
	public Rectangle getProperty(String propKey, Rectangle defaultVal)
	{
		try
		{
			int x = this.getProperty(propKey + ".x", new Double(defaultVal.getX()).intValue());
			int y = this.getProperty(propKey + ".y", new Double(defaultVal.getY()).intValue());
			int width = this.getProperty(propKey + ".width", new Double(defaultVal.getWidth()).intValue());
			int height = this.getProperty(propKey + ".height", new Double(defaultVal.getHeight()).intValue());
			return new Rectangle(x, y, width, height);
		} catch (Exception e)
		{
			cat.error("Unable to get the bounds for " + propKey, e);
			return (Rectangle) defaultVal.clone();
		}

	}
	
	public void setProperty(String propKey, Rectangle bounds)
	{
		try
		{
			if (bounds != null)
			{
			
				this.setProperty(propKey + ".x", Integer.toString(new Double(bounds.getX()).intValue()));
				this.setProperty(propKey + ".y", Integer.toString(new Double(bounds.getY()).intValue()));
				this.setProperty(propKey + ".width", Integer.toString(new Double(bounds.getWidth()).intValue()));
				this.setProperty(propKey + ".height", Integer.toString(new Double(bounds.getHeight()).intValue()));
			}

		} catch (Exception e) {
			cat.error("Unable to set the bounds for " + propKey, e);
		}
	}
	public Dimension getProperty(String propKey, Dimension defaultVal)
	{
		try
		{

			int width = this.getProperty(propKey + ".width", new Double(defaultVal.getWidth()).intValue());
			int height = this.getProperty(propKey + ".height", new Double(defaultVal.getHeight()).intValue());
			return new Dimension(width, height);
		} catch (Exception e)
		{
			cat.error("Unable to get the bounds for " + propKey, e);
			return (Dimension) defaultVal.clone();
		}		
	}
	public void setProperty(String propKey,	Dimension dim)
	{
		try
		{
			if (dim != null)
			{
				this.setProperty(propKey + ".width", Integer.toString(new Double(dim.getWidth()).intValue()));
				this.setProperty(propKey + ".height", Integer.toString(new Double(dim.getHeight()).intValue()));
			}
		} catch (Exception e) {
			cat.error("Unable to set the Dimensions for " + propKey, e);
		}
	}
	
}
