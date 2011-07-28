/*
 * Created on Feb 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.command;
import net.fiftytwo.exp.appProps.AppProps;
import net.fiftytwo.exp.appProps.GuiProps;
import net.fiftytwo.exp.model.dsr.DSRImageSet;
import net.fiftytwo.exp.gui.AppFrame;
import net.fiftytwo.exp.gui.ExampleFileFilter;
import net.fiftytwo.exp.gui.dsr.OpenDSRDialog;
import net.fiftytwo.exp.model.DSRModel;
import java.io.File;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.log4j.Category;
/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OpenDSRCommand extends Command
{
	private static Category cat = Category.getInstance("OpenDSRCommand");
	private static GuiProps guiProps = GuiProps.getInstance();
	private static AppProps appProps = AppProps.getInstance();
	private DSRModel dsrModel; //a handle to the model we're going to populate
	private OpenDSRDialog dialog;
	private JFileChooser chooser;
	private File dbFile;
	private String[] fileList;
	private String dsrPath;
	public OpenDSRCommand(DSRModel dsrModel)
	{
		this.dsrModel = dsrModel;
		dialog = new OpenDSRDialog("Open a DSR");
		//make the chooser open to the last opened DB
		chooser = new JFileChooser(appProps.getProperty("lastHPSonosDB", ""));
		initGUI();
	}
	protected void initGUI()
	{
		cat.debug("initializing the GUI");
		// Note: source for ExampleFileFilter can be found in FileChooserDemo,
		// under the demo/jfc directory in the Java 2 SDK, Standard Edition.
		ExampleFileFilter filter = new ExampleFileFilter();
		filter.addExtension("db");
		filter.setDescription("HP Sonos Database Files");
		chooser.setFileFilter(filter);
	}
	//this is here just in case we want to set the fileList and dsrPath
	//programatically and execute the command with no GUI
	public void executeSilent()
	{
		try
		{
			if (fileList != null)
			{
				//create the DSRImageSet!
				DSRImageSet imageSet = new DSRImageSet();
				//convert the array of filenames to a collection
				//BUT SKIP THE FIRST ONE BECAUSE IT IS ALWAYS THE SUMMARY SCREEN
				Vector vFilenames = new Vector(fileList.length);
				for (int i = 1; i < fileList.length; i++)
				{
					vFilenames.add(fileList[i]);
				}
				imageSet.load(dsrPath, vFilenames);
				dsrModel.load(imageSet);
				imageSet = null;
			}
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(
				null,
				"Unable to Open the selected DSR: " + e,
				"ERROR!",
				JOptionPane.ERROR_MESSAGE);
			cat.debug("Unable to Open the Selected DSRImageSet.", e);
		}
	}
	public void execute()
	{
		cat.debug("executing the open command");
		try
		{
			askUser();
			executeSilent();
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(
				null,
				"Unable to Open the selected DSR: " + e,
				"ERROR!",
				JOptionPane.ERROR_MESSAGE);
			cat.debug("Unable to Open the Selected DSRImageSet.", e);
		}
	}
	protected void askUser()
	{
		cat.debug("Asking the user");
		int dbReturnVal = chooser.showOpenDialog(dialog);
		if (dbReturnVal == JFileChooser.APPROVE_OPTION)
		{
			//keep track of the last opened db and try to open this dialog to that directory.
			appProps.setProperty(
				"lastHPSonosDB",
				chooser.getSelectedFile().getPath());
			dbFile = chooser.getSelectedFile();
			dialog.setHPSonosDBFile(dbFile);
			int dsrReturnVal = dialog.showDialog(AppFrame.getInstance());
			if (dsrReturnVal == OpenDSRDialog.APPROVE_OPTION)
			{
				cat.debug("we got an APPROVE_OPTION");
				this.fileList = dialog.getFileList();
				this.dsrPath = dialog.getDsrPath();
			} else
			{
				cat.debug(
					"we didn't get an approve option, we got: " + dsrReturnVal);
				return;
			}
		}
	}
	public String[] getFileList()
	{
		return fileList;
	}
	public void setDsrPath(String dsrPath)
	{
		this.dsrPath = dsrPath;
	}
	public void setFileList(String[] fileList)
	{
		this.fileList = fileList;
	}
	public String getDsrPath()
	{
		return dsrPath;
	}
}
