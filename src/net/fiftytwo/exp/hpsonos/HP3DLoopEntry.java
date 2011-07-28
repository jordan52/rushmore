/*
 * HP3DLoopEntry.java
 *
 * Created on August 14, 2002, 9:51 AM
 */
package net.fiftytwo.exp.hpsonos;
import java.util.*;

/**
 *
 * @author  jordan
 */

public class HP3DLoopEntry extends HPFileEntry
{
    protected String	strProtocol;
    protected int	iFileNum;
    protected int	iNumFilesInDataSet;
    protected int	iAngle;
    
 
    /** Creates a new instance of HPCLRLoopEntry */
    public HP3DLoopEntry(String SWRevision, String FileDirName, String PatientID,
	    String DateTime, String ApplicationInfo, int ApplicationID,
	    int FileType, int ScreenFormat, int MapFamily, int FrameCount, String path)
    {
	super(SWRevision, FileDirName, PatientID,
	    DateTime, ApplicationInfo, ApplicationID,
	    FileType, ScreenFormat, MapFamily, FrameCount, path);
	
	//Now, let's split up the iApplicationInfo tag to see what we've got
	// it's in the fomrat <protocol>,<file#>/<#filesInDataSet>,<angle>
	//"[0-9][a-zA-z],[0-9](3)/[0-9](3),[0-9](3)"
	String[] splitted = strApplicationInfo.split(",");

	if(splitted.length == 3) 
	{
	    strProtocol = splitted[0];
	    try{
		iAngle = Integer.parseInt(splitted[2]);
	    } catch (NumberFormatException e) {
		e.printStackTrace();
		iAngle = 0;
	    }
	    String [] fileNumSplit = splitted[1].split("/");
	    if(fileNumSplit.length == 2)
	    {
		try{
		    iFileNum = Integer.parseInt(fileNumSplit[0]);
		    iNumFilesInDataSet = Integer.parseInt(fileNumSplit[1]);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		    iFileNum = 0;
		    iNumFilesInDataSet = 0;
		}
	    }
	}
    }

    public String getProtocol() { return strProtocol; }
    
    public int getAngle() { return iAngle; }
    
    public int getFileNum() { return iFileNum; }
    
    public int getNumFilesInDataSet() { return iNumFilesInDataSet; }

    public String getInfoString()
    {
	String strRet = super.getInfoString();
	
	strRet = strRet + " strProtocol " + strProtocol + "\n";
	strRet = strRet + " iAngle " + iAngle + "\n";
	strRet = strRet + " iFileNum " + iFileNum + "\n";
	strRet = strRet + " iNumFilesInDataSet " + iNumFilesInDataSet + "\n";
	
	return strRet;
    }

    
}
