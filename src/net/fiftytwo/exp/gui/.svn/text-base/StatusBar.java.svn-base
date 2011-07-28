/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.gui;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.MenuElement;
import javax.swing.border.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StatusBar extends JPanel
{
	private static StatusBar _instance;
	private JLabel label;
	private MouseHandler handler;

	public static synchronized StatusBar getInstance()
	{
		if (_instance == null)
		{
			_instance = new StatusBar();
		}
		return _instance;
	}

	protected StatusBar()
	{
		super();
		label = new JLabel("                                                                                        ");
		//preferredSize = new Dimension(getWidth(jStatusBarLabel.getText()), 1.2 * getFontHeight());
		
		init();
	}
	protected void init()
	{
		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.CENTER);
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setStatus(" ");
	}
	public synchronized void setStatus(String status)
	{
		if (status != null)
			label.setText(status);
	}
	public synchronized String getStatus(String status)
	{
		return label.getText();
	}
		/**
		 * The rest of this code was borrowed from a sun example on actions
		 * http://www.javadesktop.org/articles/actions/index.html
		 */
		
		/**
		 * Custom mouse handler which will listen to mouse
		 * events on action based components and send the
		 * value of the LONG_DESCRIPTION to the status bar.
		 */ 
		private class MouseHandler extends MouseAdapter {
	
			public void mouseExited(MouseEvent evt) {
				setStatus(" ");
			}
	
			/**
			 * Takes the LONG_DESCRIPTION of the Action based components
			 * and sends them to the Status bar
			 */
			public void mouseEntered(MouseEvent evt) {
			if (evt.getSource() instanceof AbstractButton)  {
				AbstractButton button = (AbstractButton)evt.getSource();
				Action action = button.getAction();
				if (action != null)  {
				String message = (String)action.getValue(Action.LONG_DESCRIPTION);
				setStatus(message);
				}
			}
			}
		}
	
		/**
		 * Helper method to recursively register all MenuElements with 
		 * a mouse listener.
		 */
		public void registerListener(MenuElement[] elements) {
			if (handler == null) {
				handler = new MouseHandler();
			}
	
			for (int i = 0; i < elements.length; i++) {
			if (elements[i] instanceof JMenuItem) {
				((JMenuItem)elements[i]).addMouseListener(handler);
			}
			registerListener(elements[i].getSubElements());
			}
		}
	
		/**
		 * Helper method to register all components with a mouse listener.
		 */
		public void registerListener(Component[] components) {
			if (handler == null) {
				handler = new MouseHandler();
			}
	
			for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof AbstractButton) {
				((AbstractButton)components[i]).addMouseListener(handler);
			}
			}
		}
}