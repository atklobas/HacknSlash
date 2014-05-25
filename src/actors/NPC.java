package actors;

import java.awt.Color;
import java.awt.Graphics2D;

import mathematics.Vector;
import mathematics.Vector2D;

public class NPC extends Actor{
	
	private Faction faction;

	public NPC(Vector2D pos) {
		super(pos);
		this.setSpeed(1.5);
		this.setPid(.2, .5, .1);
	}
	
	
	public Faction getFaction(){
		return faction;
	}
	public void setFaction(Faction f){
		faction = f;
	}

	public int getWidth() {
		return 20;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.white;
	}
	@Override
	public void progress(int time) {
		super.progress(time);
		
	}
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		Vector2D setPoint=this.getSetPoint();
		g.drawRect((int)setPoint.getX()-this.getWidth()/2, (int)setPoint.getY()-this.getHeight()/2, this.getWidth()	, this.getHeight());
		super.draw(g);	
				
	}
	

}
