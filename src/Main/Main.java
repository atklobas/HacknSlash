package Main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import graphics.Drawable;
import graphics.GamePanel;

import javax.swing.JFrame;

import mathematics.Vector;
import mathematics.Vector2D;
import Map.Wall;
import actors.Actor;
import actors.Attack;
import actors.Faction;
import actors.NPC;
import actors.Player;
import actors.Zombie;


public class Main implements MouseListener, MouseMotionListener, KeyListener{
	public static final int width=800;
	public static final int height=600;
	int boxWidth=width;
	int boxHeight=height;
	static LinkedList<graphics.Drawable> drawn=new LinkedList<graphics.Drawable>();
	Vector2D playerCenter;
	public static Player player=new Player(50,50);
	Random rand=new Random();
	ArrayList<Wall> walls=new ArrayList<Wall>();
	ArrayList<Actor> actors=new ArrayList<Actor>();
	static LinkedList<Attack> attacks = new LinkedList<Attack>();
	private Vector2D getCenterPoint(){
		return player.getPos().add(new Vector2D(player.getWidth()/2,player.getHeight()/2));
	}
	public static HashMap<String, Faction> factions = new HashMap<String, Faction>();
	
	public Main() throws InterruptedException{
		JFrame f=new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel canvas=new GamePanel(width,height);
		f.addKeyListener(this);
		f.add(canvas);
		f.pack();
		f.setVisible(true);
		
		factions.put("ENEMY", new Faction("ENEMY"));
		factions.put("PLAYER", new Faction("PLAYER"));
		
		player.setFaction(factions.get("PLAYER"));
		player.addAttack("Punch", new Attack(new int[]{0, 10000, 0}, new int[]{200000, 0, 0}, 10, 10));
		
		
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
		
		Faction enemyFaction = new Faction("ENEMY");
		
		for(int i=0;i<40;i++){
			NPC generated=new Zombie(new Vector2D(rand.nextInt(boxWidth*2)-boxWidth,rand.nextInt(boxHeight*2)-boxHeight));
			//System.out.println("new "+generated.getFaction()+" created.");
			actors.add(generated);
		}
		
		drawn.addAll(actors);
		while(true){
			Thread.sleep(20);
			if(mouseHeld)player.setSetPoint(this.getCenterPoint().subtract(playerCenter).add(clickedPoint));
			for(int i=0; i<actors.size(); i++){
				if(!actors.get(i).isAlive()){
					actors.remove(i);
					i--;
				}
			}
			
			
			for(Actor a:actors){

				a.progress(20);
				
				for(Actor c:actors){
					
					if(!(a==c))
					a.collide(c);
					for(Wall w:walls){
						w.Collides(a);
						w.Collides(c);
					}
				}
			}
			for(Attack atk=attacks.poll();atk!=null;atk=attacks.poll()){

					for(Actor act:actors){
						atk.collide(act);
					}
				
			}
			
			for(Iterator<Drawable> d=drawn.iterator();d.hasNext();){
				if(!d.next().drawn()){
					d.remove();
				}
			}
			
			
			canvas.draw(drawn, this.getCenterPoint());
			f.repaint();
		}
	}
	
	
	public static void addAttack(Attack a){
		a.created=System.currentTimeMillis();
		drawn.add(a);
		attacks.add(a);
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyChar()=='a'){
			addAttack(player.attack("Punch"));
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
