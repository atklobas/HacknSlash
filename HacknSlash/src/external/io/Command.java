package external.io;

import mathematics.Vector2D;
import actors.Actor;

public abstract class Command {
	private PointerLocation pointer;
	public Command(PointerLocation pointer){
		this.pointer=pointer;
	}
	
	public int getX(){
		return pointer.getX();
	}
	public int getY(){
		return pointer.getY();
	}
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
	/**
	 * called with true whenever the button assosiated with it is pressed and false when released
	 * @param isHeld the current state of the button mapped to this command
	 */
	public abstract void held(boolean isHeld);
	
}
