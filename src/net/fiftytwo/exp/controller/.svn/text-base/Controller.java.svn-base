/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.controller;


import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import net.fiftytwo.exp.gui.AppFrame;
import net.fiftytwo.exp.model.Model;

import org.apache.log4j.Category;

/**
 *
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class Controller implements WindowListener, Observer
{
	private static Category cat = Category.getInstance("Controller");

	public abstract void addMainFrame(AppFrame mainFrame);
	public abstract void addModel(Model model);

	public void windowActivated(WindowEvent e)
	{
		cat.warn("unImplemented windowActivated event received by abstract Controller class " + e.toString());
	}
	public void windowClosed(WindowEvent e)
	{
		cat.warn("unImplemented windowClosed event received by abstract Controller class " + e.toString());
	}
	public void windowClosing(WindowEvent e)
	{
		cat.warn("unImplemented windowClosing event received by abstract Controller class " + e.toString());
	}
	public void windowDeactivated(WindowEvent e)
	{
		cat.warn("unImplemented windowDeactivated event received by abstract Controller class " + e.toString());
	}
	public void windowDeiconified(WindowEvent e)
	{
		cat.warn("unImplemented windowDeiconified event received by abstract Controller class " + e.toString());
	}
	public void windowIconified(WindowEvent e)
	{
		cat.warn("unImplemented windowIconified event received by abstract Controller class " + e.toString());
	}
	public void windowOpened(WindowEvent e)
	{
		cat.warn("unImplemented windowOpened event received by abstract Controller class " + e.toString());
	}
    public void update(Observable o, Object arg)
    {
        cat.warn("unImplemented update received by abstract Controller class by " + o.getClass().getName());
    }

}
