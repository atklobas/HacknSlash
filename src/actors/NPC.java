package actors;

import java.awt.Color;
import java.awt.Graphics2D;

import mathematics.Vector;
import mathematics.Vector2D;

public class NPC extends Actor{
	
	private Faction faction;

	public NPC(Vector2D pos) {
		super(pos);
		this.setSpeed(2.);
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
		return Color.GREEN;
	}
	int progressionCounter;
	@Override
	public void progress(int time) {
		progressionCounter++;
		if(progressionCounter%8==0){
			//this.setSetPoint(this.getPos().add(new Vector2D(rand.nextInt(100)-50,rand.nextInt(100)-50)));
			this.setSetPoint(Main.Main.player.getSetPoint());
		}
		super.progress(time);
		
	}
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		//Vector2D setPoint=this.getSetPoint();
		//g.drawRect((int)setPoint.getX()-this.getWidth()/2, (int)setPoint.getY()-this.getHeight()/2, this.getWidth()	, this.getHeight());
		super.draw(g);	
				
	}
	

}
