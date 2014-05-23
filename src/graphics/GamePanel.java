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
	private ArrayList<MouseListener> mouseListeners=new ArrayList<MouseListener>();
	private ArrayList<MouseMotionListener> mouseMotionListener=new ArrayList<MouseMotionListener>();
	private ArrayList<KeyListener> keyListeners=new ArrayList<KeyListener>();
	private ArrayList<InternalFrame> frames=new ArrayList<InternalFrame>();
	MessagePane messagePane= new MessagePane(0,0,250,200,10);
	int width;
	int height;
	AffineTransform id=new AffineTransform();
	public GamePanel(int width,int height){
		super();
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
		super.addKeyListener(this);
		
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
		this.addKeyListener(messagePane);
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
		this.requestFocus();
		if(lastTime/1000<System.currentTimeMillis()/1000){
			lastTime=System.currentTimeMillis();
			messagePane.addMessage(""+(lastTime/1000));
			
		}
		
		
		for(InternalFrame f:frames){
			if(image!=null){
				
			Graphics toSend=image.createGraphics();
			toSend.translate(f.getX(), f.getY());
			toSend.setClip(0, 0, f.getWidth(), f.getHeight());
			f.paint(toSend);
			}
		}
		g.drawImage(image, 0, 0, this);
		g.drawImage(glassPane, 0, 0, this);
		
	}
	public void update(Graphics g){
		this.requestFocus();
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
	
	public void addMouseListener(MouseListener ml){
		this.mouseListeners.add(ml);
	}
	public void addMouseMotionListener(MouseMotionListener ml){
		this.mouseMotionListener.add(ml);
	}
	public void addKeyListener(KeyListener k){
		this.keyListeners.add(k);
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
	
	boolean resize=false;
	private InternalFrame selected=null;
	private int selectX=-1, selectY=-1;
	private int selectWidth, selectHeight;
	private void modifyFrame(int x, int y){
		if(selected!=null){
			if(resize){
				
				selected.resizeTo(x-selected.getX()+selectWidth-selectX, y-selected.getY()+selectHeight-selectY);
			}else{
				selected.moveTo(x-selectX, y-selectY);
				
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(InternalFrame f:frames){
			int contained=f.contains(e.getX(),e.getY());
			if(contained>0){
				if((contained&(InternalFrame.BOTTOM_PADDING|InternalFrame.RIGHT_PADDING))!=0){
					resize=true;
					selectWidth=f.getWidth();
					selectHeight=f.getHeight();
				}else{
					resize=false;
				}
				selected=f;
				selectX=e.getX()-f.getX();
				selectY=e.getY()-f.getY();
				e.consume();
			}
		}
		if(!e.isConsumed()){
			for(MouseListener ml:this.mouseListeners){
				ml.mousePressed(e);
			}
		}
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		this.modifyFrame(e.getX(), e.getY());
		selected=null;
		selectX=-1;
		selectY=-1;
		
		for(MouseListener ml:this.mouseListeners){
			ml.mouseReleased(e);
		}
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		for(KeyListener k:this.keyListeners){
			if(!e.isConsumed()){
				k.keyPressed(e);
			}
				
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		for(KeyListener k:this.keyListeners){
			if(!e.isConsumed())
				k.keyReleased(e);
		}
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		for(KeyListener k:this.keyListeners){
			if(!e.isConsumed())
				k.keyTyped(e);
		}
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		this.modifyFrame(e.getX(), e.getY());
		
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
