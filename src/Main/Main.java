package Main;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import graphics.GamePanel;

import javax.swing.JFrame;

import mathematics.Vector;
import mathematics.Vector2D;
import Map.Wall;
import actors.Actor;
import actors.NPC;
import actors.Player;


public class Main implements MouseListener, MouseMotionListener{
	public static final int width=800;
	public static final int height=600;
	int boxWidth=width;
	int boxHeight=height;
	ArrayList<graphics.Drawable> drawn=new ArrayList<graphics.Drawable>();
	Vector2D playerCenter;
	public static Player player=new Player(50,50);
	Random rand=new Random();
	ArrayList<Wall> walls=new ArrayList<Wall>();
	ArrayList<Actor> actors=new ArrayList<Actor>();
	private Vector2D getCenterPoint(){
		return player.getPos().add(new Vector2D(player.getWidth()/2,player.getHeight()/2));
	}
	
	public Main() throws InterruptedException{
		JFrame f=new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel canvas=new GamePanel(width,height);
		f.add(canvas);
		f.pack();
		f.setVisible(true);
		playerCenter=new Vector2D(width/2,height/2);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		walls.add(new Wall(new Vector2D(-boxWidth,boxHeight),new Vector2D(boxWidth,boxHeight)));
		walls.add(new Wall(new Vector2D(boxWidth,boxHeight),new Vector2D(boxWidth,-boxHeight)));
		walls.add(new Wall(new Vector2D(boxWidth,-boxHeight),new Vector2D(-boxWidth,-boxHeight)));
		walls.add(new Wall(new Vector2D(-boxWidth,-boxHeight),new Vector2D(-boxWidth,boxHeight)));
		
		//walls.add(new Wall(new Vector2D(-width-50,-height-50),new Vector2D(width-50,height-50)));
		drawn.addAll(walls);
		
		actors.add(player);
		
		
		for(int i=0;i<100;i++){
			NPC generated=new NPC(new Vector2D(rand.nextInt(boxWidth*2)-boxWidth,rand.nextInt(boxHeight*2)-boxHeight));
			actors.add(generated);
		}
		
		drawn.addAll(actors);
		while(true){
			Thread.sleep(50);
			if(mouseHeld)player.setSetPoint(this.getCenterPoint().subtract(playerCenter).add(clickedPoint));
			for(Actor a:actors){
				a.progress(.05);
				
				for(Actor c:actors){
					
					if(!(a==c))
					a.collide(c);
					for(Wall w:walls){
						w.Collides(a);
						w.Collides(c);
					}
				}
			}
			canvas.draw(drawn, this.getCenterPoint());
			f.repaint();
		}
	}
	
	
	public static void main(String ags[]) throws InterruptedException{
		new Main();
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
	boolean mouseHeld=false;
	Vector2D clickedPoint;
	@Override
	public void mousePressed(MouseEvent e) {
		clickedPoint=new Vector2D(e.getX(),e.getY());
		mouseHeld=true;
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseHeld=false;
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		clickedPoint=new Vector2D(e.getX(),e.getY());
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
