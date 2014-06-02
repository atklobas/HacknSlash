package ai.commands;

import mathematics.Vector2D;
import actors.Actor;
import external.io.Command;
import external.io.PointerLocation;

public class Move extends Command{
	Vector2D location;
	public Move(Actor a, Vector2D location) {
		this.controlled=a;
		this.location=location;
	}

	private Actor controlled;
	private boolean hasExecuted=false;
	

	@Override
	public void execute(Actor controlled) {
		hasExecuted=true;
		this.controlled.setSetPoint(location);
		
	}

	@Override
	public boolean isActive() {
		return !hasExecuted;
	}

}
