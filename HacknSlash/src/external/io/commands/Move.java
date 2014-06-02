package external.io.commands;

import external.io.Command;
import external.io.PlayerCommand;
import external.io.PointerLocation;
import mathematics.Vector2D;
import actors.Actor;

public class Move extends PlayerCommand {
	boolean isActive=false;
	
	public Move(PointerLocation pointer) {
		super(pointer);
	}
	
	@Override
	public void execute(Actor controlled) {
			controlled.setSetPoint(controlled.getPos().add(new Vector2D(this.getX(), this.getY())));
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void held(boolean isHeld) {
		isActive=isHeld;
	}

}
