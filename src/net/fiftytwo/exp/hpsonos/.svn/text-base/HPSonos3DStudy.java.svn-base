/*
 * HPSonos3DStudy.java
 *
 * Created on August 14, 2002, 10:35 AM
 */
package net.fiftytwo.exp.hpsonos;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;
/**
 *
 * @author  jordan
 */
public class HPSonos3DStudy extends TreeSet implements HPSonosEntry
{
    protected	    int		iNumFiles;		//the number of files that should be in this study
    protected	    String	strPath;    
    protected HPSummaryEntry	entrySummaryEntry;
    /** Creates a new instance of HPSonos3DStudy */
    public HPSonos3DStudy(String path)
    {
	super(new Comparator(){
	    //order the studies by their angle.  Do a mod 360 here if you need it!
	    public int compare(Object obj, Object obj1)
	    {
		int iAngleA = ((HP3DLoopEntry)obj).getAngle();
		int iAngleB = ((HP3DLoopEntry)obj1).getAngle();
		if( iAngleA < iAngleB){ return -1; }
		else if( iAngleA > iAngleB){ return 1; }
		else{ return 0; }
	
	    }
	});
	
	strPath = path;
    }
    
    public boolean add(Object obj)
    {
	if(obj.getClass().getName().equals("net.fiftytwo.exp.hpsonos.HP3DLoopEntry"))
	    return this.add((HP3DLoopEntry)obj);
	else if(obj.getClass().getName().equals("net.fiftytwo.exp.hpsonos.HPSummaryEntry"))
	    return this.add((HPSummaryEntry)obj);
	else
	    return false;
    }
    
    public boolean add(HP3DLoopEntry studyEntry)
    {
	boolean bRet;
	
	if(this.isEmpty())
	{
	    iNumFiles = studyEntry.getNumFilesInDataSet();
	}
	//if there already are enough files in the dataset, don't add this one
	if(iNumFiles <= this.size()) 
	{
	    bRet = false;
	}
	else
	{
	    bRet = super.add(studyEntry);
	}
	
	return bRet;
	
    }
    
    public boolean add(HPSummaryEntry entry)
    {	
	if(entrySummaryEntry == null)
	{
	    entrySummaryEntry = entry;
	    return true;
	} else
	{
	    return false;
	}
    }
    
    // to be complete, we must have a summary and all the files
    public boolean isComplete()
    {
	if(entrySummaryEntry == null)
	{
	    return false;
	}else{
	    return (this.size() == iNumFiles);
	}
    }

    public String toString()
    {
	if(this.isComplete())
	    return "3D study containing " + iNumFiles + " files";
	else
	    return "This is an incomplete study containing " + this.size() + " out of " + iNumFiles + " files.";
    }
    
    public DefaultMutableTreeNode getNodes()
    {
	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(this);
	//now add a node for the summary and each file.
	if(entrySummaryEntry != null)
	    rootNode.add(entrySummaryEntry.getNodes());
	
	Iterator iter = this.iterator();
	while(iter.hasNext())
	{
	    rootNode.add(((HP3DLoopEntry)iter.next()).getNodes());
	    
	}
	
	return rootNode;
    }
    
    /** This returns a list of filenames associated with this object. Return null
     * if this entry contains no files.
     */
    public String[] getFileNames()
    {
	//return all the files starting with the summary if you've got it.
	int iSize = this.size();
	int count = 0;
	if(entrySummaryEntry != null)
	    iSize++;

	String[] strRet = new String[iSize];
	if(entrySummaryEntry != null)	
	    strRet[count++] = entrySummaryEntry.getFileNames()[0];

	Iterator iter = this.iterator();
	while(iter.hasNext())
	{
	    String strFilename  = ((HP3DLoopEntry)iter.next()).getFileNames()[0];
	    strRet[count++] = strFilename;
	}
	
	
	return strRet;
    }
    
    /** This returns a long, more informative string detailing the contents of this
     * entry.
     */
    public String getInfoString()
    {
	String strRet;
	strRet = "HP Sonos 3D Study \n";
	strRet = strRet + "Path = " + strPath + "\n";
	strRet = strRet + "Number of Files " + iNumFiles + "\n";
	strRet = strRet + "Contains " + this.size() + " Files\n";
	
	Iterator iter = this.iterator();
	while(iter.hasNext())
	{
	    HP3DLoopEntry entry = (HP3DLoopEntry)iter.next();
	    strRet = strRet + entry.getFileNames()[0] + " " + entry.getAngle() + " " + entry.getFileNum() + "\n";
	}
	
	return strRet;
    }
    
    /** this returns the physical path of where the files will be located.
     */
    public String getPath()
    {
	return strPath;
    }
    
}
