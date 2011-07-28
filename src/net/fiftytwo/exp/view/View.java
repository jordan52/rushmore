/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.view;

import java.util.Observer;
import java.util.Observable;
import net.fiftytwo.exp.model.*;
import javax.swing.*;


/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class View extends JPanel implements Observer
{
	
	public View()
	{
		super();
	}
	public View(Model model)
	{
		this();
		model.addObserver(this);	
	}
	public abstract void update(Observable o, Object arg);

}
