package attacks;

import java.util.List;
import java.util.Set;

import mathematics.Vector2D;
import actors.Actor;

public abstract class Weapon {
	private int cooldown=250;
	private int cooldownTimer=-1;
	
	public abstract Attack createAttack(String attack,Actor creator, Actor target, Vector2D targetPos);
	public abstract String[] getAttacks();
	public void progress(int time){
		if(cooldownTimer>0)cooldownTimer-=time;
	}
	public boolean canAttack(){
		return !(cooldownTimer>0);
	}
	public void resetCoolDown(){
		this.cooldownTimer=cooldown;
	}
	public void setCooldown(int time){
		this.cooldown=time;
	}

}
