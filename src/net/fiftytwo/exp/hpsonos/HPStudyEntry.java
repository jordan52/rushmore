/*
 * HPStudyEntry.java
 *
 * Created on August 7, 2002, 10:42 AM
 */
package net.fiftytwo.exp.hpsonos;
import java.io.*; //for File.separator
import javax.swing.tree.*;
import java.util.Vector;
/**
 *
 * @author  jordan
 */
public class HPStudyEntry extends HPDirEntry
{
    protected String strSubDirectoryName;  //from fileDirName slot
    protected String strStudyName;  //from the ApplicationInfo slot
    
    protected HPSonosDB db;
    
    public HPStudyEntry(String SWRevision, String FileDirName, String PatientID,
	    String DateTime, String ApplicationInfo, int ApplicationID,
	    int FileType, int ScreenFormat, int MapFamily, int FrameCount, String path)
    {
	super(SWRevision, FileDirName, PatientID,
	    DateTime, ApplicationInfo, ApplicationID,
	    FileType, ScreenFormat, MapFamily, FrameCount, path);
	
	//replace the backslash in strFileDirName with File.separator
	strFileDirName.replace('\\', File.separatorChar );
	strSubDirectoryName = strPath + File.separator + strFileDirName;
	
	db = new HPSonosDB();
	try
	{
	    db.open(strSubDirectoryName);
	} catch (IOException ioe)
	{
	    System.out.println("HPStudyEntry can't open the file: " + strSubDirectoryName + File.separator + "HPSONOS.DB");
	    ioe.printStackTrace();
	}
    }
    
    //for study entries, print out the studyname (applicationInfo) and the dateTime
    public String toString() { return strLastName + "(" + dateTime.toString() + ")" + " " + strApplicationInfo; }

    public String getInfoString()
    {
	String strReturn = super.getInfoString();
	strReturn = strReturn + " strSubDirectoryName = " + strSubDirectoryName + "\n";
	strReturn = strReturn + " strStudyName = " + strStudyName + "\n";
	return strReturn;
	
    }
    
    public DefaultMutableTreeNode getNodes()
    {
	return db.getRootNode();	    
    }
}
