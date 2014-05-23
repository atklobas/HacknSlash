package consoletools;

import actors.Actor;

public class modify implements ShellProgram {
	Actor actor;
	public modify(Actor actor){
		this.actor=actor;
	}
	@Override
	public int Execute(Shell s, String... args) throws Exception {
		if(args.length>=2){
			if(args[0].equals("health")){
				actor.damage(new int[]{-Integer.parseInt(args[1]),0,0});
			}
		}
		return 0;
	}

}
