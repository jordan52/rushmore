/*
 * Created on Feb 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.fiftytwo.exp.view;
import java.beans.PropertyChangeEvent;
import java.util.Observer;
import java.util.Observable;
import javax.swing.*;
import javax.swing.JPanel;
import net.fiftytwo.exp.model.CineModel;
import org.apache.log4j.Category;
/**
 * @author jordan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CineModelTextView extends JPanel implements Observer
{
	private static Category cat = Category.getInstance("CineModelTextView");
	private JLabel playing;
	private JLabel playingVal;
	private JLabel numFrames;
	private JLabel numFramesVal;
	private JLabel firstFrame;
	private JLabel firstFrameVal;
	private JLabel lastFrame;
	private JLabel lastFrameVal;
	private JLabel frameDelay;
	private JLabel frameDelayVal;
	private JLabel goingUp;
	private JLabel goingUpVal;
	private JLabel swingPlay;
	private JLabel swingPlayVal;
	public CineModelTextView(String name)
	{
		super();
		init(name);
	}
	protected void init(String name)
	{
		this.setName(name);
		playing = new JLabel("playing: ");
		this.add(playing);
		playingVal = new JLabel("n/a");
		this.add(playingVal);
		numFrames = new JLabel("numFrames: ");
		this.add(numFrames);
		numFramesVal = new JLabel("n/a");
		this.add(numFramesVal);
		firstFrame = new JLabel(" firstFrame: ");
		this.add(firstFrame);
		firstFrameVal = new JLabel("n/a");
		this.add(firstFrameVal);
		lastFrame = new JLabel(" lastFrame: ");
		this.add(lastFrame);
		lastFrameVal = new JLabel("n/a");
		this.add(lastFrameVal);
		frameDelay = new JLabel(" frameDelay: ");
		this.add(frameDelay);
		frameDelayVal = new JLabel("n/a");
		this.add(frameDelayVal);
		goingUp = new JLabel(" goingUp: ");
		this.add(goingUp);
		goingUpVal = new JLabel("n/a");
		this.add(goingUpVal);
		swingPlay = new JLabel(" swingPlay: ");
		this.add(swingPlay);
		swingPlayVal = new JLabel("n/a");
		this.add(swingPlayVal);
	}
	public void update(Observable o, Object arg)
	{
		try 
		{
			CineModel cine = (CineModel) o; //assure its from a CineModel
			
			PropertyChangeEvent pce = (PropertyChangeEvent) arg;
			
			String property = pce.getPropertyName();
			
			if ("playing".equals(property))
			{
				playingVal.setText(((Boolean) pce.getNewValue()).toString());
			} else if ("numFrames".equals(property))
			{
				numFramesVal.setText(((Integer) pce.getNewValue()).toString());
			} else if ("firstFrame".equals(property))
			{
				firstFrameVal.setText(((Integer) pce.getNewValue()).toString());
			} else if ("lastFrame".equals(property))
			{
				lastFrameVal.setText(((Integer) pce.getNewValue()).toString());
			} else if ("frameDelay".equals(property))
			{
				frameDelayVal.setText(((Integer) pce.getNewValue()).toString());
			} else if ("goingUp".equals(property))
			{
				goingUpVal.setText(((Boolean) pce.getNewValue()).toString());
			} else if ("swingPlay".equals(property))
			{
				swingPlayVal.setText(((Boolean) pce.getNewValue()).toString());
			} else
			{
				cat.error(
						"Update received an unknown property update! PropertyName was "
						+ property);
			}
		} catch (Exception e) {
			cat.error("Update failed " + e);
		}
	}
}
