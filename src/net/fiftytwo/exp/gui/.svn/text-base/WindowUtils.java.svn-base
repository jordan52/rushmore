/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.gui;

import java.awt.*;

/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WindowUtils
{
	/** Positions the specified frame in the center of the screen. */
	public static void center(Window w)
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension window = w.getSize();
		if (window.width == 0)
			return;
		int left = screen.width / 2 - window.width / 2;
		int top = (screen.height - window.height) / 4;
		if (top < 0)
			top = 0;
		w.setLocation(left, top);
	}

}
