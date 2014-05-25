package actors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import attacks.Attack;
import attacks.Bow;
import attacks.Weapon;
import mathematics.Vector;
import mathematics.Vector2D;

public class Player extends Actor{
	public enum hotKeys{
		Q,W,E,R,ONE,TWO,THREE,FOUR,PRIMARY,SECONDARY
	}
	Weapon bow=new Bow();
	
	public Player(int x, int y){
		super(new Vector2D(x,y));
		this.setSpeed(10.);
		this.setPid(.5, .00, .1);
		
	}
	public Attack getAttack(hotKeys k, Actor target){
		return bow.createAttack(bow.getAttacks()[0], this, target, target.getPos());
	}
	public Attack getAttack(hotKeys k, Vector2D target){
		return bow.createAttack(bow.getAttacks()[0], this, null, target);
	}
	
	public int getWidth() {
		return 20;
	}

	@Override
	public int getHeight() {
		return 20;
	}

	@Override
	public Color getColor() {
		return Color.BLACK;
	}
	
	public void damage(int health,int mana, int stamina){
		super.damage(health,mana,stamina);
		Main.Main.out.println("Ouch! Player took "+health+" damage. "+hitpoints+" hp remaining.");
	}
	

	@Override
	public void progress(int time) {
		super.progress(time);
		if(this.getSetPoint().subtract(this.getPos()).getLength()<2){
			this.setSetPoint(this.getPos());
		}
		bow.progress(time);
		
	}
	public void draw(Graphics2D g) {
		g.setColor(Color.CYAN);
		Vector2D setPoint=this.getSetPoint();
		g.fillRect((int)setPoint.getX()-this.getWidth()/2, (int)setPoint.getY()-this.getHeight()/2, this.getWidth()	, this.getHeight());
		super.draw(g);	
				
	}

}
