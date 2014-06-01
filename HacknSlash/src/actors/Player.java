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


}
