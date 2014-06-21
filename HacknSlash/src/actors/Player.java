package actors;


import external.graphics.Sprite;
import graphics.AnimationPointer;
import attacks.Attack;
import attacks.Bow;
import attacks.SpellBook;
import attacks.Sword;
import attacks.Weapon;
import mathematics.Vector;
import mathematics.Vector2D;

public class Player extends Actor{
	
	public Player(int x, int y, AnimationPointer p, double radius){
		super(new Vector2D(x,y),p,radius);
		//this.setPid(.5, 0, .1);
		this.setSpeed(5.5);
		
	}
}
