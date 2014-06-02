package external.io;

import actors.Actor;

public abstract class PlayerCommand extends Command{

	private PointerLocation pointer;
	public PlayerCommand(PointerLocation pointer){
		this.pointer=pointer;
	}
	
	public int getX(){
		return pointer.getX();
	}
	public int getY(){
		return pointer.getY();
	}

	/**
	 * called with true whenever the button assosiated with it is pressed and false when released
	 * @param isHeld the current state of the button mapped to this command
	 */
	public abstract void held(boolean isHeld);

}
