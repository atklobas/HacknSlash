package ai;

import java.util.List;

import mathematics.Vector2D;
import Main.Game;
import actors.Actor;
import actors.Player;
import ai.commands.Move;

public class Wanderer extends AI{
	Actor controlled;
	long timeOfThought= 0;
	long maxDistance=100;
	
	public Wanderer(Actor controlled){
		this.controlled=controlled;
	}
	
	@Override
	public void think() {
		
		long time=System.currentTimeMillis();
		if(time-timeOfThought>Game.rand.nextInt(30000)){
			List<Actor> close=this.getSurrounding(controlled.getPos(), 150);
			for(Actor a:close){
				if(a instanceof Player&&a!=this.controlled){
					this.addCommand(new Move(this.controlled,a.getPos()));
					return;
				}
			}
			timeOfThought=time;
			Vector2D toAdd=new Vector2D(Game.rand.nextDouble()-.5,Game.rand.nextDouble()-.5).getUnitVector().scale(Game.rand.nextDouble()*maxDistance);
			this.addCommand(new Move(this.controlled,controlled.getPos().add(toAdd)));
		}
		
	}

}
