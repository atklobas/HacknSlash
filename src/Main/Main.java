package Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import graphics.Drawable;
import graphics.GamePanel;

import javax.swing.JFrame;
import javax.swing.Timer;

import consoletools.Shell;
import consoletools.modify;
import consoletools.time;
import mathematics.Vector;
import mathematics.Vector2D;
import Map.Wall;
import actors.Actor;
import actors.Faction;
import actors.NPC;
import actors.Player;
import actors.Zombie;
import attacks.Attack;


public class Main implements MouseListener, MouseMotionListener, KeyListener, ActionListener{
	public static int delay=20;
	public static PrintStream out;
	public Timer t;
	
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
	GamePanel canvas;
	JFrame f;
	
	private Vector2D getCenterPoint(){
		return player.getPos().add(new Vector2D(player.getWidth()/2,player.getHeight()/2));
	}
	public static HashMap<String, Faction> factions = new HashMap<String, Faction>();
	
	public Main(){
		f=new JFrame();
		Attack.setModel(this);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas=new GamePanel(width,height);
		canvas.addKeyListener(this);
		f.add(canvas);
		f.pack();
		f.setVisible(true);
		canvas.requestFocus();
		Main.out=canvas.messagePane.out();
		Shell s=new Shell();
		
		
		factions.put("ENEMY", new Faction("ENEMY"));
		factions.put("PLAYER", new Faction("PLAYER"));
		
		player.setFaction(factions.get("PLAYER"));
		
		//player.addAttack("Punch", new Attack(new int[]{0, 1000, 0}, new int[]{8000, 0, 0}, 15, 15));
		
		s.addCommand("player", new modify(player));
		
		s.addCommand("time", new time(this));
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
		t=new Timer(delay, this);
		t.start();
	}
	private void progress(){
		if(mouseHeld&&mouseButton==1)player.setSetPoint(this.getCenterPoint().subtract(playerCenter).add(mouseLocation));
		if(mouseHeld&&mouseButton==3){
			this.addAttack(player.getAttack(Player.hotKeys.SECONDARY, this.getCenterPoint().subtract(playerCenter).add(new Vector2D(mouseLocation))));
			
		}
		for(int i=0; i<actors.size(); i++){
			if(!actors.get(i).isAlive()){
				actors.remove(i);
				i--;
			}
		}
		
		
		for(Actor a:actors){

			a.progress(delay);
			
			for(Actor c:actors){
				
				if(!(a==c))
				a.collide(c);
				for(Wall w:walls){
					w.Collides(a);
					w.Collides(c);
				}
			}
		}
		
		for(Iterator<Attack> itr=attacks.iterator();itr.hasNext();){
			Attack a=itr.next();
			if(a.drawn()){
				a.progress(delay);
			}else{
				itr.remove();
			}
			
		}

		synchronized(drawn){
			for(Iterator<Drawable> d=drawn.iterator();d.hasNext();){
				if(!d.next().drawn()){
					d.remove();
				}
			}
			canvas.draw(drawn, this.getCenterPoint());
			f.repaint();
		}
	}
	
	
	
	
	
	
	public void addAttack(Attack a){
		if(a!=null){
			synchronized(drawn){
				drawn.add(a);
			}
			attacks.add(a);
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
	Vector2D mouseLocation;
	int mouseButton;
	@Override
	public void mousePressed(MouseEvent e) {
		mouseLocation=clickedPoint=new Vector2D(e.getX(),e.getY());
		if(e.getButton()==MouseEvent.BUTTON3){
			this.mouseButton=3;
		}else{
			this.mouseButton=1;
			
			
		}
		mouseHeld=true;
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseHeld=false;
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseLocation=new Vector2D(e.getX(),e.getY());
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		/*if(arg0.getKeyChar()=='a'){
			addAttack(player.attack("Punch"));

		}*/
		
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
	public void actionPerformed(ActionEvent a) {
		this.progress();
		
	}
	public void pause(){
		t.stop();
	}
	public void unPause(){
		t.setDelay(delay);
		t.start();
		
	}
	public List<Actor> getSurrounding(Vector2D pos, double d) {
		List<Actor> ret=new ArrayList<Actor>();
		for(Actor a:actors){
			if(a.getPos().distance(pos)<=d){
				ret.add(a);
			}
		}
		return ret;
	}
	

}
