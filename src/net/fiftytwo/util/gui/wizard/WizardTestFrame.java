/*
 * WizardTestFrame.java
 *
 * Created on September 30, 2002, 11:48 AM
 */
package net.fiftytwo.util.gui.wizard;

/**
 *
 * @author  jordan
 */
public class WizardTestFrame extends javax.swing.JFrame
{
    
    private Wizard wizard;
    
    /** Creates new form WizardTestFrame */
    public WizardTestFrame ()
    {
	initComponents ();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        buttonWizard = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wizard Tester");
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 400, 400));
        setName("frameMain");
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                exitForm(evt);
            }
        });

        buttonWizard.setText("OpenWizard");
        buttonWizard.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                OpenWizardActionPerformed(evt);
            }
        });

        getContentPane().add(buttonWizard, java.awt.BorderLayout.NORTH);

        pack();
    }//GEN-END:initComponents
    
    private void OpenWizardActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_OpenWizardActionPerformed
    {//GEN-HEADEREND:event_OpenWizardActionPerformed
	// Add your handling code here:
	wizard = new WizardTest (this, new WizardTestPage ());
	
    }//GEN-LAST:event_OpenWizardActionPerformed
    
    /** Exit the Application */
    private void exitForm (java.awt.event.WindowEvent evt)
    {//GEN-FIRST:event_exitForm
	System.exit (0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main (String args[])
    {
	new WizardTestFrame ().show ();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonWizard;
    // End of variables declaration//GEN-END:variables
    
}
