package actors;

import java.awt.Color;
import java.awt.Graphics2D;

import graphics.Drawable;
import mathematics.Vector2D;

public class Attack implements Drawable{

	public long created= System.currentTimeMillis();
	private int length=500;
	int manacost = 0;
	int hpcost = 0;
	int staminacost = 0;
	
	int manadamage = 0;
	int hpdamage = 0;
	int staminadamage = 0;
	
	double distance = 0;
	double radius = 0;
	
	private Vector2D pos;
	
	private Faction faction;
	
	public Attack(int[] cost, int[] damage, double distance, double radius){
		hpcost = cost[0]; manacost = cost[1]; staminacost = cost[2];
		hpdamage = damage[0]; manadamage = damage[1]; staminadamage = damage[2];
		this.distance = distance;
		this.radius = radius;
	}
	
	public Faction getFaction(){
		return faction;
	}
	public void setFaction(Faction f){
		faction = f;
	}
	
	public void setPos(Vector2D pos){
		this.pos = pos;
	}
	public Vector2D getPos(){
		return pos;
	}
	
	public void collide(Actor c) {
		//System.out.println(faction+" attacking "+c.getFaction());
		if(!faction.equals(c.getFaction())){
			Vector2D toMove=c.getPos().subtract(this.pos);
			double length=toMove.getLength();
			double min=(this.radius+c.getWidth())/2;
			if(length<min){
				c.damage(new int[]{hpdamage, manadamage, staminadamage});
			}
		}
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return (int)radius;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return (int)radius;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return new Color(200, 0, 80);
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(this.getColor());
		g.fillOval((int)(pos.getX()-radius), (int)(pos.getY()-radius), (int)radius*2, (int)radius*2);
	}

	@Override
	public boolean drawn() {
		// TODO Auto-generated method stub
		return this.created+this.length>System.currentTimeMillis();
	}
	
	
	
}
