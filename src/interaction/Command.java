package interaction;

import mathematics.Vector2D;
import actors.Actor;

public abstract class Command {
	public abstract void execute(Actor controlled, Actor target, Vector2D pos);
	
}
