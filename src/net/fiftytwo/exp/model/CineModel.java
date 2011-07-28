/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.model;
import net.fiftytwo.exp.event.FrameUpdateEvent;
import net.fiftytwo.exp.event.FrameUpdateEventListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.Timer;
import org.apache.log4j.Category;
/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CineModel extends Model
{
	private static Category cat = Category.getInstance("CineModel");
	
	//vars to allow "swing play" if goingUp is true, we're incrementing frames
	protected boolean swingPlay;
	protected boolean goingUp;
	protected boolean playing;
	protected int numFrames;
	protected int curFrame;
	protected int firstFrame;
	protected int lastFrame;
	protected int frameDelay;
	
	protected RenderedImage[] frames;
	protected javax.swing.Timer timer;
	protected Vector vAnimationEventListeners;
	
	protected BufferedImage notLoadedImage;
	
	public CineModel()
	{
		vAnimationEventListeners = new Vector();
		this.init();
	}
	
	//init is used in the beginning and to re-initialize anytime.
	protected void init()
	{
		frameDelay = 10;
		swingPlay = false;
		goingUp = true;
		playing = false;
		numFrames = 0;
		curFrame = -1;
		firstFrame = 0;
		lastFrame = 0;
		
		//stop the timer if one already exists (in the case of re-initialization)
		if(timer != null)
		{
			timer.stop();
		}

		notLoadedImage = new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
		
		timer = new Timer(frameDelay, new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				nextFrame();
				if (playing == false)
				{
					timer.stop();
				}
			}
		});

	}
	public void nextFrame()
	{
		if (curFrame == -1 )
		{
			return;
		}
		
		if (goingUp == true)
			curFrame++;
		else
			curFrame--;
		if (curFrame > lastFrame)
		{
			if (swingPlay)
			{
				goingUp = false;
				curFrame = lastFrame - 1;
			} else
			{
				curFrame = firstFrame;
			}
		}
		if (curFrame < 0)
		{
			if (swingPlay)
			{
				goingUp = !goingUp;
				curFrame = firstFrame + 1;
			} else
			{
				curFrame = lastFrame;
			}
		}

		fireFrameUpdateEvent(	new FrameUpdateEvent(this, curFrame, frames[curFrame]) );
	}
	public void play()
	{
		if (playing == false)
		{
			playing = true;
			this.setChanged();
			this.notifyObservers(new PropertyChangeEvent(this, "playing", new Boolean(false), new Boolean(true)));
			timer.start();
			cat.debug("Play timer started");
		}
	}
	public void pause()
	{
		if (playing == true)
		{
			playing = false;
			this.setChanged();
			this.notifyObservers(new PropertyChangeEvent(this, "playing", new Boolean(true), new Boolean(false)));
			timer.stop();
			cat.debug("Pause: timer stopped");
		}
	}
	//this will save anything we'll need next time.
	public void shutDown()
	{
		cat.info("shutDown() is not implemented!");
		return;
	}
	public void addFrameUpdateEventListener(FrameUpdateEventListener listener)
	{
		vAnimationEventListeners.addElement(listener);
	}
	public void removeFrameUpdateEventListener(FrameUpdateEventListener listener)
	{
		if (!(vAnimationEventListeners.isEmpty()))
			vAnimationEventListeners.removeElement(listener);
	}
	protected void fireFrameUpdateEvent(FrameUpdateEvent event)
	{
		cat.debug("fireAnimationEvent for frame: " + event.getFrameNumber());
		for(Enumeration e = vAnimationEventListeners.elements(); e.hasMoreElements();)
		{
			FrameUpdateEventListener listener = (FrameUpdateEventListener) (e.nextElement());
			listener.frameUpdate(event);
		}
	}	
	/**
	 * @return
	 */
	public int getCurFrame()
	{
		return curFrame;
	}
	/**
	 * @return
	 */
	public int getFirstFrame()
	{
		return firstFrame;
	}
	/**
	 * @return
	 */
	public int getFrameDelay()
	{
		return frameDelay;
	}
	/**
	 * @return
	 */
	public boolean isGoingUp()
	{
		return goingUp;
	}
	/**
	 * @return
	 */
	public int getLastFrame()
	{
		return lastFrame;
	}
	/**
	 * @return
	 */
	public int getNumFrames()
	{
		return numFrames;
	}
	/**
	 * @return
	 */
	public boolean isPlaying()
	{
		return playing;
	}
	/*
	 * instead of playing frames 0-x->0->x,
	 * swing from 0->x->0->x->0
	 */
	public void setSwingPlay(boolean val)
	{
		if(swingPlay != val)
		{
		
			this.setChanged();
			this.notifyObservers(new PropertyChangeEvent(this, "swingPlay", new Boolean(swingPlay), new Boolean(val)));
			swingPlay = val;
		}
	}
	/**
	 * @return
	 */
	public boolean isSwingPlay()
	{
		return swingPlay;
	}
	/**
	 * @param i
	 */
	public void setFirstFrame(int i)
	{
		if(firstFrame != i)
		{
			this.setChanged();
			this.notifyObservers(new PropertyChangeEvent(this, "firstFrame", new Integer(firstFrame), new Integer(i)));	
		
			firstFrame = i;
		}
	}
	/**
	 * @param i
	 */
	public void setFrameDelay(int i)
	{
		if( frameDelay != i)
		{
			this.setChanged();
			this.notifyObservers(new PropertyChangeEvent(this, "frameDelay", new Integer(frameDelay), new Integer(i)));	
			
			frameDelay = i;
		}
		
	}
	/**
	 * @param b
	 */
	public void setGoingUp(boolean b)
	{
		if(goingUp != b)
		{
		
			this.setChanged();
			this.notifyObservers(new PropertyChangeEvent(this, "goingUp", new Boolean(goingUp), new Boolean(b)));
			goingUp = b;
		}
	}
	/**
	 * @param i
	 */
	public void setLastFrame(int i)
	{
		if( lastFrame != i)
		{
			this.setChanged();
			this.notifyObservers(new PropertyChangeEvent(this, "lastFrame", new Integer(lastFrame), new Integer(i)));	
			
			lastFrame = i;
		}
	}
	/**
	 * @param i
	 */
	public void setNumFrames(int i)
	{
		if( numFrames != i)
		{
			this.setChanged();
			this.notifyObservers(new PropertyChangeEvent(this, "numFrames", new Integer(numFrames), new Integer(i)));	
			
			 numFrames = i;
		}
	}

}
