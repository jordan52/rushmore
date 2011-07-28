/*
 * Wizard.java
 *
 * Created on September 30, 2002, 11:20 AM
 */

package net.fiftytwo.util.gui.wizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;
/**
 *  This code was borrowed (stolen) from the JAS site http://www-sldnt.slac.stanford.edu/jas/ then modified
 *
 *
 * The wizard is useful for getting user input on multiple pages.  The Wizard supports a
 * tree structure of WizardPages, and the path down that tree is determined by each node
 * when the "Next" button is clicked.  At a leaf (or at any node), the "Finish" button can be
 * clicked and the wizard closes.  The "Next" button is enabled when a WizardPage that implements
 * the interface HasNextPages is showing, the "Finish" button is enabled when a page that implements
 * the interface Finishable is showing, and the "Help" button is enabled when a page that implements
 * HasHelpPages is showing.
 *  @author Jonas Gifford
 *  @see WizardPage
 *  @see HasNextPages
 *  @see Finishable
 *  @see HasHelpPage
 */
public class Wizard extends JDialog implements ActionListener
{
    /**
     * Creates a new wizard that shows itself automatically.
     *  @param parent the parent JFrame
     *  @param title the title for the wizard dialog
     *  @firstPage the root of the tree structure and the page the wizard opens to
     */
    public Wizard (final JFrame parent, final String title, final WizardPage firstPage)
    {
	super (parent, title, true);
	m_frame = parent;
	m_contentPane = getContentPane ();
	m_contentPane.setLayout (new BorderLayout ());
	m_rootPane = getRootPane ();
	m_wizardPagePanel = new JPanel (m_layout = new CardLayout ());
	firstPage.addTo (m_wizardPagePanel, this, null); // recursive call
	final JPanel buttons = new JPanel (new FlowLayout ());
	buttons.add (m_cancel = new JButton ("Cancel"));        m_cancel.setMnemonic ('C');
	buttons.add (m_help   = new JButton ("Help"));        m_help.setMnemonic ('H');
	buttons.add (m_prev   = new JButton ("<< Previous")); m_prev.setMnemonic ('P');
	buttons.add (m_next   = new JButton ("Next >>"));        m_next.setMnemonic ('N');
	buttons.add (m_finish = new JButton ("Finish"));        m_finish.setMnemonic ('F');
	m_wizardPagePanel.setBorder (new javax.swing.border.EtchedBorder ());
	m_contentPane.add (BorderLayout.CENTER, m_wizardPagePanel);
	m_contentPane.add (BorderLayout.SOUTH, buttons);
	pack ();
	final Dimension mySize = getSize ();
	final Dimension parentSize = parent.getSize ();
	final Point position = parent.getLocation ();
	
	position.translate ((parentSize.width-mySize.width)/2,
	(parentSize.height-mySize.height)/2);
	
	setLocation (position);
	m_layout.first (m_wizardPagePanel);
	(m_currentPage = m_firstPage = firstPage).doEnable ();
	m_cancel.addActionListener (this);
	m_help.addActionListener (this);
	m_prev.addActionListener (this);
	m_next.addActionListener (this);
	m_finish.addActionListener (this);
	m_currentPageHasAmbiguousDefault = m_currentPage.isFinishable () && m_currentPage.hasNextPages ();
	//    setDefaultButton();
	m_help.setEnabled (firstPage instanceof WizardHasHelpPage);
	setResizable (false);
	enableEvents (java.awt.AWTEvent.WINDOW_EVENT_MASK);
	firstPage.beforeShowing ();
	//      Application app = Application.getApplication();
	//        if (app != null) app.modalDialogOpening(this);
//don't show until we explicitly tell it to.
	//	show ();
	//        if (app != null) app.modalDialogClosing(this);
    }
    public void setNextEnabled (final boolean b)
    {
	m_next.setEnabled (b && m_currentPage.hasNextPages ());
    }
    void setFinishEnabled (final boolean b)
    {
	m_finish.setEnabled (b && m_currentPage.isFinishable ());
    }
    void doPrevEnabled ()
    {
	m_prev.setEnabled (m_currentPage.getPrev () != null);
    }
    private void onHelp ()
    {
	setToWaitCursor ();
	WizardHasHelpPage h = ((WizardHasHelpPage) m_currentPage);
	//        Application.getApplication().showHelpTopic(h.getHelpTopic(),this);
	setToDefaultCursor ();
    }
    private void setToCurrentPage (final WizardPage page)
    {
	m_currentPage = page;
	
	m_currentPageHasAmbiguousDefault = m_currentPage.isFinishable () && m_currentPage.hasNextPages ();
	page.doEnable ();
	if (! m_currentPageHasAmbiguousDefault) setDefaultButton ();
	m_help.setEnabled (page instanceof WizardHasHelpPage);
	page.beforeShowing ();
	m_layout.show (m_wizardPagePanel, page.toString ());
    }
    public void onPrev ()
    {
	setToWaitCursor ();
	try
	{
	    setToCurrentPage (m_currentPage.getPrev ());
	}
	catch (Throwable t)
	{
	    handleError ("Error during wizard processing",t);
	}
	finally
	{
	    setToDefaultCursor ();
	}
    }
    public void onNext ()
    {
	setToWaitCursor ();
	try
	{
	    final WizardPage newPage = ((WizardHasNextPages) m_currentPage).getNext ();
	    
	    if (newPage != null)
	    {
		// Make sure this page was already added to the current (soon to be previous) page,
		// if not add it now.
		
		if (!m_wizardPagePanel.isAncestorOf (newPage))
		{
		    newPage.addTo (m_wizardPagePanel, this, m_currentPage);
		    newPage.invalidate ();
		    validate ();
		}
		
		setToCurrentPage (newPage);
	    }
	}
	catch (Throwable t)
	{
	    handleError ("Error during wizard processing",t);
	}
	finally
	{
	    setToDefaultCursor ();
	}
    }
    void setDefaultButton ()
    {
	if (m_currentPageHasAmbiguousDefault && m_next.isEnabled ())
	    m_rootPane.setDefaultButton (m_next);
	else if (m_currentPageHasAmbiguousDefault && m_finish.isEnabled ())
	    m_rootPane.setDefaultButton (m_finish);
	else
	    m_rootPane.setDefaultButton (m_currentPage.hasNextPages () ?  m_next : m_finish);
    }
    
    /**
     * Closes the wizard.  Invoked by <code>dispose()</code> in WizardPage.
     *  @see WizardPage
     *  @see WizardPage#dispose()
     */
    public void dispose ()
    {
	m_firstPage.clear ();
	/*
	 * References in it and all pages linked to
	 * it are set to null.
	 */
	WizardPage.pageNumber = 0;
	// next time the pages start at zero, so that the number doesn't get too big
	
	if (getCursor () == m_wait)
	    m_frame.setCursor (m_wait);
	super.dispose ();
    }
    private void onFinish ()
    {
	setToWaitCursor ();
	try
	{
	    ((WizardIsFinishable) m_currentPage).onFinish ();
	}
	catch (Throwable t)
	{
	    handleError ("Error during wizard processing",t);
	}
	finally
	{
	    setToDefaultCursor ();
	}
    }
    private void onCancel ()
    {
	setToWaitCursor ();
	try
	{
	    m_firstPage.doCancel ();
	    dispose ();
	}
	catch (Throwable t)
	{
	    handleError ("Error during wizard processing",t);
	}
	finally
	{
	    setToDefaultCursor ();
	}
    }
    void setToWaitCursor ()
    {
	// should set it on frame too, but doesn't work with modal dialog
	setCursor (m_wait);
    }
    void setToDefaultCursor ()
    {
	setCursor (m_default);
	m_frame.setCursor (m_default);
    }
    /** Public as an implementation side effect; do not call. */
    public void actionPerformed (final ActionEvent e)
    {
	final Object source = e.getSource ();
	if (source == m_cancel) onCancel ();
	else if (source == m_help)   onHelp ();
	else if (source == m_prev)   onPrev ();
	else if (source == m_next)   onNext ();
	else if (source == m_finish) onFinish ();
    }
    /** Public as an implementation side effect; do not call. */
    public void processWindowEvent (final WindowEvent e)
    {
	//        super.processWindowEvent(e);
	final int id = e.getID ();
	if (id == WindowEvent.WINDOW_CLOSING)
	    onCancel ();
	else if (id == WindowEvent.WINDOW_ACTIVATED)
	    setDefaultButton ();
    }
    protected void handleError (String message, Throwable t)
    {
       /*
      Application app = Application.getApplication();
      if (app != null) app.error("Error during wizard processing",t);
      else t.printStackTrace();
	*/
	t.printStackTrace ();
    }
    private final JFrame m_frame;
    private final Cursor m_default = Cursor.getDefaultCursor (), m_wait = new Cursor (Cursor.WAIT_CURSOR);
    private boolean m_currentPageHasAmbiguousDefault;
    final private JRootPane m_rootPane;
    final private java.awt.Container m_contentPane;
    final private JPanel m_wizardPagePanel;
    final private WizardPage m_firstPage;
    private WizardPage m_currentPage;
    final private JButton m_cancel, m_help, m_prev, m_next, m_finish;
    final private CardLayout m_layout;
}
