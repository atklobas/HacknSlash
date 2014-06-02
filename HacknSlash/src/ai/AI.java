package ai;

import external.io.Command;
import Main.Game;

public abstract class AI {
	private static Game model;
	public static void setModel(Game main){
		model=main;
	}
	public void addCommand(Command c){
		model.addCommand(c);
	}
	public abstract void think();
}
