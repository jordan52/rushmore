/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.model;

/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CountingModel extends Model
{
	int count;
	
	public CountingModel()
	{
		super();
		count = 0;
	}
	public void increment()
	{
		count++;
		setChanged();
		notifyObservers(new Integer(count));
	}
	public void decrement()
	{
		setChanged();
		notifyObservers(new Integer(count));
		count --;
	}
	public int getCount()
	{
		return count;
	}
}
