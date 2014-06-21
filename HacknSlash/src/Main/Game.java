package Main;

import interaction.Event;
import interaction.LoadingEvent;
import interaction.Observable;
import interaction.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import mathematics.Vector2D;
import console.Console;
import console.Shell;
import external.graphics.Renderable;
import external.graphics.Sprite;
import external.graphics.View;
import external.io.Command;
import external.resources.ImageResource;
import external.resources.ResourceLoader;
import Map.World;
import actors.Actor;
import actors.Player;
import ai.AI;
import ai.Wanderer;
import attacks.Attack;

public class Game implements Observable{
	public static final Random rand=new Random();
	private HashSet<Observer> observers =new HashSet<Observer>();
	
	private Actor player;
	
	View view;
	Shell shell;
	World world;
	ResourceLoader loader;
	
	
	private int maxRate=20;
	
	private LinkedList<AI> ais=new LinkedList<AI>();
	private LinkedList<Actor> actors=new LinkedList<Actor>();
	private LinkedList<Actor> newActors=new LinkedList<Actor>();
	private LinkedList<Attack> attacks=new LinkedList<Attack>();
	private LinkedList<Attack> newAttacks=new LinkedList<Attack>();
	private LinkedList<Command> commands=new LinkedList<Command>();
	private LinkedList<Command> newCommands=new LinkedList<Command>();
	private LinkedList<Renderable> rendered=new LinkedList<Renderable>();
	private LinkedList<Renderable> newRendered=new LinkedList<Renderable>();
	
	
	public Game(Shell c, View v, ResourceLoader loader){
		this.shell=c;
		this.view=v;
		this.loader=loader;
	}
	
	public void initialize(){
		
		AI.setModel(this);
		this.fireEvent(new LoadingEvent(100, "Creating world"));
		
		world=new World(loader);
		rendered.add(world);
		
		
		AnimationLoader al=new AnimationLoader(loader);
		try {
			this.fireEvent(new LoadingEvent(200, "Loading Player Sprite"));
			al.addAntimation("PlayerSprite.txt");
			this.fireEvent(new LoadingEvent(600, "Loading Zombie Sprite"));
			al.addAntimation("ZombieSprite.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.fireEvent(new LoadingEvent(900, "Finalizing"));
		player=new Player(000, 000,al.getAnimationList("PlayerSprite.txt"),16*3);
		rendered.add(player);
		actors.add(player);
		//ais.add(new Wanderer(player));
		
			for(int i=0;i<10;i++){
			Actor zombie=new Actor(new Vector2D(rand.nextInt(500)-250,rand.nextInt(500)-250), al.getAnimationList("ZombieSprite.txt"),16*3);
			zombie.setSpeed(2);
			ais.add(new Wanderer(zombie));
			this.addActor(zombie);

		}/**/
		//System.out.println(System.currentTimeMillis()-time);
		/**/
		
		
		
		
			
			/*ImageResource ir=loader.LoadImageResource("basic sprites2.png");
			ir.setTransparent(0, 0);
			//player.addSprite(ir.createSprite(0, 0, 24, 34));
			Sprite[] sprites = new Sprite[4];
			sprites[0] = ir.createSprite(24, 0, 24, 32,12,16);
			sprites[1] = ir.createSprite(0, 0, 24, 32,12,16);
			sprites[2] = ir.createSprite(24, 0, 24, 32,12,16);
			sprites[3] = ir.createSprite(48, 0, 24, 32,12,16);
			player.addAnimation("upleft", Arrays.asList(sprites), 500);
			sprites[0] = ir.createSprite(24, 32, 24, 32,12,16);
			sprites[1] = ir.createSprite(0, 32, 24, 32,12,16);
			sprites[2] = ir.createSprite(24, 32, 24, 32,12,16);
			sprites[3] = ir.createSprite(48, 32, 24, 32,12,16);
			player.addAnimation("upright", Arrays.asList(sprites), 500);
			sprites[0] = ir.createSprite(24, 64, 24, 32,12,16);
			sprites[1] = ir.createSprite(0, 64, 24, 32,12,16);
			sprites[2] = ir.createSprite(24, 64, 24, 32,12,16);
			sprites[3] = ir.createSprite(48, 64, 24, 32,12,16);
			player.addAnimation("downright", Arrays.asList(sprites), 500);
			sprites[0] = ir.createSprite(24, 96, 24, 32,12,16);
			sprites[1] = ir.createSprite(0, 96, 24, 32,12,16);
			sprites[2] = ir.createSprite(24, 96, 24, 32,12,16);
			sprites[3] = ir.createSprite(48, 96, 24, 32,12,16);
			player.addAnimation("downleft", Arrays.asList(sprites), 500);
			sprites = new Sprite[1];
			sprites[0] = ir.createSprite(24, 0, 24, 32,12,16);
			player.addAnimation("upleftstanding", Arrays.asList(sprites), 500);
			sprites[0] = ir.createSprite(24, 32, 24, 32,12,16);
			player.addAnimation("uprightstanding", Arrays.asList(sprites), 500);
			sprites[0] = ir.createSprite(24, 64, 24, 32,12,16);
			player.addAnimation("downrightstanding", Arrays.asList(sprites), 500);
			sprites[0] = ir.createSprite(24, 96, 24, 32,12,16);
			player.addAnimation("downleftstanding", Arrays.asList(sprites), 500);
			/**/
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**/
		 
		
		new Thread(new loop(),"gameloop").start();
	}
	
	
	
	
	
	public void gameLoop(){
		long previousTime=System.currentTimeMillis();
		long time=previousTime;
		int lag=0;
		while(true){//mostly timing code
			lag=(int)(System.currentTimeMillis()-previousTime);
			previousTime+=lag;//System.currentTimeMillis();
			input();
			update(lag);
			render();
			time=System.currentTimeMillis();
			try {
				int sleepTime=(int)(maxRate-(time-previousTime));
				if(sleepTime>0){
				Thread.sleep(sleepTime);
				}
			} catch (InterruptedException e) {
				//NOTHING, just move along
			}
		}
	}
	
	private void input() {
		for(AI ai:ais){
			ai.think();
		}
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
		for(Iterator<Actor> itr=actors.iterator();itr.hasNext();){
			Actor a1=itr.next();
			for(Iterator<Actor> itr2=actors.iterator();itr2.hasNext();){
				Actor a2=itr2.next();
				if(a1!=a2)
				a1.collide(a2);
			}
		}
		
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
			if(this.newRendered.size()!=0){
				this.rendered.addAll(newRendered);
				this.newRendered.clear();
			}
		}
		Renderable map=this.rendered.getFirst();
		this.rendered.removeFirst();
		Collections.sort(this.rendered,new Comparator<Renderable>(){
			public int compare(Renderable arg0, Renderable arg1) {
				// TODO Auto-generated method stub
				return arg0.getY()-arg1.getY();
			}
			
		});
		this.rendered.addFirst(map);
		
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
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			gameLoop();
			
		}
		
	}

	
	
	private void fireEvent(Event e){
		for(Observer o: observers){
			o.event(e);
		}
	}
	
	
	@Override
	public void addObserver(Observer o) {
		observers.add(o);
		
	}




	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
		
	}
}
