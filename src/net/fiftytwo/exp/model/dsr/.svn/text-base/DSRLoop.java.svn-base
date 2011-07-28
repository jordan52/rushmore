/*
 * Created on Feb 20, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.model.dsr;
/*
 * DSRLoop.java
 *
 * Created on July 23, 2002, 9:43 PM
 */
/**
 *
 * @author  jordan
 */
import java.util.Vector;
import java.util.Collections;
import java.util.Collection;
import java.io.*;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.*;
import javax.media.jai.ParameterBlockJAI;
import com.sun.media.jai.codec.*;
import org.libtiff.jai.codecimpl.XTIFFCodec;
import org.libtiff.jai.operator.XTIFFDescriptor;
import org.libtiff.jai.util.FileSeekableStreamWithLength;
public class DSRLoop
{
	private DSRInfo dsrInfo;
	private Vector images;
	private boolean bIsModified; //set to true if an image is set from outside.
	/** Creates a new instance of DSRLoop */
	public DSRLoop()
	{
		images = new Vector();
	}
	public DSRLoop(String filename)
	{
		images = new Vector();
		int y = 0;
		try
		{
			XTIFFDescriptor.register();
			File file = new File(filename);
			SeekableStream s = new FileSeekableStreamWithLength(file);
			TIFFDecodeParam param = null;
			ImageDecoder dec = null;
			dec = XTIFFCodec.createImageDecoder("tiff", s, param);
			//get all the images
			RenderedImage op =
				new NullOpImage(
					dec.decodeAsRenderedImage(0),
					null,
					OpImage.OP_IO_BOUND,
					null);
			//now get the DSRInfo from the image
			dsrInfo = new DSRInfo(s);
			//crop the image to get each image in the sequence
			ParameterBlockJAI pb = new ParameterBlockJAI("crop");
			pb.addSource((PlanarImage) op);
			pb.set(0.0f, "x");
			pb.set((float) dsrInfo.getImageWidth(), "width");
			pb.set((float) dsrInfo.getImageHeight(), "height");
			float aspectRatio =
				dsrInfo.getXResolution() / dsrInfo.getYResolution();
			for (int i = 0; i < dsrInfo.getFramesInFile(); i++)
			{
				y = dsrInfo.getImageHeight() * i;
				pb.set((float) y, "y");
				RenderedOp cropped = JAI.create("crop", pb);
				//scale and translate the image to get the right aspect ratio and move to the top
				ParameterBlock pbScale = new ParameterBlock();
				pbScale.addSource((PlanarImage) cropped);
				pbScale.add(1.0f); //mag x
				pbScale.add(1.0f * aspectRatio); //mag y
				pbScale.add(0.0f); //dx
				pbScale.add((float) - y * aspectRatio); //dy
				pbScale.add(
					Interpolation.getInstance(Interpolation.INTERP_NEAREST));
				RenderedOp scaled = JAI.create("scale", pbScale);
				//		ParameterBlockJAI pbConvert = new ParameterBlockJAI();
				//		pb.addSource((PlanarImage)scaled);
				//		pb.set(ColorSpace.getInstance(ColorSpace.CS_sRGB), "ColorSpace");
				//		RenderedOp colorConvert = JAI.create("ColorConvert", pb);
				//RenderedOp colorConvert = JAI.create("ColorConvert", scaled,ColorSpace.getInstance(ColorSpace.CS_sRGB)); 
				//		byte[] r = createByteArray();
				//		byte[] g = createByteArray();
				//		byte[] b = createByteArray();
				//		RenderedOp colorConvert = JAI.create("ColorConvert", scaled,
				//		  new IndexColorModel(8, 255, r, g, b)); 
				RenderedOp lookup =
					JAI.create("lookup", scaled, createLookupTable());
				images.add(lookup);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public DSRLoop(DSRInfo dsr)
	{
		dsrInfo = dsr;
	}
	public DSRLoop(DSRInfo dsr, Vector vect)
	{
		dsrInfo = dsr;
		images = vect;
	}
	public void addImage(PlanarImage img)
	{
		images.add(img);
	}
	public void addImageVector(Vector vImgs)
	{
		images = vImgs;
	}
	public PlanarImage getImage(int index) throws IndexOutOfBoundsException
	{
		try
		{
			return (PlanarImage) images.get(index);
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"DSRLoop.getImage(index " + index + ") out of bounds");
		}
	}
	public void setImage(PlanarImage image, int index)
		throws IndexOutOfBoundsException
	{
		if (index > dsrInfo.getFramesInFile())
			throw new IndexOutOfBoundsException(
				"DSRLoop.setImage(PlanarImage, index "
					+ index
					+ ") out of bounds");
		try
		{
			images.set(index, image);
			bIsModified = true;
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new IndexOutOfBoundsException(
				"DSRLoop.setImage(PlanarImage, index "
					+ index
					+ ") out of bounds");
		}
	}
	/* don't use me, use getImages instead! */
	public Vector getImageVector()
	{
		return images;
	}
	public Collection getImages()
	{
		return Collections.unmodifiableCollection((Collection) images);
	}
	public DSRInfo getDSRInfo()
	{
		return dsrInfo;
	}
	public boolean isModified()
	{
		return bIsModified;
	}
	protected static LookupTableJAI createLookupTable()
	{
		byte lut[] = new byte[256];
		for (int i = 0; i < 200; i++)
		{
			lut[i] = (byte) (i * 1.28);
			//factor by 1.9 so the values go from 0 to 256
		}
		for (int i = 200; i < 256; i++)
		{
			lut[i] = (byte) 0xff;
		}
		//blacken the background
		for (int i = 253; i < 256; i++)
		{
			lut[i] = (byte) 0;
		}
		//set physio-x to dark gray
		for (int i = 206; i < 230; i++)
		{
			lut[i] = (byte) 52;
		}
		//set graphics planes to lighter grey
		for (int i = 232; i < 248; i++)
		{
			lut[i] = (byte) 152;
		}
		lut[230] = (byte) 0;
		lut[231] = (byte) 0;
		//redmark
		lut[204] = (byte) 200;
		lut[203] = (byte) 0;
		lut[205] = (byte) 200;
		lut[202] = (byte) 0;
		//erase bar
		lut[200] = (byte) 100;
		lut[201] = (byte) 100;
		return new LookupTableJAI(lut, 0);
	}
	protected static byte[] createByteArray()
	{
		byte lut[] = new byte[256];
		for (int i = 0; i < 200; i++)
		{
			lut[i] = (byte) (i * 1.28);
			//factor by 1.9 so the values go from 0 to 256
		}
		return lut;
	}
}
