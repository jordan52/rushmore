/*
 * HPFileEntry.java
 *
 * Created on August 7, 2002, 10:43 AM
 */
package net.fiftytwo.exp.hpsonos;
/**
 *
 * @author  jordan
 */

public class  HPFileEntry extends HPDirEntry
{	
    //these are extracted from the applicationInfo tag
    private String	strProtocol;
    private int	iFileNumber;
    private int	iNumFilesInDataSet;
    private int	iAngle;


    public HPFileEntry(String SWRevision, String FileDirName, String PatientID,
	    String DateTime, String ApplicationInfo, int ApplicationID,
	    int FileType, int ScreenFormat, int MapFamily, int FrameCount, String path)
    {
	super(SWRevision, FileDirName, PatientID,
	    DateTime, ApplicationInfo, ApplicationID,
	    FileType, ScreenFormat, MapFamily, FrameCount, path);
	
	// now, put the missing . in strFileDirName
	StringBuffer filename = new StringBuffer(strFileDirName);
	filename.insert(filename.indexOf("TIF"), ".");
	strFileDirName = filename.toString();
	
    }
    
    public String[] getFileNames()
    {	
	String strRet[] = new String[1];
	strRet[0] = strFileDirName;
	return strRet;
    }
}
