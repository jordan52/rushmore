package net.fiftytwo.exp.view;

import net.fiftytwo.exp.event.FrameUpdateEvent;
import net.fiftytwo.exp.event.FrameUpdateEventListener;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import javax.swing.*;
import javax.swing.JPanel;

import com.sun.media.jai.widget.DisplayJAI;

/**
  I created this using http://www.geoambiente.com.br/~rafael/JAI/display.html  Thanks dawg!
 */

public class ImageView extends JPanel implements FrameUpdateEventListener
{
    DisplayJAI displayJai;

    public ImageView()
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        //look how easy it would be to log frames per second. That is, override the paint
        //method.  Get the system time after each paint and comparing it to the first.  
        //Think of it as a performance monitor similar to the one found in the Java2D demo
        //displayJai = new DisplayJAI(){ public void paint(java.awt.Graphics g){super.paint(g);}};
        displayJai = new DisplayJAI()
        {
            public void paint(java.awt.Graphics g)
            {
                super.paint(g);
            }
        };
        add(new JScrollPane(displayJai));
    }

    public void frameUpdate(FrameUpdateEvent e)
    {
		this.set( e.getFrame());
    }

    public void set(RenderedImage image)
    {
    	if(image == null)
    	{
    		/* TODO:  Be sure to add some text to this image saying no image available! */
    		image = new BufferedImage(displayJai.getWidth(), displayJai.getHeight(), BufferedImage.TYPE_INT_RGB);
    	}
        displayJai.set(image);
        this.repaint();
    }
}