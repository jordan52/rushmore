/*
 * Created on Feb 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.gui.dsr;
/*
 * @(#) ListSelectEvent.java 1.0 99/21/1 
 * Copyright (c) 1999 Lawrence Rodrigues
 */
import java.io.*;
import java.awt.*;
/** ListSelectEvent Class
  * @version 1.0  21 Jan 1999
  * @author Lawrence Rodrigues
  * This is an event-state class for ListSelect event.
  **/
public class ListSelectEvent
	extends java.awt.event.ActionEvent
	implements Serializable
{
	File path;
	String[] fileList;
	public ListSelectEvent(
		Object obj,
		String command,
		File dir,
		String[] flist)
	{
		super(obj, (int) AWTEvent.ACTION_EVENT_MASK, command);
		this.path = dir;
		this.fileList = flist;
	}
	public String[] getFileList()
	{
		return fileList;
	}
	public File getPath()
	{
		return path;
	}
}