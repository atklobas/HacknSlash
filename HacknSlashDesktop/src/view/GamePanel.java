package view;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.VolatileImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import resources.PC_Sprite;
import external.graphics.Drawable;
import external.graphics.Pen;
import external.graphics.Renderable;
import external.graphics.Sprite;
import external.graphics.Sprited;
import external.graphics.View;
import mathematics.Vector;

public class GamePanel extends Component implements View, MouseListener, MouseMotionListener, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2211479314105403140L;
	private ArrayList<MouseListener> mouseListeners=new ArrayList<MouseListener>();
	private ArrayList<MouseMotionListener> mouseMotionListener=new ArrayList<MouseMotionListener>();
	private ArrayList<KeyListener> keyListeners=new ArrayList<KeyListener>();
	private ArrayList<InternalFrame> frames=new ArrayList<InternalFrame>();
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
	}
	VolatileImage image;
	VolatileImage glassPane;
	Graphics2D g;
	Graphics2D gg;
	Pen pen;
	
	
	public void addFrame(InternalFrame frame){
		frames.add(frame);
	}
	
	public void removeFrame(InternalFrame frame){
		frames.remove(frame);
	}
	
	
	
	
	public void paint(Graphics g){
		this.requestFocus();
		
		
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


	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
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


	
	public void mouseReleased(MouseEvent e) {
		this.modifyFrame(e.getX(), e.getY());
		selected=null;
		selectX=-1;
		selectY=-1;
		
		for(MouseListener ml:this.mouseListeners){
			ml.mouseReleased(e);
		}
		
	}


	
	public void keyPressed(KeyEvent e) {
		for(KeyListener k:this.keyListeners){
			if(!e.isConsumed()){
				k.keyPressed(e);
			}
				
		}
		
	}


	
	public void keyReleased(KeyEvent e) {
		for(KeyListener k:this.keyListeners){
			if(!e.isConsumed())
				k.keyReleased(e);
		}
		
	}


	
	public void keyTyped(KeyEvent e) {
		for(KeyListener k:this.keyListeners){
			if(!e.isConsumed())
				k.keyTyped(e);
		}
		
	}


	
	public void mouseDragged(MouseEvent e) {
		this.modifyFrame(e.getX(), e.getY());
		if(this.selected!=null)e.consume();
		if(!e.isConsumed()){
			for(MouseMotionListener ml:this.mouseMotionListener){
				ml.mouseDragged(e);
			}
		}
		
	}


	
	public void mouseMoved(MouseEvent e) {
		if(!e.isConsumed()){
			for(MouseMotionListener ml:this.mouseMotionListener){
				ml.mouseDragged(e);
			}
		}
		
	}
	public void clear(Graphics2D g2){
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g2.fillRect(0,0,width,height);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	}
	

	public void render(List<Renderable> rendered, int x, int y) {
		if(g==null){
			image=this.createVolatileImage(width, height);
			g=image.createGraphics();
			pen=new PC_Pen(g);
		}
		if(g!=null){
			g.setTransform(id);
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, width, height);
			clear(gg);
			gg.setColor(Color.BLACK);
			gg.drawString("time: "+System.currentTimeMillis(), 0, 12);
			g.translate(-x+width/2, -y+height/2);
			
			//int xx=(int)center.getElement(0)-width/2;
			//int yy=(int)center.getElement(1)-height/2;
			//g.translate(-centerX, -centerY);
			for(Renderable r:rendered){
				if(r instanceof Sprited){
					Sprite s=((Sprited) r).getSprite();
					if(s instanceof PC_Sprite){
						((PC_Sprite) s).draw(g, r.getX()-x, r.getY()-y);
					}
				}else if(r instanceof Drawable){
					((Drawable) r).draw(pen,x-width/2,y-height/2,width,height);
				}
			}
		}
		this.repaint();
		
	}
		
}
