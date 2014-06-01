package attacks;

import java.util.List;

import external.graphics.Drawable;
import external.graphics.Renderable;
import actors.Actor;
import mathematics.Vector2D;
import Main.Game;

public abstract class Attack implements Renderable{
	private static Game model;
	public static void setModel(Game main){
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
	public List<Actor> getSurrounding(Double d, Vector2D pos){
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
	public abstract boolean exists();
	public int getX(){
		return (int)pos.getX();
	}
	public int getY(){
		return (int)pos.getY();
	}
	
	
	
	
	
	
	
}
