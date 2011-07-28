/*
 * Created on Feb 3, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.appProps;

import java.util.Properties;

/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AppProps extends EnhancedProps
{
	private static AppProps _instance;
	private static final String FILENAME = "appProps.properties";

	public static synchronized AppProps getInstance()
	{
		if (_instance == null)
		{
			_instance = new AppProps();
			_instance.loadProps(FILENAME);  //if this fails, it's OK, we'll get a log message and we can just continue
		}
		return _instance;
	}
	
	protected AppProps()
	{
		super();
	}

	protected AppProps(Properties defaults)
	{
		super(defaults);
	}
	public void save()
	{
		this.saveProps(FILENAME);
	}
}
