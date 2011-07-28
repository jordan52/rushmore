/*
 * Created on Feb 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.event;

import java.awt.image.RenderedImage;
import java.io.Serializable;
import java.util.EventObject;

public class FrameUpdateEvent extends EventObject implements Serializable
{
	int frameNumber;
	RenderedImage frame;
		
	public FrameUpdateEvent(Object source, int frameNumber, RenderedImage frame)
	{
		super(source);
		this.frameNumber = frameNumber;
		this.frame = frame;
	}
	public int getFrameNumber()
	{
		return frameNumber;
	}
	public RenderedImage getFrame()
	{
		return frame;
	}
}
