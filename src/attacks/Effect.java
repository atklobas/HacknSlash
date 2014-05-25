package attacks;

import actors.Actor;

public abstract class Effect {
	Actor affected;
	public void start(Actor a){
		affected=a;
	}
	public void end(Actor a){
		a.removeEffect(this);
	}
	public void progress(int time){
		System.out.println("Effect Progress");
	}
}
