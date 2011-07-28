/*
 * HPSonosEntry.java
 *
 * Created on August 14, 2002, 12:11 PM
 */
package net.fiftytwo.exp.hpsonos;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 *
 * @author  jordan
 */
public interface HPSonosEntry
{
    /**
     * this returns the physical path of where the files will be located.
     */
    public String getPath();
    
    /**
     * This returns a list of filenames associated with this object. Return null
     * if this entry contains no files.
     */
    public String[] getFileNames();
    
    /**
     * This returns the descriptive string of the contents of this sonos entry.
     * It will often be placed in the tree, so make it short and informative.
     */
    public String toString();
    
    /**
     * This returns a long, more informative string detailing the contents of this
     * entry.
     */
    public String getInfoString();
    
    /**
     *  Returns the DefaultMutableTree node containing the representation of this
     *  object for insertion into a TreeModel
     */
    public DefaultMutableTreeNode getNodes();
}
