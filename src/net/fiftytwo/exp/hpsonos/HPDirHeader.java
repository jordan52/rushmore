/*
 * HPDirHeader.java
 *
 * Created on August 7, 2002, 10:43 AM
 */
package net.fiftytwo.exp.hpsonos;
import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author  jordan
 */
public class HPDirHeader implements HPSonosEntry
{
    protected int iNextFreeOffset;
    protected int iEntryCount;
    protected int iHoleCount;
    
    protected String strPath;

    public HPDirHeader(BufferedReader br, String path)
    {
	strPath = path;
	
	//read in the 4 lines to fill up this object
	String instring;
	
	try
	{
	    
	    instring = br.readLine();
	    iNextFreeOffset = Integer.parseInt(instring.trim(), 16);

	    instring = br.readLine();
	    iEntryCount = Integer.parseInt(instring.trim(), 16);
	     
	    instring = br.readLine();
	    iHoleCount = Integer.parseInt(instring.trim(), 16);
	    
	}
	catch (Exception e)
	{
	    System.out.println("Exception occured in HPDirHeader constructor!");
	}
    }
    
    /*  cheesy debugging print that'll print this object to System.out
     */
    public void print()
    {
	System.out.println("Printing an HPDirHeader Object");
	System.out.println("strPath = " + strPath);
	System.out.println("iNextFreeOffset = " + iNextFreeOffset);
	System.out.println("iEntryCount = " + iEntryCount);
	System.out.println("iHoleCount = " + iHoleCount);
	
    }
    
    public String toString()
    {
	return strPath + "\\HPSONOS.DB";
    }
    
    public int getEntryCount(){ return iEntryCount; }

    public int getHoleCount(){ return iHoleCount; }
 
    /**
     * this returns the physical path of where the files will be located.
     */
    public String getPath(){ return strPath; }
    
    /** This returns a list of filenames associated with this object. Return null
     * if this entry contains no files.
     */
    public String[] getFileNames()
    {
	return null;
    }
    
    /** This returns a long, more informative string detailing the contents of this
     * entry.
     */
    public String getInfoString()
    {
	String strRet = "HP Sonos DB Header\n";
	strRet = strRet + "Physical Path = " + strPath + "\n";
	strRet = strRet + "Next Free Disk Offset = " + iNextFreeOffset + "\n";
	strRet = strRet + "Number of Entries = " + iEntryCount + "\n";
	strRet = strRet + "Number of Holes = " + iHoleCount + "\n";
	return strRet;
    }
    
    /**  Returns the DefaultMutableTree node containing the representation of this
     *  object for insertion into a TreeModel
     */
    public DefaultMutableTreeNode getNodes()
    {
	return new DefaultMutableTreeNode(this);
    }
    
};