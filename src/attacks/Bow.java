package attacks;

import java.awt.Color;
import java.awt.Graphics2D;

import mathematics.Vector2D;
import Main.SortedLinkedList;
import actors.Actor;

public class Bow extends Weapon {
	String[] attacks=new String[]{"arrow"};
	double arrowSpeed=1500;
	int damage=150000;
	
	
	@Override
	public Attack createAttack(String attack, Actor creator, Actor target, Vector2D targetPos) {
		if(this.canAttack()){
			this.resetCoolDown();
			return new Arrow(creator,targetPos,arrowSpeed,damage);
		}
		return null;
	}

	@Override
	public String[] getAttacks() {
		return attacks.clone();
	}
	
	private class Arrow extends ProjectileAttack{
		private int maxTime=10000;
		private int currentTime=0;
		int damage;
		double xLag,yLag;
		public Arrow(Actor creator, Vector2D target, double speed,int damage) {
			super(creator, target, speed);
			Vector2D temp=this.getVelocity().getUnitVector().scale(10);
			this.xLag=-temp.getX();
			this.yLag=-temp.getY();
			this.damage=damage;
		}
		@Override
		public void progress(int time){
			super.progress(time);
			currentTime+=time;
			SortedLinkedList<Actor> temp=this.getHitList();
			if(super.hasHit()){
				temp.getFirst().damage(damage, 0, 0);
			}
		}

		@Override
		public void draw(Graphics2D g) {
			g.setColor(Color.BLACK);
			
			Vector2D pos=this.getPos();
			g.drawLine((int)pos.getX(), (int)pos.getY(), (int)(pos.getX()-xLag), (int)(pos.getY()-yLag));
			
		}

		@Override
		public boolean drawn() {
			return !this.hasHit()||currentTime>maxTime;
		}

		@Override
		public int getWidth() {
			// TODO Auto-generated method stub
			return 5;
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 5;
		}
		
	}

}
