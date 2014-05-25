package attacks;

import java.awt.Color;
import java.awt.Graphics2D;

import mathematics.Vector2D;
import actors.Actor;

public class SpellBook extends Weapon {

	@Override
	public Attack createAttack(String attack, Actor creator, Actor target, Vector2D targetPos) {
		// TODO Auto-generated method stub
		return new Meteor(creator, target, targetPos);
	}

	@Override
	public String[] getAttacks() {
		// TODO Auto-generated method stub
		return new String[]{"meteor","orb","chain lightning "};
	}
	private class Meteor extends Attack{
		int delay=1500;
		int burn=1500;
		int burnDamage=100;
		double radius=100;
		int damage=10000;
		public Meteor(Actor creator, Actor target, Vector2D pos) {
			super(creator, target, pos);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getWidth() {
			// TODO Auto-generated method stub
			return (int)radius*2;
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return (int)radius*2;
		}

		@Override
		public Color getColor() {
			// TODO Auto-generated method stub
			return Color.red;
		}

		@Override
		public void draw(Graphics2D g) {
			Vector2D pos=this.getPos();
			if(delay>0){
				g.setColor(this.getColor());
				g.fillOval((int)pos.getX()-2, (int)pos.getY()-2, 4, 4);
			}else{
				g.setColor(this.getColor());
				g.drawOval((int)(pos.getX()-radius), (int)(pos.getY()-radius), getWidth(), getHeight());
			}
			
		}

		@Override
		public boolean drawn() {
			// TODO Auto-generated method stub
			return (delay>-100);
		}

		@Override
		public void progress(int time) {
			if(delay>0){
				
				delay-=time;
				if(delay<=0){
					for(Actor a:this.getSurrounding(radius)){
						if(a!=this.getCreator()){
							a.damage(damage, 0, 0);
							
						}
					}
					this.addAttack(new Burn(this.getCreator(),this.getTarget(),this.getPos(),burnDamage, burn,radius));
				}
			}else{
				delay-=time;
			}
		}	
	}
	private class Burn extends Attack{
		int damage;
		int time;
		double radius;
		public Burn(Actor creator, Actor target, Vector2D pos, int damage, int time, double radius) {
			super(creator, target, pos);
			this.damage=damage;
			this.time=time;
			this.radius=radius;
		}

		public int getWidth() {
			// TODO Auto-generated method stub
			return (int)radius*2;
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return (int)radius*2;
		}

		@Override
		public Color getColor() {
			return new Color(255,0,0,127);
		}

		@Override
		public void draw(Graphics2D g) {
			Vector2D pos=this.getPos();
			g.setColor(this.getColor());
			g.fillOval((int)(pos.getX()-radius), (int)(pos.getY()-radius), getWidth(), getHeight());

			
		}

		@Override
		public boolean drawn() {
			// TODO Auto-generated method stub
			return time>0;
		}

		@Override
		public void progress(int time) {
			this.time-=time;
			for(Actor a:this.getSurrounding(radius)){
				if(a!=this.getCreator()){
					a.damage(damage*time, 0, 0);
				}
			}
			
		}
		
	}
}
