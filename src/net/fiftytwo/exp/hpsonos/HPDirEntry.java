/*
 * HPDirEntry.java
 *
 * Created on August 7, 2002, 10:41 AM
 */
package net.fiftytwo.exp.hpsonos;
import java.io.*; //for File.separator

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.tree.DefaultMutableTreeNode;

import net.fiftytwo.exp.hpsonos.dsr.DSRUtils;

/**
 *
 * @author  jordan
 */
public class HPDirEntry implements HPSonosEntry
{
    //these all come in straight out of the DB file
    protected String	strSWRevision;//[8];
    protected String	strFileDirName;//[11];
    protected String	strPatientID;//[61];
    protected Date	dateTime;
    protected String	strApplicationInfo;//[120];
    protected int	iApplicationID;
    protected int	iFileType;
    protected int	iScreenFmt;
    protected int	iMapFamily;
    protected int	iFrameCount;
    protected String	strPath;
    
    //extracted from the PatientID field 
    protected String	strLastName;
    protected String	strFirstName;
    protected String	strID;
    protected String	strMisc;
    

    public HPDirEntry(String SWRevision, String FileDirName, String PatientID,
	    String DateTime, String ApplicationInfo, int ApplicationID,
	    int FileType, int ScreenFormat, int MapFamily, int FrameCount, String path)
    {

	strSWRevision = SWRevision;
	strFileDirName = FileDirName;
	strPatientID = PatientID;
	strApplicationInfo = ApplicationInfo;
	iApplicationID = ApplicationID;
	iFileType = FileType;
	iScreenFmt = ScreenFormat;
	iMapFamily = MapFamily;
	iFrameCount = FrameCount;
	strPath = path;
	
	//replace the backslash in strFileDirName with File.separator
	strFileDirName.replace('\\', File.separatorChar );

	//parse the date and put it into dateTime. it comes in as YYYY:MM:DD hh:mm:ss
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
	try{
	    dateTime = dateFormat.parse(DateTime);
	} catch (Exception e) {
	    e.printStackTrace();
	    dateTime = new Date();
	}
	
	//parse the PatientID field
	try{

	    strLastName = strPatientID.substring(0, 14).trim();
	    strFirstName = strPatientID.substring(15, 29).trim();
	    strID = strPatientID.substring(30, 44).trim();
	    strMisc = strPatientID.substring(45, 58).trim();
	} catch (IndexOutOfBoundsException e) { 
	    e.printStackTrace();
	}
	
    }
    
    public String getInfoString()
    {
	String strReturn;
	strReturn = "Path = " + strPath + "\n";
	strReturn = strReturn + "Software Revision = " + strSWRevision + "\n";
	strReturn = strReturn + "File/Directory Name = " + strFileDirName + "\n";
	strReturn = strReturn + "Unformatted PatientID = " + strPatientID + "\n";
	strReturn = strReturn + "Date = " + dateTime.toString() + "\n";
	strReturn = strReturn + "Application Info =  " + strApplicationInfo + "\n";
	strReturn = strReturn + "Application ID = " + iApplicationID + "\n";
	strReturn = strReturn + "File Type = " + iFileType + "\n";
	strReturn = strReturn + "Screen Format = " + iScreenFmt + "\n";
	strReturn = strReturn + "Map Family = " + iMapFamily + "\n";
	strReturn = strReturn + "Frame Count = " + iFrameCount + "\n";
	
	strReturn = strReturn + "Last Name = " + strLastName + "\n";
	strReturn = strReturn + "First Name = " + strFirstName + "\n";
	strReturn = strReturn + "ID = " + strID + "\n";
	strReturn = strReturn + "Misc = " + strMisc + "\n";
	return strReturn;
    }   
    
    public String getPath()
    {
	return strPath;
    }
    public String[] getFileNames()
    {	
	return null;
    }
    
    public String toString()
    {
	String strFileType = "";
	DSRUtils util = DSRUtils.getInstance();
	strFileType = DSRUtils.getDataType(iFileType);
	return strLastName + "(" + dateTime.toString() + ") " + strFileType ;
    }
    
    /**
     *  Returns the DefaultMutableTree node containing the representation of this
     *  object for insertion into a TreeModel
     */
    public DefaultMutableTreeNode getNodes()
    {
	return new DefaultMutableTreeNode(this);
    }
};
