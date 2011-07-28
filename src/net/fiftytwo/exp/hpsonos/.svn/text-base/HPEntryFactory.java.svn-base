/*
 * HPEntryFactory.java
 *
 * Created on August 7, 2002, 10:40 AM
 */
package net.fiftytwo.exp.hpsonos;
import java.io.*;
/**
 *
 * @author  jordan
 */
    /*  This factory will build the objects that represent the entires
     * in the HPSONOS.DB file.  It will decide whether to build a file
     * entry or a study entry based on parameters given to it.
     */
public class HPEntryFactory
{
    //reference to the Singleton HPEntryFactory
    private static HPEntryFactory factory;

    protected HPEntryFactory()
    {
    }

    public static final HPEntryFactory getInstance()
    {
	if (factory == null)
	{
	    factory = new HPEntryFactory();
	}
	return factory;
    }

    public HPDirEntry newHPDirEntry(BufferedReader br, String path) throws IOException
    {
	/* read the entry and see if we shall create a HPStudyEntry 
	 * or an HPFileEntry
	 */
	String instring;
	int iHeaderSize;
	
	
	String strSWRevision;
	String strFileDirName;
	String strPatientID;
	String strDateTime;
	String strApplicationInfo;
	int iApplicationID;
	int iFileType;
	int iScreenFmt;
	int iMapFamily;
	int iFrameCount;

	try
	{
	    instring = br.readLine(); //skip the FF from the preceding entry or header

	    /* actually i realized if the headersize is 6 long, we need
	     * to lop off the first three chars... this is because of junk
	     * data that includes a "9", "0", a weird character, and who
	     * knows what else.
	     */
	    if(instring.length() == 6)
	    {
		instring = instring.substring(3);
	    }
	    else
	    {
		instring = instring.substring(2);  //trim off the <FF>
	    }
	    instring.trim();
	    iHeaderSize = Integer.parseInt(instring, 16);
	    
	    strSWRevision = br.readLine().trim();
	    
	    strFileDirName = br.readLine().trim();
	    
	    strPatientID = br.readLine();
	    
	    strDateTime = br.readLine().trim();
	    
	    strApplicationInfo = br.readLine().trim();
	    
	    iApplicationID = Integer.parseInt(br.readLine().trim(), 16);
	    
	    iFileType = Integer.parseInt(br.readLine().trim(), 16);
	    
	    iScreenFmt = Integer.parseInt(br.readLine().trim(), 16);
	    	    
	    iMapFamily = Integer.parseInt(br.readLine().trim(), 16);
	    
	    iFrameCount= Integer.parseInt(br.readLine().trim(), 16);
	    
	    //if the headersize isn't 266, then it's a hole, so skip it.
	    if(iHeaderSize != 266)
	    {
		return null;
	    }
	    
	    if(iScreenFmt == 0)
	    {
		return new HPStudyEntry(strSWRevision, strFileDirName, strPatientID,
		    strDateTime, strApplicationInfo, iApplicationID,
		    iFileType, iScreenFmt, iMapFamily, iFrameCount, path);
	    }
	    else
	    {
		if(iFileType == 1)
		{
		    if(iApplicationID == 3) //then it's a 3D application
		    {
			return new HP3DLoopEntry(strSWRevision, strFileDirName, strPatientID,
			strDateTime, strApplicationInfo, iApplicationID,
			iFileType, iScreenFmt, iMapFamily, iFrameCount, path);
		    }
		    else
		    {
			return new HPCLRLoopEntry(strSWRevision, strFileDirName, strPatientID,
			    strDateTime, strApplicationInfo, iApplicationID,
			    iFileType, iScreenFmt, iMapFamily, iFrameCount, path);
		    }
		}
		else if(iFileType == 7)
		{
		    return new HPSummaryEntry(strSWRevision, strFileDirName, strPatientID,
			strDateTime, strApplicationInfo, iApplicationID,
			iFileType, iScreenFmt, iMapFamily, iFrameCount, path);
		}
		else
		{
		    return new HPFileEntry(strSWRevision, strFileDirName, strPatientID,
			strDateTime, strApplicationInfo, iApplicationID,
			iFileType, iScreenFmt, iMapFamily, iFrameCount, path);
		}
	    }
	    
	} catch (Exception e)
	{
	    e.printStackTrace();
	    System.out.println("cant' parse the entry in HPEntryFactory for " + path);
	    throw new IOException();
	}
    }

};
