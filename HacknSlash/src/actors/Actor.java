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
import graphics.AnimatedSprite;

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
			o.onNotify(e);
		}
	}
	
	Sprite tempSprite=null;
	
	public void addSprite(Sprite sprite) {
		tempSprite=sprite;
		
	}
	public void addAnimation(String name, List<Sprite> images, int animationTime){
		sprite.addAnimation(name, images, animationTime);
	}
	
	
	
	
	
	private double maxSpeed=5;
	double p=.3;
	double i=.1;
	double d=.1;
	double integralMax=maxSpeed*20*1000;
	Vector2D velocity = new Vector2D();
	
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
	AnimatedSprite sprite;
	private Faction faction;
	private HashSet<Effect> effects = new HashSet<Effect>();
	
	
	private Vector2D pos;
	Vector2D facing = new Vector2D();
	
	Vector2D tally=new Vector2D();
	Vector2D previous=new Vector2D();
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
		return sprite.getSprite();
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
	
	
	public Actor(Vector2D pos){
		this.pos=pos;
		this.setPoint=this.getPos();
		sprite = new AnimatedSprite();
	}
	public void setSetPoint(Vector2D v){
		this.setPoint=v;
	}
	public void setSpeed(Double d){
		this.maxSpeed=d;
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
		Vector2D north = new Vector2D(0,-1);
		double angle = Math.toDegrees(facing.getAngle(north));
		boolean standing = velocity.getLength()<0.5;
		
		if(angle<45){
			if(standing){
				sprite.animate("upstanding");
			}else
			sprite.animate("up");
		}else if(angle>135){
			if(standing){
				sprite.animate("downstanding");
			}else
			sprite.animate("down");
		}else if(facing.getX()>0){
			if(standing){
				sprite.animate("rightstanding");
			}else
			sprite.animate("right");
		}else{
			if(standing){
				sprite.animate("leftstanding");
			}else
			sprite.animate("left");
		}
	}
	
	
	public void progress(int time){
		animateSprite();
		
		sprite.progress(time);
		progressEffects(time);
		
		hitpoints += hpregen*time;
		manapoints += manaregen*time;
		staminapoints += staminaregen*time;
		
	
		
		Vector2D proportion=setPoint.subtract(pos);
		if(!proportion.equals(Vector2D.ZERO)){
			//this.facing = proportion.scale(1.0/proportion.getLength());
		}
		this.tally=this.tally.add(proportion.scale(time));
		double intLength=tally.getLength();
		
		if(intLength>integralMax){
			this.tally=tally.scale(integralMax/(intLength));
		}
		proportion=proportion.scale(p);
		Vector2D derivative=this.getPos().subtract(this.previous);
		derivative=derivative.scale(d);
		this.previous=this.getPos();
		
		Vector2D integral=tally.scale(i);
		
		
		//Vector2D dir=this.setPoint.subtract(this.getPos());
		Vector2D dir=integral.add(proportion).subtract(derivative);
		double length=dir.getLength();
		
		if(length>maxSpeed){
			dir=dir.scale(maxSpeed/(length));
		}
		
		velocity=dir;
		if(!velocity.equals(Vector2D.ZERO)){
			facing=velocity.getUnitVector();
		}
		this.translate(velocity);
		/*if(length==0){
				
		}else if(length<=this.maxSpeed){
			
		}else{
			this.translate(dir.scale(this.maxSpeed/length));
		}*/
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
		return 5;
	}

}
