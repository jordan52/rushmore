/*
 * HPDirHeaderFactory.java
 *
 * Created on August 7, 2002, 10:59 AM
 */
package net.fiftytwo.exp.hpsonos;
import java.io.*;

/**
 *
 * @author  jordan
 */
public class HPDirHeaderFactory
{
    //reference to the Singleton HPDirHeaderFactory
    private static HPDirHeaderFactory factory;

    protected HPDirHeaderFactory()
    {
    }

    public static final HPDirHeaderFactory getInstance()
    {
	if (factory == null)
	{
	    factory = new HPDirHeaderFactory();
	}
	return factory;
    }

    public HPDirHeader newHPDirHeader(BufferedReader br, String path) throws IOException
    {
	/* read the data and see if we shall create a HPDirHeader
	 * or an HPDirExtHeader
	 */
	String instring;
	int iHeaderSize;
	
	instring = br.readLine();
	
	if(instring == null)
	    throw new IOException();
	
	try
	{
	    iHeaderSize = Integer.parseInt(instring.trim(), 16);
	    
	    if(iHeaderSize == 34)
	    {
		return new HPDirHeader(br, path);
	    }
	    else if (iHeaderSize == 266)
	    {
		return new HPDirExtHeader(br, path);
	    }
	    else
	    {
		throw new Exception("A HeaderSize of " + iHeaderSize + " is not recognized." );
	    }
	    
	} catch (Exception e)
	{
	    e.printStackTrace();
	    System.out.println("cant' parse the dir entry length in HPDirHeaderFactory");
	    throw new IOException();
	}
	
    }    
    
}
