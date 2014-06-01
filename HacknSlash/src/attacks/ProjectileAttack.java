package attacks;

import java.util.List;

import mathematics.Vector2D;
import Main.SortedLinkedList;
import actors.Actor;

public abstract class ProjectileAttack extends Attack{

	private boolean hit;
	private Vector2D vel;
	double speed;
	private SortedLinkedList<Actor> hitList=new SortedLinkedList<Actor>();
	
	
	public ProjectileAttack(Actor creator, Vector2D target, double speed) {
		super(creator, null, creator.getPos());
		this.speed=speed;
		vel=target.subtract(creator.getPos());
		vel=vel.scale(speed/vel.getLength());
		hit=false;
	}

	@Override
	public void progress(int time) {
		Vector2D movement=vel.scale(time/1000.);
		this.translate(movement.scale(.5));
		List<Actor> actors=this.getSurrounding(speed*time/500.);
		this.translate(movement.scale(-.5));
		Vector2D perp=new Vector2D(movement.getY(),-movement.getX());
		hitList=new SortedLinkedList<Actor>();
		for(Actor a:actors){
			if(a.getPos().subtract(this.getPos()).getProjection(perp).getLength()<a.getRadius()
					&&a!=this.getCreator()){
				hitList.add(a, a.getPos().subtract(this.getPos()).getProjection(movement).getLength());
			}
		}
		if(hitList.getCount()>0&&hitList.getFirstRank()<speed*time/1000.){
			hit=true;
		}
		this.translate(movement);
	}
	public boolean hasHit(){
		return hit;
	}
	public SortedLinkedList getHitList(){
		return hitList;
	}
	public Vector2D getVelocity(){
		return vel;
	}

	
}
