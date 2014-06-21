package ai;

import java.util.List;

import mathematics.Vector;
import mathematics.Vector2D;
import actors.Actor;
import external.io.Command;
import Main.Game;

public abstract class AI{
	private static Game model;
	public static void setModel(Game main){
		model=main;
	}
	public void addCommand(Command c){
		model.addCommand(c);
	}
	public List<Actor> getSurrounding(Vector2D pos, double distance){
		return model.getSurrounding(pos, distance);
	}
	public abstract void think();
}
