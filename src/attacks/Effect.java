package attacks;

import actors.Actor;

public abstract class Effect {
	Actor affected;
	boolean ended=false;
	public void start(Actor a){
		affected=a;
	}
	public void end(Actor a){
		this.ended=true;
	}
	public void progress(int time){
		System.out.println("Effect Progress");
	}
	public boolean hasEnded(){
		return ended;
	}
}
