/*
 * Dialog.java
 *
 * Created on October 1, 2002, 11:11 PM
 */

package net.fiftytwo.util.gui.dialog;

import net.fiftytwo.util.gui.State;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

/**
 * code stolen from http://www-sldnt.slac.stanford.edu/servlet/jascvs/show/Base/jas/util/JASDialog.java/1.6
 * Dialog extends the built in java dialog to provide a number of extra features
 * <ul>
 * <li>Automatic placement relative to parent frame
 * <li>OK, Cancel, Apply, and Help buttons at the bottom of the dialog box
 * <li>Automatic disposal of the dialog when the user hits OK, Cancel or closes
 *     the dialog using the window manager
 * </ul>
 * To make components visible on this dialog, add them to the content pane.
 *  @see Dialog#getContentPane()
 *  @author Tony Johnson
 */

public class Dialog extends JDialog implements MouseListener, DocumentListener
{
    /** Include this in your flags to include an OK button. */
    public final static int OK_BUTTON = 1;

    /** Include this in your flags to include an Apply button. */
    public final static int APPLY_BUTTON = 2;

    /** Include this in your flags to include a Cancel button. */
    public final static int CANCEL_BUTTON = 4;

    /** Include this in your flags to include a Help button. */
    public final static int HELP_BUTTON = 8;

   public static Dialog create(Frame frame, String title)
    {
        return create(frame, title, true, OK_BUTTON | CANCEL_BUTTON);
    }
   public static Dialog create(Frame frame, String title, boolean modal)
    {
        return create(frame, title, modal, OK_BUTTON | CANCEL_BUTTON);
    }
   public static Dialog create(Component c,String t,boolean modal,int flags)
   {
      Window w = (Window) SwingUtilities.getAncestorOfClass(Window.class,c);
      if      (w instanceof Frame ) return new Dialog((Frame) w,t,modal,flags);
      else if (w instanceof Dialog) return new Dialog((Dialog) w,t,modal,flags);
      else throw new RuntimeException("No dialog parent found");
   }
   
    /**
     * Creates a modal dialog with OK and Cancel buttons.
     *  @param frame the parent frame
     *  @param title the title for the dialog box
     */
    public Dialog(Frame frame, String title)
    {
        this(frame, title, true, OK_BUTTON | CANCEL_BUTTON);
    }

    /**
     * Creates a dialog with OK and Cancel buttons.
     *  @param frame the parent frame
     *  @param title the title for the dialog box
     *  @param modal whether the dialog box is modal
     */
    public Dialog(Frame frame, String title, boolean modal)
    {
        this(frame, title, modal, OK_BUTTON | CANCEL_BUTTON);
    }

    /**
     * Creates a dialog box with any combination of buttons.  Use bitwise
     * logic to specify the appropriate flags in the <code>flags</code>
     * parameter.  For example,<br>
     * <code>Dialog.OK_BUTTON | Dialog.CANCEL_BUTTON | Dialog.HELP_BUTTON</code><br>
     * when passed as the <code>flags</code> parameter will create a button
     * that has an OK button, a Cancel button, and a Help button.
     *  @param dlg the parent Dialog
     *  @param title the title for the dialog box
     *  @param modal whether the dialog box is modal
     *  @param flags option flags
     */
    public Dialog(Frame f,String t,boolean modal,int flags)
    {
        super(f,t,modal);
      init(f,flags);
   }
    /**
     * Creates a modal dialog with OK and Cancel buttons.
     *  @param frame the parent frame
     *  @param title the title for the dialog box
     */
    public Dialog(Dialog dlg, String title)
    {
        this(dlg, title, true, OK_BUTTON | CANCEL_BUTTON);
    }

    /**
     * Creates a dialog with OK and Cancel buttons.
     *  @param dlg the parent Dialog
     *  @param title the title for the dialog box
     *  @param modal whether the dialog box is modal
     */
    public Dialog(Dialog dlg, String title, boolean modal)
    {
        this(dlg, title, modal, OK_BUTTON | CANCEL_BUTTON);
    }

    /**
     * Creates a dialog box with any combination of buttons.  Use bitwise
     * logic to specify the appropriate flags in the <code>flags</code>
     * parameter.  For example,<br>
     * <code>Dialog.OK_BUTTON | Dialog.CANCEL_BUTTON | Dialog.HELP_BUTTON</code><br>
     * when passed as the <code>flags</code> parameter will create a button
     * that has an OK button, a Cancel button, and a Help button.
     *  @param dlg the parent Dialog
     *  @param title the title for the dialog box
     *  @param modal whether the dialog box is modal
     *  @param flags option flags
     */
    public Dialog(Dialog dlg,String t,boolean modal,int flags)
    {
        super(dlg,t,modal);
      init(dlg,flags);
   }
   private void init(Window w, int flags)
   {
        m_window = w;
        m_flags = flags; 
        
        ActionListener l = new ActionEventHandler();

        m_ok     = new JButton("OK");
        m_ok.addActionListener(l);
        m_ok.setMnemonic('O');

        m_apply  = new JButton("Apply");
        m_apply.setMnemonic('A');
        m_apply.addActionListener(l);

        m_cancel = new JButton("Cancel");
        m_cancel.setMnemonic('C');
        m_cancel.addActionListener(l);
        
        m_help = new JButton("Help");
        m_help.setMnemonic('H');
        m_help.addActionListener(l);

        m_buttonPanel = new JPanel();
        super.getContentPane().add(m_buttonPanel,BorderLayout.SOUTH);        
        
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        super.getContentPane().add(p,BorderLayout.CENTER);
        m_contentPane = p;
    }

    /**
     * Causes a default pack(), but also places the dialog in the center
     * of the parent frame.
     *  @see java.awt.Window#pack()
     */
    public void pack()
    {
        defaultPack();
        setLocationRelativeTo(m_window);
    }

    /** Causes default pack; does not move dialog. */
    public void defaultPack()
    {
        if (m_buttonPanel.getComponentCount() == 0)
        {
            if ((m_flags & OK_BUTTON)     !=0) m_buttonPanel.add(m_ok);
            if ((m_flags & APPLY_BUTTON)  !=0) m_buttonPanel.add(m_apply);
            if ((m_flags & CANCEL_BUTTON) !=0) m_buttonPanel.add(m_cancel);
            if ((m_flags & HELP_BUTTON) !=0)   m_buttonPanel.add(m_help);
        }
        super.pack();
    }

    /** This method is public as an implementation side effect; do not call or override. */
    public void processEvent(AWTEvent e) 
    {
        if      (e.getID() == WindowEvent.WINDOW_CLOSING) onCancel();
        super.processEvent(e);
    }

    /**
     * Forces a modal display of the dialog box
     *  @return true if the OK button was pushed, otherwise false
     */
    public boolean doModal()
    {
        m_result = false;
        setModal(true);
        show();
        return m_result;
    }
    
    public void show()
    {
        callEnable();
 //       if (isModal() && app != null) app.modalDialogOpening(this);
        super.show();
 //       if (isModal() && app != null) app.modalDialogClosing(this);
    }

    /**
     * Set the label on the OK button.  Allows for
     * customized button labels.
     */
    public void setOKLabel(String label)
    {
        m_ok.setText(label);
    }

    /**
     * Sets the mnnemonic (or keyboard shortcut) for the
     * OK button.  If you have changed the label
     * using <code>setOKLabel(String)</code> the default
     * mnemonic ('O') may not be appropriate, so use
     * this method to set a better one.
     *  @param mnemonic the new key shortcut to use
     *  @see Dialog#setOKLabel(String)
     */
    public void setOKMnemonic(char mnemonic)
    {
        m_ok.setMnemonic(mnemonic);
    }

    /**
     * Set the label on the Cancel button.  Allows for
     * customized button labels.
     */
    public void setCancelLabel(String label)
    {
        m_cancel.setText(label);
    }

    /**
     * Sets the mnnemonic (or keyboard shortcut) for the
     * Cancel button.  If you have changed the label
     * using <code>setCancelLabel(String)</code> the default
     * mnemonic ('C') may not be appropriate, so use
     * this method to set a better one.
     *  @param mnemonic the new key shortcut to use
     *  @see Dialog#setCancelLabel(String)
     */
    public void setCancelMnemonic(char mnemonic)
    {
        m_cancel.setMnemonic(mnemonic);
    }

    /**
     * Set the label on the Apply button.  Allows for
     * customized button labels.
     */
    public void setApplyLabel(String label)
    {
        m_apply.setText(label);
    }

    /**
     * Sets the mnnemonic (or keyboard shortcut) for the
     * Apply button.  If you have changed the label
     * using <code>setApplyLabel(String)</code> the default
     * mnemonic ('A') may not be appropriate, so use
     * this method to set a better one.
     *  @param mnemonic the new key shortcut to use
     *  @see Dialog#setApplyLabel(String)
     */
    public void setApplyMnemonic(char mnemonic)
    {
        m_apply.setMnemonic(mnemonic);
    }

    /**
     * Called when the OK button is pushed. Override to provide customized
     * functionality for derived dialog boxes. 
     */
    protected void onOK()
    {
        m_result = true;
        dispose();
    }

    /**
     * Called when the Cancel button is pushed. Override to provide customized
     * functionality for derived dialog boxes. 
     */
    protected void onCancel()
    {
        dispose();
    }

    /**
     * Called when the Apply button is pushed. Override to provide customized
     * functionality for derived dialog boxes. 
     */
    protected void onApply()
    {
    }

    /** Forces the dialog to re-evaluate button enabling. */
    protected void callEnable()
    {
        if (m_ok    != null)
        {
            enableOK(new ButtonState(m_ok));
            getRootPane().setDefaultButton(m_ok);
        }
        if (m_apply != null) enableApply(new ButtonState(m_apply));
        if (m_help  != null) enableHelp(new ButtonState(m_help));
    }

    /**
     * Override to customize when OK is enabled.  OK is enabled by
     * default.
     */
    protected void enableOK(State state)
    {
    }
    
    /**
     * Override to customize when Help is enabled.   By default, Help
     * is enabled if both a help book and help topic have been set.
     *  @see Dialog#setHelpTopic(String)
     */
    protected void enableHelp(State state)
    {
        state.setEnabled(m_helpTopic != null);
    }

    /**
     * Override to customize when Apply is enabled.  By default,
     * Apply is disenabled.
     */
    protected void enableApply(State state)
    {
        state.setEnabled(false);
    }

    /**
     * Returns the content pane for the Dialog, which is the
     * panel (not including the buttons) where components can be
     * added.  Add components to this container to have them appear
     * in the dialog.
     */
    public Container getContentPane()
    {
        return m_contentPane;
    }

    /**
     * Sets the content pane.  The buttons are not members of this
     * content pane.
     */
    public void setContentPane(Container c)
    {
        m_contentPane = c;
        super.getContentPane().add(m_contentPane,BorderLayout.CENTER);
    }

    /**
     * Sets the name for the help topic that the Help button opens.
     * Note: This is now being used to set the XML target for JavaHelp.
     */
    public final void setHelpTopic(String topic)
    {
        m_flags |= HELP_BUTTON;
        m_helpTopic = topic;
    }
    private void onHelp()
    {
//        Application.getApplication().showHelpTopic(m_helpTopic,this);
    }
    private class ActionEventHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            if      (source == m_ok) onOK();
            else if (source == m_apply) onApply();
            else if (source == m_cancel) onCancel();
            else if (source == m_help) onHelp();
        }
    }
    

    
    private class ButtonState implements State
    {
        ButtonState(JButton b)
        {
            m_button = b;
        }
        public void setEnabled(boolean state)
        {
            m_button.setEnabled(state);
        }
        private JButton m_button;
    }

    /**
     * Does nothing by default.  To use this class as a MouseListener, 
     * override only the method(s) you need.
     */
    public void mouseClicked(MouseEvent e){}
    /**
     * Does nothing by default.  To use this class as a MouseListener, 
     * override only the method(s) you need.
     */
    public void mousePressed(MouseEvent e){}
    /**
     * Does nothing by default.  To use this class as a MouseListener, 
     * override only the method(s) you need.
     */
    public void mouseReleased(MouseEvent e){}
    /**
     * Does nothing by default.  To use this class as a MouseListener, 
     * override only the method(s) you need.
     */
    public void mouseEntered(MouseEvent e){}
    /**
     * Does nothing by default.  To use this class as a MouseListener, 
     * override only the method(s) you need.
     */
    public void mouseExited(MouseEvent e){}

    // Implements DocumentListener as a convenience to subclasses so
    // that they only need to overload the desired functions 

    /**
     * Calls <code>callEnable()</code> by default.  To use this class
     * as a DocumentListener, override only the method(s) you need.
     *  @see Dialog#callEnable()
     */
    public void insertUpdate(DocumentEvent e){callEnable();}
    /**
     * Calls <code>callEnable()</code> by default.  To use this class
     * as a DocumentListener, override only the method(s) you need.
     *  @see Dialog#callEnable()
     */
    public void removeUpdate(DocumentEvent e){callEnable();}
    /**
     * Calls <code>callEnable()</code> by default.  To use this class
     * as a DocumentListener, override only the method(s) you need.
     *  @see Dialog#callEnable()
     */
    public void changedUpdate(DocumentEvent e){callEnable();}

  //  private Application app = Application.getApplication();
    private String m_helpTopic = null;
    private int m_flags;
    private JPanel m_buttonPanel;
    private JButton m_ok;
    private JButton m_cancel;
    private JButton m_apply;
    private JButton m_help;
    private boolean m_result;
    private Window m_window;
    private Container m_contentPane;
}
