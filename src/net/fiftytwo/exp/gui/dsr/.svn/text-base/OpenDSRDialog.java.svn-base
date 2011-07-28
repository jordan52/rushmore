/*
 * Created on Feb 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.gui.dsr;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JDialog;
import net.fiftytwo.exp.appProps.AppProps;
import net.fiftytwo.exp.appProps.GuiProps;
import net.fiftytwo.exp.command.OpenDSRCommand;
import net.fiftytwo.exp.gui.dsr.OpenDSRDialog;
import org.apache.log4j.Category;
/**
 * @author jordan
 *
 * plenty of code and ideas were copied (Stolen) from JFileChooser
 */
public class OpenDSRDialog extends JComponent implements ListSelectListener
{
	protected OpenDSRCommand openDSRCommand = null;
	protected Frame parent = null;
	protected String dialogTitle = null;
	protected JDialog dialog = null;
	protected ActionListener actionListener = null;
	protected JButton approveButton;
	protected JButton cancelButton;
	protected ApproveSelectionAction approveSelectionAction;
	protected CancelSelectionAction cancelSelectionAction;
	protected JPanel buttonPanel;
	protected String hpSonosDirectory;
	protected HPSonosBrowser hpBrowser = null;
	protected String [] fileList = null;
	protected String dsrPath = null;
	protected int returnValue; //selected return option
	// ********************************
	// ***** OpenDSRDialog Return Values *****
	// ********************************
	/**
	 * Return value if cancel is chosen.
	 */
	public static final int CANCEL_OPTION = 1;
	/**
	 * Return value if approve (yes, ok) is chosen.
	 */
	public static final int APPROVE_OPTION = 0;
	/**
	 * Return value if an error occured.
	 */
	public static final int ERROR_OPTION = -1;
	private static Category cat = Category.getInstance("OpenDSRDialog");
	private static GuiProps guiProps = GuiProps.getInstance();
	private static AppProps appProps = AppProps.getInstance();
	public OpenDSRDialog(String title)
	{
		this.dialogTitle = title;
	}
	public void setHPSonosDBFile(File hpSonosDBFile)
	{
		
		hpBrowser = null;
		this.hpSonosDirectory = hpSonosDBFile.getParent();
		hpBrowser = new HPSonosBrowser(this.hpSonosDirectory);
		hpBrowser.addListSelectListener(this);
	}
	public String[] getFileList()
	{
		return this.fileList;
	}
	public String getDsrPath()
	{
		return this.dsrPath;
	}
	public void load(ListSelectEvent e)
	{
		cat.info("caught a listSelect event, path was " + e.getPath() + " filenames were ");
		fileList = e.getFileList();		
		dsrPath = e.getPath().getPath();  //e.getPath() returns a File, file.getPath() returns a string...
	}
	
	public int showDialog(Frame parent)
	{
		this.parent = parent;
		dialog = createDialog(parent);
		dialog.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				returnValue = CANCEL_OPTION;
			}
		});
		returnValue = ERROR_OPTION;
		dialog.show();
		dialog.dispose();
		dialog = null;
		return returnValue;
	}
	protected JDialog createDialog(Frame parent)
	{
		JDialog dialog = new JDialog(parent, this.dialogTitle, true);
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(hpBrowser, BorderLayout.CENTER);
		// buttons
		getButtonPanel().setLayout(new ButtonAreaLayout());
		approveButton = new JButton("Open");
		approveButton.addActionListener(getApproveSelectionAction());
		approveButton.setToolTipText("Open a DSR");
		getButtonPanel().add(approveButton);
		cancelButton = new JButton("Cancel");
		cancelButton.setToolTipText("Cancel");
		cancelButton.addActionListener(getCancelSelectionAction());
		getButtonPanel().add(cancelButton);
		
		contentPane.add(getButtonPanel(), BorderLayout.SOUTH);
		
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		return dialog;
	}
	
	protected OpenDSRCommand getOpenDSRCommand()
	{
		return openDSRCommand;
	}
	protected JPanel getButtonPanel()
	{
		if (buttonPanel == null)
		{
			buttonPanel = new JPanel();
		}
		return buttonPanel;
	}
	protected ApproveSelectionAction getApproveSelectionAction()
	{
		if (approveSelectionAction == null)
		{
			approveSelectionAction = new ApproveSelectionAction();
		}
		return approveSelectionAction;
	}
	protected CancelSelectionAction getCancelSelectionAction()
	{
		if (cancelSelectionAction == null)
		{
			cancelSelectionAction = new CancelSelectionAction();
		}
		return cancelSelectionAction;
	}
	/**
	 * Responds to an Open or Save request
	 */
	protected class ApproveSelectionAction extends AbstractAction
	{
		protected ApproveSelectionAction()
		{
			super("approveSelection");
			if(dialog != null)
			{
				dialog.setVisible(false);
			}
		}
		public void actionPerformed(ActionEvent e)
		{
			if( fileList != null)
			{
				//then we selected a study.
				returnValue = APPROVE_OPTION;
				if(dialog != null)
				{
					dialog.setVisible(false);
				}
			} else {
				JOptionPane.showMessageDialog(null,"Be sure to select a 3D Study!", "alert", JOptionPane.ERROR_MESSAGE); 
			}
		}
	}
	/**
	 * Responds to a cancel request.
	 */
	protected class CancelSelectionAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			//clear out the file list and return the CANCEL_OPTION
			fileList = null;
			returnValue = CANCEL_OPTION;
			if(dialog != null)
			{
				dialog.setVisible(false);
			}
		}
	}
	/**
	 *     STOLEN FROM SUN SOURCE
	 * 
	 * <code>ButtonAreaLayout</code> behaves in a similar manner to
	 * <code>FlowLayout</code>. It lays out all components from left to
	 * right, flushed right. The widths of all components will be set
	 * to the largest preferred size width.
	 */
	private static class ButtonAreaLayout implements LayoutManager
	{
		private int hGap = 5;
		private int topMargin = 17;
		public void addLayoutComponent(String string, Component comp)
		{}
		public void layoutContainer(Container container)
		{
			Component[] children = container.getComponents();
			if (children != null && children.length > 0)
			{
				int numChildren = children.length;
				Dimension[] sizes = new Dimension[numChildren];
				Insets insets = container.getInsets();
				int yLocation = insets.top + topMargin;
				int maxWidth = 0;
				for (int counter = 0; counter < numChildren; counter++)
				{
					sizes[counter] = children[counter].getPreferredSize();
					maxWidth = Math.max(maxWidth, sizes[counter].width);
				}
				int xLocation, xOffset;
				if (container.getComponentOrientation().isLeftToRight())
				{
					xLocation =
						container.getSize().width - insets.left - maxWidth;
					xOffset = hGap + maxWidth;
				} else
				{
					xLocation = insets.left;
					xOffset = - (hGap + maxWidth);
				}
				for (int counter = numChildren - 1; counter >= 0; counter--)
				{
					children[counter].setBounds(
						xLocation,
						yLocation,
						maxWidth,
						sizes[counter].height);
					xLocation -= xOffset;
				}
			}
		}
		public Dimension minimumLayoutSize(Container c)
		{
			if (c != null)
			{
				Component[] children = c.getComponents();
				if (children != null && children.length > 0)
				{
					int numChildren = children.length;
					int height = 0;
					Insets cInsets = c.getInsets();
					int extraHeight = topMargin + cInsets.top + cInsets.bottom;
					int extraWidth = cInsets.left + cInsets.right;
					int maxWidth = 0;
					for (int counter = 0; counter < numChildren; counter++)
					{
						Dimension aSize = children[counter].getPreferredSize();
						height = Math.max(height, aSize.height);
						maxWidth = Math.max(maxWidth, aSize.width);
					}
					return new Dimension(
						extraWidth
							+ numChildren * maxWidth
							+ (numChildren - 1) * hGap,
						extraHeight + height);
				}
			}
			return new Dimension(0, 0);
		}
		public Dimension preferredLayoutSize(Container c)
		{
			return minimumLayoutSize(c);
		}
		public void removeLayoutComponent(Component c)
		{}
	}
	private static void groupLabels(AlignedLabel[] group)
	{
		for (int i = 0; i < group.length; i++)
		{
			group[i].group = group;
		}
	}
	private class AlignedLabel extends JLabel
	{
		private AlignedLabel[] group;
		private int maxWidth = 0;
		AlignedLabel(String text)
		{
			super(text);
			setAlignmentX(JComponent.LEFT_ALIGNMENT);
		}
		public Dimension getPreferredSize()
		{
			Dimension d = super.getPreferredSize();
			// Align the width with all other labels in group.
			return new Dimension(getMaxWidth() + 11, d.height);
		}
		private int getMaxWidth()
		{
			if (maxWidth == 0 && group != null)
			{
				int max = 0;
				for (int i = 0; i < group.length; i++)
				{
					max = Math.max(group[i].getSuperPreferredWidth(), max);
				}
				for (int i = 0; i < group.length; i++)
				{
					group[i].maxWidth = max;
				}
			}
			return maxWidth;
		}
		private int getSuperPreferredWidth()
		{
			return super.getPreferredSize().width;
		}
	}
}