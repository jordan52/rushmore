/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.options;

import org.apache.log4j.Category;

/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Options
{
	private static Options _instance;
	private static Category cat = Category.getInstance("Options");
	
	public static synchronized Options getInstance()
	{
		if (_instance == null)
		{
			_instance = new Options();
		}
		return _instance;
	}
	
	protected Options ()
	{
		 
	}
	
	public boolean parseArgs(String[] args)
	{
		/* TODO:  Implement me! */
		cat.warn("parseArgs is not implemented yet!");
		
		return true;
	}

}
