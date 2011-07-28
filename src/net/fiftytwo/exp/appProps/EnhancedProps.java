/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.appProps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Category;

/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EnhancedProps extends Properties
{
	private static Category cat = Category.getInstance("EnhancedProps");

	public EnhancedProps()
	{
		super();
	}

	public EnhancedProps(Properties defaults)
	{
		super(defaults);
	}
		
	public boolean loadProps(String propFilename)
	{
		try
		{
			InputStream in =
				ClassLoader.getSystemResourceAsStream(propFilename);
			if(in != null)
			{
				this.load(in);
				in.close();
				return true;
			} else 
			{
				cat.warn("Can't find ExtendedProperties file: " + propFilename);
				return false;
			}
		} catch (IOException e)
		{
			cat.error("Properties file " + propFilename + " was not found.", e);
			return false;
		}
	}
	protected boolean saveProps(String propFilename)
	{
		return saveProps(propFilename, "--NO COMMENT--");
	}
	
	/*
	 * This will attempt to save the properties file where it found it in the classpath.
	 * if it doesn't find the file, it will save it at the root of the classpath. NOT IMPLEMENTED!
	 */
	protected boolean saveProps(String propFilename, String comment)
	{
		try
		{
			cat.warn("HEY! Fix up EnhancedProps.saveProps.. it's lame!");
			URL url = this.getClass().getClassLoader().getResource(propFilename);
			
			if(url == null)
			{
				//we have to create the properties file
				/* TODO:  figure out a way to create the file if it's not found! 
				 * 
				 * use something like System.getProperty("user.home");
				 * 
				 * */
			}
			
			/*
			 * here we have to figure out the path to the properties file... 
			 * so, get the URL and lop off the "file:/" if it exists. if something 
			 * happens, it'll throw an exception, so just catch it and move on.
			 */
			String location = url.toString();

			int begOfFilename = location.indexOf("file:/");

			if (begOfFilename > -1)
			{
				//we have the location from ClassLoader, keep on truckin.
				location = location.substring(location.indexOf(":") + 1);				
			} else {
				//then we didn't find the file, figure out where to put it.
				location = File.separator;
				
				/* TODO: actually make this work! */
				cat.error("EnhancedProperties.saveProperties.  CODE NOT IMPLEMENTED!");
			}		
			FileOutputStream out = new FileOutputStream(location);

			this.store(out, comment);
			out.close();
			return true;
			
		} catch (Exception e)
		{
			cat.error("Unable to save properties file.  Name: " + propFilename, e);
			return false;
		}
	}	

	/* 
	 * this will return the int value of a property.
	 */
	public int getProperty(String key, int defaultVal)
	{
		String sVal;
		int val = -1;

		//get the property
		sVal = this.getProperty(key);
		if( sVal == null )
		{
			//if it didn't exist, add the default
			this.put(key, Integer.toString(defaultVal));
			return defaultVal;
		}
		
		try
		{			
			//if it did exist, try to pull it out.
			val = Integer.parseInt(sVal);
			return val;
		} catch (NumberFormatException e) {
			cat.error("Unable to getProperty <int> for key " + key + ". Returning default. " , e);
		}
		//if you got a numberFormatException, replace that property with the default and continue
		this.put(key, Integer.toString(defaultVal));
		return defaultVal;
	}
	public double getProperty(String key, double defaultVal)
	{
		String sVal;
		double val = -1;

		//get the property
		sVal = this.getProperty(key);
		if( sVal == null )
		{
			//if it didn't exist, add the default
			this.put(key, Double.toString(defaultVal));
			return defaultVal;
		}
		
		try
		{			
			//if it did exist, try to pull it out.
			val = Double.parseDouble(sVal);
			return val;
		} catch (NumberFormatException e) {
			cat.error("Unable to getProperty <double> for key " + key + ". Returning default. " , e);
		}
		//if you got a numberFormatException, replace that property with the default and continue
		this.put(key, Double.toString(defaultVal));
		return defaultVal;
	}
	public boolean getProperty(String key, boolean defaultVal)
	{
		String sVal;
		boolean val = false;

		//get the property
		sVal = this.getProperty(key);
		if( sVal == null )
		{
			//if it didn't exist, add the default
			this.put(key, Boolean.toString(defaultVal));
			return defaultVal;
		}
		
		try
		{			
			//if it did exist, try to pull it out.
			val = Boolean.valueOf(sVal).booleanValue();
			return val;
		} catch (NumberFormatException e) {
			cat.error("Unable to getProperty <boolean> for key " + key + ". Returning default. " , e);
		}
		//if you got a numberFormatException, replace that property with the default and continue
		this.put(key, Boolean.toString(defaultVal));
		return defaultVal;
	}
	public void setProperty(String propKey,	boolean val)
	{
		try
		{
			this.setProperty(propKey, Boolean.toString(val));
		} catch (Exception e) {
			cat.error("Unable to set the boolean for " + propKey, e);
		}
	}
}
