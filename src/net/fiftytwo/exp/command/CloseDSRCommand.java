/*
 * Created on Mar 28, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.command;

import net.fiftytwo.exp.appProps.AppProps;
import net.fiftytwo.exp.appProps.GuiProps;
import net.fiftytwo.exp.model.DSRModel;

import org.apache.log4j.Category;

/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CloseDSRCommand extends Command
{
	private static Category cat = Category.getInstance("CloseDSRCommand");

	private static GuiProps guiProps = GuiProps.getInstance();
	private static AppProps appProps = AppProps.getInstance();

	private DSRModel dsrModel; //a handle to the model we're going to close

	public CloseDSRCommand(DSRModel dsrModel)
	{
		this.dsrModel = dsrModel;
	}

	/* (non-Javadoc)
	 * @see net.fiftytwo.exp.command.Command#execute()
	 */
	public void execute()
	{
		this.dsrModel.unload();
	}
}
