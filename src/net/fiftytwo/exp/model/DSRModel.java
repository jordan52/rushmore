/*
 * Created on Feb 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.model;

import net.fiftytwo.exp.event.FrameUpdateEvent;
import net.fiftytwo.exp.model.dsr.DSRImageSet;

import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.Iterator;



import org.apache.log4j.Category;

/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DSRModel extends CineModel
{
	private static Category cat = Category.getInstance("DSRModel");
	
	protected DSRImageSet dsrImageSet;
	protected int curSlice;
	protected boolean loaded;
	
	
	public DSRModel()
	{
		super();
		loaded = false;
		
		init();
	}
	protected void init()
	{
		super.init();
		curSlice = 0;
		/* TODO: make a better notLoadedImage! */
	}
	public void load(DSRImageSet set)
	{
		this.unload();
		this.dsrImageSet = set;
		this.numFrames = dsrImageSet.getNumFrames();
		this.setFrameDelay(dsrImageSet.getFrameDuration(0,0));
		this.setCurSlice(0); //should be this.setCurSlice(dsrImageSet.getFirstSlice());		
		
		this.curFrame = 0;
		this.firstFrame = 0;
		this.lastFrame = this.numFrames - 1;
		
		loaded = true;
		
		this.setChanged();
		this.notifyObservers(new PropertyChangeEvent(this, "loaded", new Boolean(false), new Boolean(loaded)));
		
		this.fireFrameUpdateEvent( new FrameUpdateEvent(this, curFrame, frames[curFrame]) );
	}
	
	public void unload()
	{
		if(loaded)
		{
			dsrImageSet = null;
			
			this.init(); //reinitialize the model
			
			System.gc();
			
			loaded = false;
			this.setChanged();
			this.notifyObservers(new PropertyChangeEvent(this, "loaded", new Boolean(true), new Boolean(false)));
			this.fireFrameUpdateEvent( new FrameUpdateEvent(this, -1, this.notLoadedImage) );
		}
	}
	
	public void setCurSlice(int slice)
	{
		Collection imageCol = dsrImageSet.getImagesAtSlice(slice);
		if(imageCol != null)
		{
			Iterator imageIter = imageCol.iterator();
			
			this.frames = new RenderedImage[imageCol.size()];
			for(int i = 0; i < this.frames.length; i++)
			{
				if(imageIter.hasNext())
				{
					this.frames[i] = (RenderedImage) imageIter.next();
				} else {
					cat.error("setCurSlice: unable to convert image set to RenderedImages");
				}
			}
			this.curSlice = slice;
			this.setChanged();
			this.notifyObservers(new PropertyChangeEvent(this, "curSlice", new Integer(curSlice), new Integer(slice)));	
		} else {
			cat.debug("NULL IMAGE SET FOUND at slice " + slice); 
		}
	}
}
