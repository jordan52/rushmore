/*
 * WizardTestPage.java
 *
 * Created on September 30, 2002, 12:16 PM
 */

package net.fiftytwo.util.gui.wizard;

import java.awt.GridLayout;
import javax.swing.*;
/**
 *
 * @author  jordan
 */
public class WizardTestPage extends WizardPage implements WizardHasNextPages
{
    WizardPage [] nextPages;
    
    /** Creates a new instance of WizardTestPage */
    public WizardTestPage ()
    {
	super (new GridLayout (1,1));
	this.add (new JButton ("Test Page"));
	
	nextPages = new WizardPage[1];
	nextPages[0] = new WizardTestPageFinal();
	
    }
    
    /** This method is called when the user clicks on the "Next" button.  You must
     * return a page that was in the array returned by <code>getNextWizardPages</code>.
     *  @return a WizardPage that was included in the array returned by <code>getNextWizardPages</code>
     */
    public WizardPage getNext ()
    {
	return nextPages[0];
    }
    
    /** Returns an array of all possible next pages.  There could, of course, be just
     * one element in this array.  This method will be called before the wizard
     * shows on the screen.  The wizard needs to know all of the possible pages so that
     * it can size itself to fit them all.
     *  @return an array of all possible subsequent pages
     */
    public WizardPage[] getNextWizardPages ()
    {
	return nextPages;
    }
    
}
