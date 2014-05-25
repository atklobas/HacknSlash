package actors;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import attacks.Attack;
import attacks.Effect;
import Main.Map;
import mathematics.Vector;
import mathematics.Vector2D;
import graphics.AnimatedSprite;
import graphics.Drawable;

public abstract class Actor implements Drawable{
	
	
	
	
	private double maxSpeed=5;
	double p=.3;
	double i=.1;
	double d=.1;
	double integralMax=maxSpeed*20*1000;
	int hitpoints = 100000;
	int hpregen = 1;
	int manapoints = 100000;
	int manaregen = 3;
	int staminapoints = 100000;
	int staminaregen = 5;
	HashMap<String, Attack> attacks = new HashMap<String, Attack>();
	public static Random rand=new Random();
	private Vector2D setPoint;
	private AnimatedSprite sprite;
	private Faction faction;
	private HashSet<Effect> effects = new HashSet<Effect>();
	
	
	
	
	private Vector2D pos;
	Vector2D facing;
	
	Vector2D tally=new Vector2D();
	Vector2D previous=new Vector2D();
	public void setPid(double p, double i, double d){
		this.p=p;
		this.i=i;
		this.d=d;
	}
	
	public void addEffect(Effect e){
		effects.add(e);
		e.start(this);
	}
	
	public void removeEffect(Effect e){
		effects.remove(e);
	}
	
	public void progressEffects(int time){
		for(Effect e:effects){
			e.progress(time);
		}
	}
	
	public AnimatedSprite getSprite(){
		return sprite;
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
	public void progress(int time){
		sprite.progress(time);
		progressEffects(time);
		
		hitpoints += hpregen*time;
		manapoints += manaregen*time;
		staminapoints += staminaregen*time;
		
	
		
		Vector2D proportion=setPoint.subtract(pos);
		if(!proportion.equals(Vector2D.ZERO)){
			this.facing = proportion.scale(1.0/proportion.getLength());
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
		this.translate(dir);
		/*if(length==0){
				
		}else if(length<=this.maxSpeed){
			
		}else{
			this.translate(dir.scale(this.maxSpeed/length));
		}*/
	}
	public double getX(){
		return this.pos.getX();
	}
	public double getY(){
		return this.pos.getY();
	}
	@Override
	public void draw(Graphics2D g) {
		Set<String> animations = sprite.getAnimations();
		if(!animations.isEmpty()){
			Iterator<String> itr= animations.iterator();
			sprite.animate(itr.next());
			g.drawImage(sprite.getImage(), null, (int)this.getX()-this.getWidth()/2, (int)this.getY()-this.getHeight()/2);
		}
		g.setColor(this.getColor());
		g.fillRect((int)this.getX()-this.getWidth()/2, (int)this.getY()-this.getHeight()/2, (int)this.getWidth(), (int)this.getHeight());
	}
	public void collide(Actor c) {
		Vector2D toMove=c.pos.subtract(this.pos);
		double length=toMove.getLength();
		double min=(this.getWidth()+c.getWidth())/2;
		if(length<min){
			this.pos=this.pos.add(toMove.scale((length-min)/length));
			c.pos=c.pos.subtract(toMove.scale((length-min)/length));
		}
		
	}

}
