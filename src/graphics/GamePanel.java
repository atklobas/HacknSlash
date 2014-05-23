package graphics;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import mathematics.Vector;

public class GamePanel extends Component implements MouseListener, MouseMotionListener, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2211479314105403140L;
	private ArrayList<InternalFrame> frames=new ArrayList<InternalFrame>();
	MessagePane messagePane= new MessagePane(0,0,250,200,10);
	int width;
	int height;
	AffineTransform id=new AffineTransform();
	public GamePanel(int width,int height){
		super();
		this.width=width;
		this.height=height;
		glassPane=this.createClearVolatileImage(width, height);
		gg=glassPane.createGraphics();
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.frames.add(messagePane);
		messagePane.addMessage(""+1);
		messagePane.addMessage(""+2);
		messagePane.addMessage(""+3);
	}
	VolatileImage image;
	VolatileImage glassPane;
	Graphics2D g;
	Graphics2D gg;
	
	public void draw(List<Drawable> todraw,Vector center){
		if(g==null){
			image=this.createVolatileImage(width, height);
			g=image.createGraphics();
			
		}
		if(g!=null){
			g.setTransform(id);
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, width, height);
			int centerX=(int)center.getElement(0)-width/2;
			int centerY=(int)center.getElement(1)-height/2;
			g.translate(-centerX, -centerY);
			for(Drawable d:todraw){
				d.draw(g);		
			}
		}
	}
	
	long lastTime=System.currentTimeMillis()+5000;
	
	public void paint(Graphics g){
		if(lastTime/1000<System.currentTimeMillis()/1000){
			lastTime=System.currentTimeMillis();
			messagePane.addMessage(""+(lastTime/1000));
			
		}
		
		
		for(InternalFrame f:frames){
			if(image!=null){
				
			Graphics toSend=image.createGraphics();
			toSend.setClip(f.getX(), f.getY(), f.getWidth(), f.getHeight());
			f.paint(toSend);
			}
		}
		g.drawImage(image, 0, 0, this);
		g.drawImage(glassPane, 0, 0, this);
		
	}
	public void update(Graphics g){
		this.paint(g);
	}
	public VolatileImage createClearVolatileImage(int width, int height) {
		VolatileImage ret= GraphicsEnvironment.getLocalGraphicsEnvironment().
	        getDefaultScreenDevice().getDefaultConfiguration().
	        createCompatibleVolatileImage(width, height, Transparency.TRANSLUCENT);
		 Graphics2D g = ret.createGraphics();
		    g.setComposite(AlphaComposite.DstOut);
		    g.fillRect(0, 0, ret.getWidth(), ret.getHeight());
		    g.dispose();
		return ret;
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
