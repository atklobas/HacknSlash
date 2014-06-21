package actors;

import interaction.Event;
import interaction.Observable;
import interaction.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import external.graphics.Drawable;
import external.graphics.Renderable;
import external.graphics.Sprite;
import external.graphics.Sprited;
import attacks.Attack;
import attacks.Effect;
import Map.Map;
import mathematics.Vector;
import mathematics.Vector2D;
import graphics.Action;
import graphics.AnimatedSprite;
import graphics.AnimationPointer;
import graphics.Direction;

public class Actor implements Sprited, Observable{
	private ArrayList<Observer> observers=new ArrayList<Observer>();
	public void addObserver(Observer o){
		this.observers.add(o);
	}
	public void removeObserver(Observer o){
		this.observers.remove(o);
	}
	public void notify(Event e){
		//XXX possibly add Dedicated thread for all actors
		for(Observer o:this.observers){
			o.event(e);
		}
	}
	
	
	
	//AnimatedSprite sprite;
	AnimationPointer animation;
	//1/10 a meter per second
	private double standingSpeed=16*3/5000.;
	private double maxSpeed;
	double p=.01;
	double i=0;
	double d=.1;
	double integralMax=maxSpeed*20*1000;
	Vector2D velocity = new Vector2D();
	boolean standing = true;
	int hitpoints = 100000;
	int hpregen = 1;
	int manapoints = 100000;
	int manaregen = 3;
	int staminapoints = 100000;
	int staminaregen = 5;
	double radius=10;
	HashMap<String, Attack> attacks = new HashMap<String, Attack>();
	public static Random rand=new Random();
	private Vector2D setPoint;
	
	private Faction faction;
	private HashSet<Effect> effects = new HashSet<Effect>();
	private Vector2D pos;
	Vector2D facing = new Vector2D();
	
	Vector2D tally=new Vector2D();
	Vector2D previous=new Vector2D();
	
	
	public Actor(Vector2D pos, AnimationPointer animation, double radius){
		this.pos=pos;
		this.setPoint=this.getPos();
		this.animation=animation;
		this.radius=radius;
		this.setSpeed(.8);
	}
	
	
	
	public void setPid(double p, double i, double d){
		this.p=p;
		this.i=i;
		this.d=d;
	}
	
	public void addEffect(Effect e){
		synchronized(this){
			effects.add(e);
			e.start(this);
		}
	}
	
	public void removeEffect(Effect e){
		synchronized(this){
			effects.remove(e);
		}
	}
	
	public void progressEffects(int time){
		
		synchronized(this){
			
			for(Iterator<Effect> itr=effects.iterator();itr.hasNext();){
				Effect e=itr.next();
				e.progress(time);
				if(e.hasEnded()){
					itr.remove();
				}
			}
		}
	}
	
	public Sprite getSprite(){
		return this.animation.getSprite();
	}
	
	public void damage(int health,int mana, int stamina){
		hitpoints -= health; manapoints -= mana; staminapoints -= stamina;
	}
	
	
	public boolean isAlive(){
		return hitpoints>0;
	}
	
	public Faction getFaction(){
		return faction;
	}
	public void setFaction(Faction f){
		faction = f;
	}
	
	
	
	public void setSetPoint(Vector2D v){
		this.setPoint=v;
	}
	/**
	 * sets speed in meters per second, where 1 meter is 16*3 pixels
	 * @param d
	 */
	public void setSpeed(double d){
		
		this.maxSpeed=d*16*3/1000.;
	}
	public Vector2D getSetPoint(){
		return this.setPoint;
	}
	
	public boolean drawn(){
		return isAlive();
	}
	
	
	
	public Vector2D getPos(){
		return this.pos;
	}
	public void translate(Vector2D trans){
		if(trans.getX()<9999)
		this.pos=this.pos.add(trans);
	}
	
	
	private void animateSprite(){
		Direction d= this.getFacing(this.animation.avaliableDirections());
		//System.out.println(velocity.getLength());
		
		
		if(standing){
			animation.setAction(Action.STANDING, d);
		}else{
			animation.setAction(Action.MOVING, d);
		}
	}
	
	
	public void progress(int time){
		animateSprite();
		animation.progress(time);
		progressEffects(time);
		
		hitpoints += hpregen*time;
		manapoints += manaregen*time;
		staminapoints += staminaregen*time;
		move(time);
	
		
	}
	private void move(int time){
		this.previous=this.getPos();
		
		double maxSpeed=this.maxSpeed*time;
		
		//calculate pid values
		Vector2D proportion=setPoint.subtract(pos);
		this.tally=this.tally.add(proportion.scale(time));
		double intLength=tally.getLength();
		if(intLength>integralMax){this.tally=tally.scale(integralMax/(intLength));}
		Vector2D derivative=this.getPos().subtract(this.previous);
		
		//scale by pid 
		proportion=proportion.scale(p);
		derivative=derivative.scale(d);
		Vector2D integral=tally.scale(i);
		
		
		velocity=integral.add(proportion).subtract(derivative);
		
		Vector2D dir=velocity.scale(time);
		double length=dir.getLength();
		
		if(length>maxSpeed){
			dir=dir.scale(maxSpeed/(length));
			
		}
		
		
		if(velocity.getLength()>this.standingSpeed){
			facing=velocity.getUnitVector();
			standing=false;
		}else{
			standing=true;
		}
		
		this.translate(dir);
	}
	
	
	public int getX(){
		return (int)this.pos.getX();
	}
	public int getY(){
		return (int)this.pos.getY();
	}
	public void collide(Actor c) {
		Vector2D toMove=c.pos.subtract(this.pos);
		double length=toMove.getLength();
		double min=(this.getRadius()+c.getRadius())/2;
		if(length<min){
			this.pos=this.pos.add(toMove.scale((length-min)/length));
			c.pos=c.pos.subtract(toMove.scale((length-min)/length));
		}
		
	}
	public double getRadius(){
		return this.radius;
	}
	@Override
	public boolean rendered() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public double getScale() {
		return 3;
	}
	public Direction getFacing(Set<Direction> avaliable){
		Vector2D north = new Vector2D(0,-1);
		double angle = Math.toDegrees(facing.getAngle(north));
		if(facing.getX()<0){
			angle= 360-angle;
		}
		if(avaliable.size()==4){
			if(avaliable.contains(Direction.NORTH)){
				switch((int)Math.round(angle/90)){
				case 1: return Direction.EAST;
				case 2:return Direction.SOUTH;
				case 3:return Direction.WEST;
				default:return Direction.NORTH;
				
				}
			}else{
				switch((int)Math.round((angle-45)/90)){
				case 1:return Direction.SOUTH_EAST;
				case 2:return Direction.SOUTH_WEST;
				case 3:return Direction.NORTH_WEST;
				default:return Direction.NORTH_EAST;
				
				}
			}
		}else if(avaliable.size()==8){
			
		}
				
		return Direction.SOUTH;
	}

}
