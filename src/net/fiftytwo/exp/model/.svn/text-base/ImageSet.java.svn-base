/*
 * ImageSet.java
 *
 * Created on September 8, 2002, 11:40 AM
 */
package net.fiftytwo.exp.model;
/**
 *
 * @author  jordan
 */
import java.util.Vector;
import java.util.Collection;
import java.util.Collections;
import javax.media.jai.PlanarImage;
import java.io.Serializable;
/* an image set is a collection of PlanarImages ordered by slice and frame.
 * each image is scaled to be a power of 2 for easy texture creation.
 * actual size of the images are held in iWidth and iHeight
 */
public abstract class ImageSet implements Serializable
{
	protected boolean bIsModified;
	protected int iWidth;
	protected int iHeight;
	protected int iNumSlices;
	protected int iNumFrames;
	/*
	 *  A Vector of Vectors containing the int durations of each frame
	 *  in msecs.  The vectors go like this [slice][frame]
	 */
	protected Vector vDurations;
	/*
	 * A Vector of Vectors containing the planarImages of each frame
	 * The vectors go like ths [slice][frame]
	 */
	protected Vector vImages;
	/** Creates a new instance of ImageSet */
	public ImageSet()
	{
		bIsModified = false;
		//can't really do anything here.
	}
	public ImageSet(int width, int height, int numSlices, int numFrames)
	{
		//set the properties
		bIsModified = false; //this is set if an image is set.
		iWidth = width;
		iHeight = height;
		iNumSlices = numSlices;
		iNumFrames = numFrames;
		//create the vectors of vectors
		vDurations = new Vector(numSlices);
		for (int i = 0; i < numSlices; i++)
			vDurations = new Vector(numFrames);
		vImages = new Vector(numSlices);
		for (int i = 0; i < numSlices; i++)
			vImages = new Vector(numFrames);
	}
	public abstract boolean load(String path, Collection cFilenames);
	/* 
	 *  this method will blast out the old image collection and create a new one
	 *  only call this sucker after you set numSlices and numFrames
	 */
	protected void createImagesCollection()
	{
		vImages = new Vector(iNumSlices);
		for (int i = 0; i < iNumSlices; i++)
			vImages = new Vector(iNumFrames);
	}
	/* 
	 *  this method will blast out the old durations collection and create a new one
	 *  only call this sucker after you set numSlices and numFrames
	 */
	protected void createDurationsCollection()
	{
		vDurations = new Vector(iNumSlices);
		for (int i = 0; i < iNumSlices; i++)
			vDurations = new Vector(iNumFrames);
	}
	public boolean isModified()
	{
		return bIsModified;
	}
	public int getWidth()
	{
		return iWidth;
	}
	public int getHeight()
	{
		return iHeight;
	}
	public int getNumSlices()
	{
		return iNumSlices;
	}
	public int getNumFrames()
	{
		return iNumFrames;
	}
	public int getFrameDuration(int slice, int frame)
		throws IndexOutOfBoundsException
	{
		try
		{
			return (
				(Integer) ((Vector) vDurations.elementAt(slice)).elementAt(
					frame))
				.intValue();
			//that line up there is the same as: 
			// Vector bill = (Vector)vDurations.elementAt(slice);
			// duration =  ((Integer)bill.elementAt(slice)).intValue();
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"ImageSet.getFrameDuration(slice "
					+ slice
					+ ", frame "
					+ frame
					+ ") out of bounds");
		}
	}
	public void setFrameDuration(int duration, int slice, int frame)
		throws IndexOutOfBoundsException
	{
		try
		{
			((Vector) vDurations.elementAt(slice)).setElementAt(
				new Integer(duration),
				frame);
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"ImageSet.setFrameDuration(slice "
					+ slice
					+ ", frame "
					+ frame
					+ ", duration "
					+ duration
					+ ") out of bounds");
		}
	}
	public PlanarImage getImage(int slice, int frame)
		throws IndexOutOfBoundsException
	{
		try
		{
			return (
				(PlanarImage) ((Vector) vImages.elementAt(slice)).elementAt(
					frame));
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"ImageSet.getImage(slice "
					+ slice
					+ ", frame "
					+ frame
					+ ") out of bounds");
		}
	}
	public void setImage(PlanarImage image, int slice, int frame)
		throws IndexOutOfBoundsException
	{
		try
		{
			((Vector) vImages.elementAt(slice)).setElementAt(image, frame);
			bIsModified = true;
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"ImageSet.getImage(slice "
					+ slice
					+ ", frame "
					+ frame
					+ ") out of bounds");
		}
	}
	public Collection getImagesAtSlice(int slice)
		throws IndexOutOfBoundsException
	{
		try
		{
			return Collections.unmodifiableCollection(
				(Collection) vImages.elementAt(slice));
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"ImageSet.GetImagesAtSlice(slice " + slice + ") out of bounds");
		}
	}
	public Collection getDurationsAtSlice(int slice)
	{
		try
		{
			return Collections.unmodifiableCollection(
				(Collection) vDurations.elementAt(slice));
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"ImageSet.GetDurationsAtSlice(slice "
					+ slice
					+ ") out of bounds");
		}
	}
}