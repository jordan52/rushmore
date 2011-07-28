/*
 * Created on Feb 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.gui.dsr;

import net.fiftytwo.exp.hpsonos.HPSonosDB;
import net.fiftytwo.exp.hpsonos.HPSonosEntry;
import java.awt.*;
import java.io.*;
import java.util.Vector;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
/**
 *
 * @author  jordan
 */
public class HPSonosBrowser extends JPanel
{
	private JTree tree;
	private DefaultTreeModel treeModel;
	private JTextArea fileDetailsTextArea;
	private String strPath;
	private Vector listSelListeners = new Vector();
	/** Creates a new instance of HPSonosBrowser */
	public HPSonosBrowser(String path)
	{
		strPath = path;
		HPSonosDB db = new HPSonosDB();
		try
		{
			db.open(path);
		} catch (IOException ioe)
		{
			System.out.println("can't open the file: " + path);
			ioe.printStackTrace();
		}
		//get the tree from HPSonosDB
		treeModel = new DefaultTreeModel(db.getRootNode());
		tree = new JTree(treeModel);
		//add the tree listener
		tree.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent event)
			{
				DefaultMutableTreeNode node =
					(DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				if (node != null)
				{
					HPSonosEntry selectedDir =
						(HPSonosEntry) node.getUserObject();
					fileDetailsTextArea.setText(selectedDir.getInfoString());
					String[] filenames = selectedDir.getFileNames();
					if (filenames != null)
					{
						ListSelectEvent ls =
							new ListSelectEvent(
								this,
								null,
								new File(selectedDir.getPath()),
								filenames);
						fireListSelectEvent(ls);
						//System.out.println("HPSonosBrowser just fired a List select event for " + selectedDir.getFileNames().length + " files.\n");
					}
				}
			}
		});
		createUI();
	}
	protected void createUI()
	{
		//Create Text Area
		fileDetailsTextArea = new JTextArea();
		fileDetailsTextArea.setEditable(false);
		//Create the panes
		JScrollPane treePane = new JScrollPane(tree);
		treePane.setPreferredSize(new Dimension(300, 400));
		JScrollPane fileDetailsPane = new JScrollPane(fileDetailsTextArea);
		fileDetailsPane.setPreferredSize(new Dimension(300, 200));
		//put the panes in a split pane
		JSplitPane splitPane =
			new JSplitPane(
				JSplitPane.VERTICAL_SPLIT,
				true,
				treePane,
				fileDetailsPane);
		splitPane.setDividerLocation(400);
		splitPane.setPreferredSize(new Dimension(600, 600));
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gb);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 5.0;
		gb.setConstraints(splitPane, c);
		add(splitPane);
		revalidate();
	}
	public void addListSelectListener(ListSelectListener il)
	{
		listSelListeners.addElement(il);
	}
	public void removeListSelectListener(ListSelectListener il)
	{
		if (!(listSelListeners.isEmpty()))
			listSelListeners.removeElement(il);
	}
	protected void fireListSelectEvent(ListSelectEvent lse)
	{
		if (listSelListeners.isEmpty())
			return;
		Vector lstn = new Vector();
		synchronized (this)
		{
			lstn = (Vector) listSelListeners.clone();
		}
		for (Enumeration e = lstn.elements(); e.hasMoreElements();)
		{
			((ListSelectListener) (e.nextElement())).load(lse);
		}
	}
}