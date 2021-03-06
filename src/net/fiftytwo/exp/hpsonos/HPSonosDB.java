/*
 * HPSonosDB.java
 *
 * Created on August 4, 2002, 11:48 PM
 */
package net.fiftytwo.exp.hpsonos;
import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;

import java.util.Vector;
/**
 *
 * @author  jordan
 */

public class HPSonosDB
{
    protected	String		strPath;
    protected	HPDirHeader	dirHeader;
    protected	Vector		vStudyEntries;
    protected	Vector		vFileEntries;

    /** Creates a new instance of HPSonosDB */
    public HPSonosDB()
    {
	vStudyEntries = new Vector();
	vFileEntries = new Vector();
    }
    
    /* takes in a path withouth the slashes at the end. this directory is
     * to contain an HPSonos.DB file.  if not, we will throw an error.
     */
    public void open(String path) throws IOException
    {
	/* first, test to see if there is a HPSONOS.DB file in the
	 * path provided
	 */
	
	strPath = path;

	int iEntryCounter = 0;
	int iHoleCounter = 0;
	int iEntryCount = 0;
	int iHoleCount = 0;
	path=path.replace('\\', File.separator.toCharArray()[0]);
	File fileDB = new File(path + File.separator + "HPSONOS.DB");
	if(!fileDB.exists())
	    throw new IOException("The file " + path + File.separator + "HPSONOS.DB" + " does not exist.");
	
	BufferedReader br = new BufferedReader(new FileReader(fileDB));
	
	//get the factories needed to create the data structure
	HPDirHeaderFactory dirHeaderFactory = HPDirHeaderFactory.getInstance();
	HPEntryFactory entryFactory = HPEntryFactory.getInstance();
	
	//get the header
	try{
	    dirHeader = dirHeaderFactory.newHPDirHeader(br, strPath);
	    iEntryCount = dirHeader.getEntryCount();
	    iHoleCount = dirHeader.getHoleCount();
	} catch (Exception e) {
	    System.out.println("an exception got caught from the dirHeaderFactory in HPSonosDB.open");
	    System.out.println("hole count = " + iHoleCount);
	    System.out.println("entry count = " + iEntryCount);
	}
	
	//now get all the nodes in the HPSONOS database and shove them in a Vector
	try{
	    while(iEntryCounter < iEntryCount)
	    {
		HPDirEntry dirEntry = entryFactory.newHPDirEntry(br, strPath);
		
		if(dirEntry == null)
		{
		    iHoleCounter++;
		}
		else
		{   
		    //this is a valid entry, so add it
		    iEntryCounter++;
		    if(dirEntry.getClass().getName().equals("HPStudyEntry"))
			vStudyEntries.add(dirEntry);
		    else
			vFileEntries.add(dirEntry);	    
		}
	    }
	    
	} catch (Exception e) {
	    throw new IOException("HPSonosDB.open(String): Cannot parse the HPSonos.DB File - open failed");
	}
    }

    public DefaultMutableTreeNode getRootNode()
    {
	HPDirEntry dirEntry;
	DefaultMutableTreeNode node;
	//build the entire tree and return the root node.
	
	//the header is the rootNode
	DefaultMutableTreeNode rootNode = dirHeader.getNodes();
	
	/*iterate thru the vector, build tree nodes and add them to the root node.
	 * if the entry is a StudyEntry, it will probably have children nodes, so
	 * add those, too.
	 */
	for(int i =0; i < vStudyEntries.size(); i++)
	{
	    dirEntry = (HPDirEntry)vStudyEntries.elementAt(i);
	    node = dirEntry.getNodes();
	    
	    //don't add the study dir entry, add the study's header instead.
	    HPStudyEntry studyEntry = (HPStudyEntry) dirEntry;
	    node = studyEntry.getNodes();
	    
	    rootNode.add(node); 
	}
	
	//now add the file directories
	HPSonos3DStudy study = new HPSonos3DStudy(strPath);
	for(int i =0; i < vFileEntries.size(); i++)
	{
	    
	    String strEntryClassName = vFileEntries.elementAt(i).getClass().getName();
System.out.println(strEntryClassName);	    
	    if(strEntryClassName.equals("net.fiftytwo.exp.hpsonos.HP3DLoopEntry") || strEntryClassName.equals("net.fiftytwo.exp.hpsonos.HPSummaryEntry") )
	    {
//if( (vFileEntries.elementAt(i) instanceof net.fiftytwo.exp.hpsonos.HP3DLoopEntry) || (vFileEntries.elementAt(i) instanceof net.fiftytwo.exp.hpsonos.HPSummaryEntry))
//{
		if( ! study.add(vFileEntries.elementAt(i)))
		{
System.out.println("the study failed for some reason");
		    //the add failed for whatever reason, so just add the directory to the root
		    dirEntry = (HPDirEntry)vFileEntries.elementAt(i);
		    node = dirEntry.getNodes();
		    rootNode.add(node);
		}
		if(study.isComplete())
		{
		    //the study is complete, so add the sucker to the root and create a new, empty one
		    node = study.getNodes();
		    rootNode.add(node);
		    study = new HPSonos3DStudy(strPath);
		}
		    
	    }
	    else 
	    {
System.out.println("This was not a symmary or 3d app: " + strEntryClassName);
		//it's not a 3d app or a summary, so just add the entry
		dirEntry = (HPDirEntry)vFileEntries.elementAt(i);
		node = dirEntry.getNodes();
		rootNode.add(node);
	    }
	}
	//now, if we have an incomplete but unempty HPSonos3Dstudy, show it, but as incomplete!
	if( !study.isComplete() && !study.isEmpty())
	{
	    node = study.getNodes();
	    rootNode.add(node);
	    study = new HPSonos3DStudy(strPath);
	}

	return rootNode;
    }
    
    public HPDirHeader getHeader(){ return dirHeader; }

    public static void main(String[] args)
    {
	String path;
	
	path = "c:\\heartFiles\\hpfiles";
	//path = "c:\\hpfilesTammyB";
	//path = "c:\\hpfiles\\9709\\JOHN00";
		
	HPSonosDB db = new HPSonosDB();
	try
	{
	    db.open(path);
	} catch (IOException ioe)
	{
	    System.out.println("can't open the file: " + path);
	    ioe.printStackTrace();
	}
    }
}