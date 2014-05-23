package actors;

import java.awt.Graphics2D;
import java.util.Random;

import mathematics.Vector;
import mathematics.Vector2D;
import graphics.Drawable;

public abstract class Actor implements Drawable{
	private double maxSpeed=5;
	double p=.3;
	double i=.1;
	double d=.1;
	double integralMax=maxSpeed*20;
	Vector2D tally=new Vector2D();
	Vector2D previous=new Vector2D();
	public void setPid(double p, double i, double d){
		this.p=p;
		this.i=i;
		this.d=d;
	}
	
	public static Random rand=new Random();
	private Vector2D setPoint;
	
	public Actor(Vector2D pos){
		this.pos=pos;
		this.setPoint=this.getPos();
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
	
	private Vector2D pos;
	
	public Vector2D getPos(){
		return this.pos;
	}
	public void translate(Vector2D trans){
		this.pos=this.pos.add(trans);
	}
	public void progress(double time){
		Vector2D proportion=setPoint.subtract(pos);
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
			dir=dir.scale(maxSpeed/length);
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
