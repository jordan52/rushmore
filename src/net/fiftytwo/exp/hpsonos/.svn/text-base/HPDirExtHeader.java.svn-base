/*
 * HPDirExtHeader.java
 *
 * Created on August 7, 2002, 10:56 AM
 */
package net.fiftytwo.exp.hpsonos;
import java.io.BufferedReader;


import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author  jordan
 */
public class HPDirExtHeader extends HPDirHeader implements HPSonosEntry
{
    private String strFileDirName;
    private String strPatientID;
    private Date dateTime;
    private String strAppInfo;
    private String strApplicationID;
    private String strFileType;
    
    private String strLastName;
    private String strFirstName;
    private String strID;
    private String strMisc;
    
    /** Creates a new instance of HPDirExtHeader */
    public HPDirExtHeader(BufferedReader br, String path)
    {
	super(br, path);
		//read in the 4 lines to fill up this object
	String instring;
	String strDateTime = "";
	
	try
	{
	    
	    instring = br.readLine();
	    strFileDirName = instring.trim();

	    instring = br.readLine();
	    strPatientID = instring;

	    instring = br.readLine();
	    strDateTime = instring.trim();

	    instring = br.readLine();
	    strAppInfo = instring.trim();

	    instring = br.readLine();
	    strApplicationID = instring.trim();
	    
	    instring = br.readLine();
	    strFileType = instring.trim();
	    
	}
	catch (Exception e)
	{
	    System.out.println("Exception occured in HPDirExtHeader constructor!");
	}
	
	//parse the date and put it into dateTime. it comes in as YYYY:MM:DD hh:mm:ss
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
	try{
	    dateTime = dateFormat.parse(strDateTime);
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

	if(!strAppInfo.matches("[^¥]*"))
	{
	    //clean up strAppInfo
	    strAppInfo = strAppInfo.replace('¥', ' ');
	    strAppInfo.trim();
	}
    }

    
    public String toString()
    {
	return strLastName + "(" + dateTime.toString() + ")"+strAppInfo;
    }
    
    public String getInfoString()
    {
	String strRet = "";
	strRet = strRet + "Last Name = " + strLastName + "\n";
	strRet = strRet + "First Name = " + strFirstName + "\n";
	strRet = strRet + "ID = " + strID + "\n";
	strRet = strRet + "Misc = " + strMisc + "\n";
	strRet = strRet + "Date = " + dateTime.toString() + "\n";
	strRet = strRet + "Application Info = " + strAppInfo + "\n";
	strRet = strRet + "Application ID = " + strApplicationID + "\n";
	return strRet;
    }
}
