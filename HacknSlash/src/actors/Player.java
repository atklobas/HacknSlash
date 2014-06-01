package actors;


import external.graphics.Sprite;
import attacks.Attack;
import attacks.Bow;
import attacks.SpellBook;
import attacks.Sword;
import attacks.Weapon;
import mathematics.Vector;
import mathematics.Vector2D;

public class Player extends Actor{
	
	public Player(int x, int y){
		super(new Vector2D(x,y));
		this.setSpeed(3.);
		this.setPid(.5, .00, .1);
		
	}
	
	public void progress(int time){
		if(facing.getX()>0){
			if(facing.getY()>0){
				if(velocity.equals(Vector2D.ZERO)){
					sprite.animate("downrightstanding");
				}else{
					sprite.animate("downright");
				}
			}else{
				if(velocity.equals(Vector2D.ZERO)){
					sprite.animate("uprightstanding");
				}else{
					sprite.animate("upright");
				}
			}
		}else{
			if(facing.getY()>0){
				if(velocity.equals(Vector2D.ZERO)){
					sprite.animate("downleftstanding");
				}else{
					sprite.animate("downleft");
				}
			}else{
				if(velocity.equals(Vector2D.ZERO)){
					sprite.animate("upleftstanding");
				}else{
					sprite.animate("upleft");
				}
			}
		}
		super.progress(time);
	}


}
