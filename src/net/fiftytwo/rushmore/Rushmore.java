/*
 * Created on Jul 18, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.fiftytwo.rushmore;

import javax.swing.JPanel;

import net.fiftytwo.exp.appProps.AppProps;
import net.fiftytwo.exp.appProps.GuiProps;
import net.fiftytwo.exp.controller.AppController;
import net.fiftytwo.exp.controller.Controller;
import net.fiftytwo.exp.gui.AppFrame;
import net.fiftytwo.exp.model.CountingModel;
import net.fiftytwo.exp.model.Model;
import net.fiftytwo.exp.model.DSRModel;
import net.fiftytwo.exp.options.Options;
import net.fiftytwo.exp.view.CineModelTextView;
import net.fiftytwo.exp.view.ImageView;

/**
 * @author jordan_2
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Rushmore extends JPanel
{

	private static void createAndShowApplication()
	{
		
		Model model = new DSRModel();

		ImageView imageView = new ImageView();
		CineModelTextView cmtView = new CineModelTextView("General Info");

		Controller controller = new AppController();
		
		//hook the controller to the model
		controller.addModel(model);	

		//hook the views to the model        
		//model.addAnimationEventListener(imageView);
		model.addObserver(cmtView);

		//the app frame shows the views and other gui pieces.
		AppFrame appFrame = AppFrame.getInstance();

		//the controller has a reference to the appFrame
		//so it can shut down the application
		controller.addMainFrame(appFrame);

		//add some views to the appFrame 
        appFrame.addImageView(imageView);
        appFrame.addTab(cmtView);

		//get swinging.
        appFrame.pack();
        appFrame.setVisible(true);
	}
	public static void main(String[] args)
	{	
		//let's just hit the enhancedProperties to make sure they get loaded
		GuiProps.getInstance();  
		AppProps.getInstance();  
		
		Options.getInstance().parseArgs(args);
	
		//start the gui thread
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowApplication();
			}
		});
	}
}
