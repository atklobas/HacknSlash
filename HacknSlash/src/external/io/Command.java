package external.io;

import mathematics.Vector2D;
import actors.Actor;

public abstract class Command {
	
	/**
	 * Called at the beginning of the gameloop if its in the list of active commands
	 * @param controlled The actor being controlled during this execution
	 */
	public abstract void execute(Actor controlled);
	/**
	 * called before execute is called, if its false its removed from the list of active commands and is not executed
	 * @return if the command should be executed
	 */
	public abstract boolean isActive();
	
	
}
