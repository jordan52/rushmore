/*
 * Created on Feb 20, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.model.dsr;
/*
 * DSRInfo.java
 *
 * Created on July 21, 2002, 5:17 PM
 */
/**
 *
 * @author  jordan
 */
import org.libtiff.jai.codec.XTIFFDirectory;
import org.libtiff.jai.codec.XTIFFField;
import org.libtiff.jai.codec.XTIFF;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.FileSeekableStream;
import java.lang.Math;
public class DSRInfo
{
	protected XTIFFDirectory dir;
	protected XTIFFDirectory extendedDir;
	private int imageWidth;
	private int imageHeight;
	private String imageDescription;
	private long stripOffset;
	private int samplesPerPixel;
	private long[] xResolution;
	private long[] yResolution;
	private int planarConfig;
	private String software;
	private String dateTime;
	private int applicationID;
	private int framesInFile;
	private int[] frameInfo;
	private int screenFormat;
	private long[] systemMode;
	private int imageMapType;
	private String applicationDescription;
	private int fieldsInFile;
	private int colorMapSettings;
	private long colorBaseline;
	private int dataType;
	private int[] cineTimeline;
	private int[] imageSettings;
	private int[] applicationSpecificData;
	private int[] teeAngle;
	private long extendedTagsOffset;
	private int[] omniAngle;
	/** Creates a new instance of DSRInfo */
	public DSRInfo(SeekableStream stream)
	{
		try
		{
			XTIFFField field;
			dir = XTIFFDirectory.create(stream, 0);
			imageWidth = (int) dir.getFieldAsLong(XTIFF.TIFFTAG_IMAGE_WIDTH);
			imageHeight = (int) dir.getFieldAsLong(XTIFF.TIFFTAG_IMAGE_LENGTH);
			// Get the number of frames in the file
			field = dir.getField(XTIFF.TIFFTAG_FRAMESINFILE);
			if (field == null)
			{
				framesInFile = 1;
			} else
			{
				framesInFile = (int) field.getAsLong(0);
			}
			field = null;
			// Get the X Resoultion
			field = dir.getField(XTIFF.TIFFTAG_X_RESOLUTION);
			if (field == null)
			{
				xResolution = null;
			} else
			{
				xResolution = field.getAsRational(0);
			}
			field = null;
			field = dir.getField(XTIFF.TIFFTAG_Y_RESOLUTION);
			if (field == null)
			{
				yResolution = null;
			} else
			{
				yResolution = field.getAsRational(0);
			}
			field = null;
			field = dir.getField(XTIFF.TIFFTAG_EXTENDEDTAGSOFFSET);
			if (field == null)
			{
				extendedTagsOffset = -1;
			} else
			{
				extendedTagsOffset = field.getAsLong(0);
			}
			field = null;
			if (extendedTagsOffset > 0)
			{
				extendedDir = XTIFFDirectory.create(stream, extendedTagsOffset);
				field = extendedDir.getField(XTIFF.TIFFTAG_OMNIANGLE);
				if (field == null)
				{
					omniAngle = null;
				} else
				{
					int count = field.getCount();
					omniAngle = new int[count];
					for (int i = 0; i < count; i++)
						omniAngle[i] = field.getAsInt(i);
				}
				field = null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public int getImageWidth()
	{
		return imageWidth;
	}
	public int getImageHeight()
	{
		return imageHeight;
	}
	public int getFramesInFile()
	{
		return framesInFile;
	}
	public float getXResolution()
	{
		return xResolution[0] / xResolution[1];
	}
	public float getYResolution()
	{
		return yResolution[0] / yResolution[1];
	}
	public int getAngle()
	{
		if (omniAngle != null)
		{
			//omni-angle is in 16th of a degree
			double ret;
			ret = omniAngle[0] / 16.0;
			return (int) (Math.round(ret));
		} else
		{
			return 0;
		}
	}
	public int getFrameDuration(int frame)
	{
		return 20;
	}
	public static void main(String[] args)
	{
		try
		{
			SeekableStream s =
				new FileSeekableStream("c:\\hpfiles\\0414070A.TIF");
			DSRInfo dsr = new DSRInfo(s);
			System.out.println("image Width = " + dsr.getImageWidth());
			System.out.println("image Height = " + dsr.getImageHeight());
			System.out.println("X Resolution = " + dsr.getXResolution());
			System.out.println("Y Resolution = " + dsr.getYResolution());
			System.out.println("DSRInfo Exiting.");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
