/*
 * Created on Feb 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.controller;

import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.lang.Boolean;
import java.net.URL;
import java.util.Observable;

import net.fiftytwo.exp.appProps.AppProps;
import net.fiftytwo.exp.appProps.GuiProps;
import net.fiftytwo.exp.command.OpenDSRCommand;
import net.fiftytwo.exp.command.CloseDSRCommand;
import net.fiftytwo.exp.gui.AppFrame;
import net.fiftytwo.exp.model.DSRModel;
import net.fiftytwo.exp.model.Model;

import org.apache.log4j.Category;

import com.sun.jaf.ui.ActionManager;

/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AppController extends Controller
{
    private static Category cat = Category.getInstance("AppController");

    private static String ACTION_RESOURCE_FILE = "appActions.xml";

    //	The Application action manager;
    private ActionManager manager;

    private AppFrame mainFrame;

    private DSRModel model;

    
    /*
     * 
    public static void main(String[] args) {
	// load the Actions
	ActionManager.loadActions(getClass().getResource("myactions.xml"));
	// Register the callbacks for the loaded Actions.
	ActionHandler handler = new ActionHandler();
	manager.registerCallback("actionID", handler, "methodName");
	// Create the toplevel frame
	JFrame frame = new JFrame();
	// Create the toplevel menu
	frame.setJMenuBar(UIFactory.createMenuBar("main-menu"));
	// Create the tool bar
	frame.getContentPane().add(BorderLayout.NORTH,
				   UIFactory.createToolbar("main-toolbar"));
	// Pack and show the frame
	frame.pack();
	frame.setVisible(true);
    }
     * 
     * 
     */
    

    public AppController()
    {
        super();
        init();
    }

    protected void init()
    {
        URL actionURL = ClassLoader.getSystemResource(ACTION_RESOURCE_FILE);

        manager = ActionManager.getInstance();

        try {
            manager.loadActions(actionURL);
        } catch(IOException ioe) {
            cat.error("ERROR parsing actionResourceFile: " + ioe);
            cat.error(
                "Unable to initialize the controller, the app is going down!");
            return;
        }

        //register the callbacks.
        manager.registerCallback("exit-command", this, "handleExit");
        manager.registerCallback("new-command", this, "handleNew");
        manager.registerCallback("open-command", this, "handleOpen");
        manager.registerCallback("save-command", this, "handleSave");
		manager.registerCallback("close-command", this, "handleClose");

        manager.registerCallback("cut-command", this, "handleCut");
        manager.registerCallback("copy-command", this, "handleCopy");
        manager.registerCallback("paste-command", this, "handlePaste");

        manager.registerCallback("play-command", this, "handlePlay");
        manager.registerCallback("pause-command", this, "handlePause");

        manager.registerCallback("history-command", this, "handleViewStatus");
        manager.registerCallback("about-command", this, "handleAbout");

        // Set the initial state of the actions
        manager.setSelected("open-command", true);
		manager.setSelected("close-command", false);

        manager.setEnabled("play-command", false);
        manager.setEnabled("pause-command", false);

        manager.setEnabled("save-command", false);
        manager.setEnabled("cut-command", false);
        manager.setEnabled("copy-command", false);
        manager.setEnabled("paste-command", false);
    }

    //adding the model to the controller ALSO makes this controller observe
    //the model.  We do this to handle major events that happen within the
    //model
    public void addModel(Model model)
    {
        this.model = (DSRModel) model;
        model.addObserver(this);
    }

    //we add the main frame to handle shutdowns properly.
    public void addMainFrame(AppFrame mainFrame)
    {
        this.mainFrame = mainFrame;

        // the controller must listen to the main frame
        //for shutdown events and whatnot.
        this.mainFrame.addWindowListener(this);
    }

    //remember, Controller is a WindowListener.
    //we catch this event from the AppFrame for when
    //people close the window by hitting the X in the
    //upper right corner.
    public void windowClosing(WindowEvent e)
    {
        this.handleExit();
    }

    /*
     *
     *    Handle events from the gui (action handlers)
     *
     */
    public void handleExit()
    {
        cat.debug("Shutting Down");

		//TODO: remember which study was open, current state, etc. 
        
        handleClose();

        mainFrame.shutDown();

        GuiProps.getInstance().save();
        AppProps.getInstance().save();

        System.exit(0);
    }

    public void handleNew()
    {
        cat.debug("Handling an NEW action");
    }

    public void handleOpen()
    {
        cat.debug("Handling an OPEN action");
        OpenDSRCommand cmd = new OpenDSRCommand(model);
        cmd.execute();
    }

    public void handleClose()
    {
        cat.debug("Handling a CLOSE action");
        CloseDSRCommand cmd = new CloseDSRCommand(model);
        cmd.execute();
    }

    public void handleSave()
    {
        cat.debug("Handling a SAVE action");
    }

    public void handleCut()
    {
        cat.debug("Handling an CUT action");
    }

    public void handleCopy()
    {
        cat.debug("Handling an COPY action");
    }

    public void handlePaste()
    {
        cat.debug("Handling an PASTE action");
    }

    public void handlePlay()
    {
        cat.debug("Handling a PLAY action");
        model.play();
    }

    public void handlePause()
    {
        cat.debug("Handling a PAUSE action");
        model.pause();
    }

    public void handleViewStatus(boolean selected)
    {
        cat.debug("Handling an VIEW STATUS action");
    }

    public void handleAbout()
    {
        cat.debug("Handling an ABOUT action");
    }

    /**
     * Handle Events observed from the Model
     */
    //remeber, Controller is Observing the Model
    public void update(Observable o, Object arg)
    {
        try {
            if(o instanceof DSRModel) {
                PropertyChangeEvent pce = (PropertyChangeEvent) arg;

                String property = pce.getPropertyName();
                if("loaded".equals(property)) {
                    if( ( (Boolean) pce.getNewValue()).booleanValue()) {
                        this.handleModelLoadedEvent();
                    } else {
                        this.handleModelUnloadedEvent();
                    }

                } else if("playing".equals(property)) {
                    if( ( (Boolean) pce.getNewValue()).booleanValue()) {
                        this.handleModelPlayingEvent();
                    } else {
                        this.handleModelStoppedEvent();
                    }
                }
            }
        } catch(Exception e) {
            cat.error("ERROR while updating the Controller!", e);
        }

    }

    protected void handleModelLoadedEvent()
    {
        //update the gui
		manager.setEnabled("open-command", false);
		manager.setEnabled("close-command", true);
        manager.setEnabled("play-command", true);
        manager.setEnabled("pause-command", false);
    }

    protected void handleModelUnloadedEvent()
    {
        //update the gui
		manager.setEnabled("open-command", true);
		manager.setEnabled("close-command", false);
        manager.setEnabled("play-command", false);
        manager.setEnabled("pause-command", false);
    }

    protected void handleModelPlayingEvent()
    {
        //update the gui
        manager.setEnabled("play-command", false);
        manager.setEnabled("pause-command", true);
    }

    protected void handleModelStoppedEvent()
    {
        //update the gui
        manager.setEnabled("play-command", true);
        manager.setEnabled("pause-command", false);
    }

}
