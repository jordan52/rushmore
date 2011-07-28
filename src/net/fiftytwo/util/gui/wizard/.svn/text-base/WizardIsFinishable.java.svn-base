/*
 * WizardIsFinishable.java
 *
 * Created on September 30, 2002, 11:29 AM
 */
package net.fiftytwo.util.gui.wizard;

/**
 * A WizardPage that implements this interface can be the final page
 * on a Wizard.  When a Finishable page is displayed on the wizard, the
 * "Finish" button is enabled.  A sensible wizard page would implement either
 * this interface, or HasNextPages, or both.  If your page implemented neither
 * then neither the "Next" nor "Finish" button would enable, so your page
 * would be a "dead end."
 *  @author Jonas Gifford
 *  @see WizardPage
 *  @see Wizard
 *  @see HasNextPages
 */
public interface WizardIsFinishable
{
    /**
     * Invoked by the Wizard when the used clicks on
     * the "Finish" button.
     *  @see Wizard
     */
    public abstract void onFinish ();
}
