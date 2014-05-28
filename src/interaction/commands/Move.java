package interaction.commands;

import mathematics.Vector2D;
import actors.Actor;
import interaction.Command;

public class Move extends Command {

	@Override
	public void execute(Actor controlled, Actor target, Vector2D pos) {
		if(target!=null){
			controlled.setSetPoint(target.getPos());
		}else{
			controlled.setSetPoint(pos);
		}

	}

}
