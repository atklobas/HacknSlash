package actors;

import mathematics.Vector2D;

public class Zombie extends NPC{

	public Zombie(Vector2D pos) {
		super(pos);
		this.setFaction(Main.Main.factions.get("ENEMY"));
		this.addAttack("ZOMG", new Attack(new int[]{100,0,1000}, new int[]{1000,0,100}, 10,10));
	}
	public void progress(int time){
		Vector2D playerPos = Main.Main.player.getPos();
		if(playerPos.subtract(this.getPos()).getLength()<30){
			Main.Main.addAttack(this.attack("ZOMG"));
		}
		
		super.progress(time);
	}

}
