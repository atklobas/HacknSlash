package attacks;

import java.util.List;

import actors.Actor;
import mathematics.Vector2D;
import Main.Main;
import graphics.Drawable;

public abstract class Attack implements Drawable{
	private static Main model;
	public static void setModel(Main main){
		model=main;
	}
	
	private Actor creator;
	private Actor target;
	private Vector2D pos;
	
	public Attack(Actor creator, Actor target, Vector2D pos) {
		this.creator=creator;
		this.target=target;
		this.pos=pos;
	}
	public abstract void progress(int time);
	
	public List<Actor> getSurrounding(Double d){
		return model.getSurrounding(pos,d);
	}
	public void addAttack(Attack a){
		model.addAttack(a);
	}
	public Actor getCreator(){
		return this.creator;
	}
	public Actor getTarget(){
		return this.target;
	}
	public Vector2D getPos() {
		return pos;
	}
	public void translate(Vector2D v){
		pos=pos.add(v);
	}
	
	
	
	
	
	
	
	
}
