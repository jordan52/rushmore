package net.fiftytwo.exp.command;

import net.fiftytwo.exp.model.CineModel;

import org.apache.log4j.Category;

/**
 * This command is here to keep complexity and user interfaces as far from
 * the heartModel as possible!
 */

public class OpenAnalysisCommand extends Command
{
    private static Category cat = Category.getInstance("OpenAnalysisCommand");

    private CineModel heartModel;

    public OpenAnalysisCommand(CineModel heartModel)
    {
        this.heartModel = heartModel;
        initGUI();
        cat.debug("Creating the Command");
    }
    protected void initGUI()
    {
        cat.debug("initializing the GUI");
        //initialize any GUI necessary.
    }
    public void execute()
    {
        cat.debug("executing the command");
        /**@todo Implement this net.fiftytwo.exp.command.Command abstract method*/
        //this will handle all the steps necessary to open an analysis.
        askUser();
        //do some work
        //heartModel.setAnalysis(this.analysis blah blah blah)
        //or maybe:
        //heartModel.setHPSonos(this.HPSonosblah blah blah)
    }
    protected void askUser()
    {
        /**@todo Implement this net.fiftytwo.exp.command.Command askUser method*/
    }


}