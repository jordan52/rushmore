/*
 * DSRImageSet.java
 *
 * Created on September 8, 2002, 1:04 PM
 */
package net.fiftytwo.exp.model.dsr;
/**
 *
 * @author  jordan
 */
import net.fiftytwo.exp.model.ImageSet;
import java.util.Vector;
import java.util.TreeSet;
import java.util.Collection;
import java.util.Comparator;
import java.io.File;
import javax.media.jai.PlanarImage;
public class DSRImageSet extends ImageSet
{
	private Vector vFileNames;
	private String strPath;
	private TreeSet setDSRLoops;
	//properties used to test if an image belongs in the set.
	private float fXResolution;
	private float fYResolution;
	/** Creates a new instance of DSRImageSet */
	public DSRImageSet()
	{
		vFileNames = new Vector();
		setDSRLoops = new TreeSet(new Comparator()
		{
				//order the studies by their angle.  Do a mod 360 here if you need it!
	public int compare(Object obj, Object obj1)
			{
				int iAngleA = ((DSRLoop) obj).getDSRInfo().getAngle();
				int iAngleB = ((DSRLoop) obj1).getDSRInfo().getAngle();
				if (iAngleA < iAngleB)
				{
					return -1;
				} else if (iAngleA > iAngleB)
				{
					return 1;
				} else
				{
					return 0;
				}
			}
		});
	}
	/*
	 *  Returns true if all files were nicely added to the set.
	 */
	public boolean load(String path, Collection cFilenames)
	{
		boolean bSuccess = true;
		strPath = path;
		//set the vector of filenames
		vFileNames.addAll(cFilenames);
		//brute force, just load up the vectors with ALL images. Rely on DSRLoop to 
		//defer loading images into memory until needed.
		for (int i = 0; i < vFileNames.size(); i++)
		{
			DSRLoop loop =
				new DSRLoop(
					strPath
						+ File.separator
						+ (String) (vFileNames.elementAt(i)));
			/* 
			 * check to see it passes tests necessary to add it to the set
			 * If it doesn't pass, don't add it.
			     */
			if (testLoop(loop))
			{
				try
				{
					if (setDSRLoops.add(loop))
					{
						iNumSlices++;
					} else
					{
						bSuccess = false;
						System.err.println(
							"DSRImageSet.load(Collection c): Didn't add "
								+ (String) vFileNames.elementAt(i)
								+ " to the ImageSet because it already exists.  Angle was "
								+ loop.getDSRInfo().getAngle());
					}
				} catch (Exception e)
				{
					bSuccess = false;
					System.err.println(
						"DSRImageSet.load(Collection c): " + e.getMessage());
					e.printStackTrace();
				}
			} else
			{
				bSuccess = false;
				System.err.println(
					"DSRImageSet.load(Collection c): Didn't add "
						+ (String) vFileNames.elementAt(i)
						+ " to the ImageSet. angle was "
						+ loop.getDSRInfo().getAngle());
			}
		}
		return bSuccess;
	}
	protected boolean testLoop(DSRLoop loop)
	{
		boolean bPassed = true;
		//if it's the first one in the loop, use load up the ImageSet's properties.
		if (setDSRLoops.isEmpty())
		{
			this.iWidth = loop.getDSRInfo().getImageWidth();
			this.iHeight = loop.getDSRInfo().getImageHeight();
			this.iNumFrames = loop.getDSRInfo().getFramesInFile();
			this.fXResolution = loop.getDSRInfo().getXResolution();
			this.fYResolution = loop.getDSRInfo().getYResolution();
		} else
		{
			//check to see if this loop matches the properties necessary to belong to this set
			if (this.iWidth != loop.getDSRInfo().getImageWidth())
				bPassed = false;
			if (this.iHeight != loop.getDSRInfo().getImageHeight())
				bPassed = false;
			if (this.iNumFrames != loop.getDSRInfo().getFramesInFile())
				bPassed = false;
			if (this.fXResolution != loop.getDSRInfo().getXResolution())
				bPassed = false;
			if (this.fYResolution != loop.getDSRInfo().getYResolution())
				bPassed = false;
		}
		return bPassed;
	}
	public int getFrameDuration(int slice, int frame)
		throws IndexOutOfBoundsException
	{
		return 20;
	}
	public void setFrameDuration(int duration, int slice, int frame)
		throws IndexOutOfBoundsException
	{}
	public PlanarImage getImage(int slice, int frame)
		throws IndexOutOfBoundsException
	{
		if (slice > iNumSlices || frame > iNumFrames)
			throw new IndexOutOfBoundsException(
				"DSRImageSet.getImage(slice "
					+ slice
					+ ", frame "
					+ frame
					+ ") out of bounds.");
		try
		{
			int size = setDSRLoops.size();
			Object[] loops = setDSRLoops.toArray();
			DSRLoop loop = (DSRLoop) (loops[slice]);
			return loop.getImage(frame);
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"DSRImageSet.getImage(slice "
					+ slice
					+ ", frame "
					+ frame
					+ ") out of bounds.");
		}
	}
	public void setImage(PlanarImage image, int slice, int frame)
		throws IndexOutOfBoundsException
	{
		if (slice > iNumSlices || frame > iNumFrames)
			throw new IndexOutOfBoundsException(
				"DSRImageSet.gstImage(slice "
					+ slice
					+ ", frame "
					+ frame
					+ ") out of bounds.");
		try
		{
			DSRLoop loop = (DSRLoop) (setDSRLoops.toArray()[slice]);
			loop.setImage(image, frame);
			bIsModified = true;
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"DSRImageSet.setImage(slice "
					+ slice
					+ ", frame "
					+ frame
					+ ") out of bounds.");
		}
	}
	public Collection getImagesAtSlice(int slice)
		throws IndexOutOfBoundsException
	{
		if (slice > iNumSlices)
			throw new IndexOutOfBoundsException(
				"DSRImageSet.getImagesAtSlice(slice "
					+ slice
					+ ") out of bounds.");
		try
		{
			DSRLoop loop = (DSRLoop) (setDSRLoops.toArray()[slice]);
			return loop.getImages();
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"DSRImageSet.getImagesAtSlice(slice "
					+ slice
					+ ") out of bounds.");
		}
	}
	public Collection getDurationsAtSlice(int slice)
	{
		Vector durations = new Vector(iNumFrames);
		for (int i = 0; i < durations.size(); i++)
			durations.add(new Integer(20));
		return durations;
	}
	public static void main(String[] args)
	{
		DSRImageSet imageSet = new DSRImageSet();
		Vector fileList = new Vector();
		fileList.add("GSM10900.TIF");
		fileList.add("GSM10901.TIF");
		fileList.add("GSM10902.TIF");
		fileList.add("GSM10903.TIF");
		fileList.add("GSM10904.TIF");
		fileList.add("GSM10905.TIF");
		fileList.add("GSM10906.TIF");
		fileList.add("GSM10907.TIF");
		fileList.add("GSM10908.TIF");
		fileList.add("GSM10909.TIF");
		fileList.add("GSM1090A.TIF");
		fileList.add("GSM1090B.TIF");
		fileList.add("GSM1090C.TIF");
		fileList.add("GSM1090D.TIF");
		fileList.add("GSM1090E.TIF");
		fileList.add("GSM1090F.TIF");
		fileList.add("GSM1090G.TIF");
		fileList.add("GSM1090H.TIF");
		fileList.add("GSM1090I.TIF");
		fileList.add("GSM1090J.TIF");
		fileList.add("GSM1090K.TIF");
		fileList.add("GSM1090L.TIF");
		fileList.add("GSM1090M.TIF");
		fileList.add("GSM1090N.TIF");
		fileList.add("GSM1090O.TIF");
		fileList.add("GSM1090P.TIF");
		fileList.add("GSM1090Q.TIF");
		fileList.add("GSM1090R.TIF");
		fileList.add("GSM1090S.TIF");
		fileList.add("GSM1090T.TIF");
		fileList.add("GSM1090U.TIF");
		imageSet.load("c:\\hpfiles\\9709\\GAMA01", fileList);
	}
}
