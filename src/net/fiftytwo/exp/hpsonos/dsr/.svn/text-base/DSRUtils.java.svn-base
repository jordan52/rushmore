package net.fiftytwo.exp.hpsonos.dsr;

/*
 * DSRUtils.java
 *
 * Created on August 12, 2002, 6:58 PM
 */

/**
 * This is a singleton that contains constants that are found
 * in DSR TIFs and the HPSonos.DB file 
 * @author  jordan
 */
public class DSRUtils
{
    private static DSRUtils utils;
    
    private static String[] DATA_TYPE = 
    {	"Still Frame", "CLR (loop) Memory","Unknown DataType", 
	"Unknown Data Type",
	"Analysis Report (still frames format)",
	"DSR Study", "3D Rotate Loop",
	"3D Summary Loop"
    };
    
    /** Creates a new instance of DSRUtils */
    protected DSRUtils()
    {
    }
    
    public static final DSRUtils getInstance()
    {
	if (utils == null)
	{
	    utils = new DSRUtils();
	}
	return utils;
    }
    
    public static final String getDataType(int type)
    {
	if(type >= 0 || type <= 7)
	    return DATA_TYPE[type];
	else
	    return "Unknown Data Type";
	
    }
}
