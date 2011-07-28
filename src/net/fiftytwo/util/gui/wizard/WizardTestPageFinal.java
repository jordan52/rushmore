/*
 * WizardTestPage.java
 *
 * Created on September 30, 2002, 11:56 AM
 */

package net.fiftytwo.util.gui.wizard;

import java.awt.GridLayout;

import javax.swing.*;
/**
 *
 * @author  jordan
 */
public class WizardTestPageFinal extends WizardPage implements WizardIsFinishable
{
    
    /** Creates a new instance of WizardTestPage */
    public WizardTestPageFinal ()
    {
	super (new GridLayout (1,1));
	this.add (new JButton ("This is a button"));
    }
    
    /** Invoked by the Wizard when the used clicks on
     * the "Finish" button.
     *  @see Wizard
     */
    public void onFinish ()
    {
    }
    
}
