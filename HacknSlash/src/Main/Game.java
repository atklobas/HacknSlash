package Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import mathematics.Vector2D;
import console.Console;
import console.Shell;
import external.graphics.Renderable;
import external.graphics.View;
import external.io.Command;
import external.resources.ResourceLoader;
import Map.World;
import actors.Actor;
import actors.Player;
import attacks.Attack;

public class Game {
	public static final Random rand=new Random();
	
	
	private Player player;
	
	View view;
	Shell shell;
	
	
	private int maxRate=20;
	
	LinkedList<Actor> actors=new LinkedList<Actor>();
	LinkedList<Actor> newActors=new LinkedList<Actor>();
	LinkedList<Attack> attacks=new LinkedList<Attack>();
	LinkedList<Attack> newAttacks=new LinkedList<Attack>();
	LinkedList<Command> commands=new LinkedList<Command>();
	LinkedList<Command> newCommands=new LinkedList<Command>();
	LinkedList<Renderable> rendered=new LinkedList<Renderable>();
	LinkedList<Renderable> newRendered=new LinkedList<Renderable>();
	
	World world;
	
	public Game(Shell c, View v, ResourceLoader loader){
		this.shell=c;
		this.view=v;
		player=new Player(000, 000);
		rendered.add(player);
		actors.add(player);
		
		world=new World(loader);
		rendered.add(world);
		
		 
		
		new Thread(new loop(),"gameloop").start();
	}
	
	
	
	
	public void gameLoop(){
		long previousTime=System.currentTimeMillis();
		long time=previousTime;
		int lag=0;
		while(true){//mostly timing code
			lag=(int)(System.currentTimeMillis()-previousTime);
			previousTime+=lag;
			input();
			update(lag);
			render();
			time=System.currentTimeMillis();
			try {
				int sleepTime=(int)(maxRate-(time-previousTime));
				if(sleepTime>0)
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				//NOTHING, just move along
			}
		}
	}
	
	private void input() {
		synchronized(this){
			commands.addAll(newCommands);
			newCommands.clear();
		}
		
		for(Iterator<Command> itr=commands.iterator();itr.hasNext();){
			Command c=itr.next();
			if(c.isActive()){
				c.execute(player);
			}else{
				itr.remove();
			}
		}
		
	}




	private void update(int time){
		synchronized(this){
			actors.addAll(newActors);
			newActors.clear();
			attacks.addAll(newAttacks);
			newAttacks.clear();
		}
		
		for(Iterator<Actor> itr=actors.iterator();itr.hasNext();){
			Actor a = itr.next();
			if(!a.isAlive()){
				itr.remove();
			}else{
				a.progress(time);
			}
		}
		/*
		 * for(Actor c:actors){
				if(!(a==c))
				a.collide(c);
				for(Wall w:walls){
					w.Collides(a);
					w.Collides(c);
				}
			}
		 */
		
			for(Iterator<Attack> itr=attacks.iterator();itr.hasNext();){
				Attack a=itr.next();
				if(a.exists()){
					a.progress(time);
				}else{
					itr.remove();
				}
			}
			
	}
	
	private void render(){
		synchronized(this){
			this.rendered.addAll(newRendered);
			this.newRendered.clear();
		}
		view.render(rendered,player.getX(),player.getY());
		view.display();
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
	
	
	
	
	
	
	
	
	
	public synchronized void addActor(Actor a){
		this.newActors.add(a);
		this.newRendered.add(a);
	}
	public synchronized void addAttack(Attack a){
		this.newAttacks.add(a);
		this.newRendered.add(a);
	}
	public synchronized void addCommand(Command c){
		
		this.newCommands.add(c);
	}
	
	private class loop implements Runnable{

		@Override
		public void run() {
			gameLoop();
			
		}
		
	}
}
