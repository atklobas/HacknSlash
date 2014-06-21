package graphics;

import java.util.Set;

import external.graphics.Sprite;

public class AnimationPointer {
	private AnimatedSprite current;
	private AnimationList animations;
	private int time;
	public AnimationPointer(AnimationList list){
		animations=list;
	}
	public void progress(int time){
		this.time+=time;
		this.time=this.time%current.getTotalTime();
	}
	public Sprite getSprite() {
		return current.getSprite(time);
	}
	public void setAction(Action a, Direction d){
		this.current=animations.getAnimation(a, d);
	}
	public Set<Direction> avaliableDirections(){
		return this.animations.getAvaliableDirection();
	}
}
