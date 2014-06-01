package attacks;

import java.util.LinkedList;
import java.util.List;

import mathematics.Vector2D;
import Main.Game;
import actors.Actor;

public class SpellBook extends Weapon {
	public SpellBook(){
		this.setCooldown(10);
	}

	@Override
	public Attack createAttack(String attack, Actor creator, Actor target, Vector2D targetPos) {
		// TODO Auto-generated method stub
		if(this.canAttack()){
			this.resetCoolDown();
			switch(attack){
				case "meteor":
					return new Meteor(creator, target, targetPos);
				case "chain lightning":
					return new ChainLightning(creator, target, targetPos);
			}
			
		}
		return null;
	}

	@Override
	public String[] getAttacks() {
		return new String[]{"meteor","orb","chain lightning"};
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
		public boolean rendered() {
			// TODO Auto-generated method stub
			return (delay>-100);
		}
		public boolean exists(){
			return this.rendered();
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


		@Override
		public boolean rendered() {
			// TODO Auto-generated method stub
			return time>0;
		}
		public boolean exists(){
			return this.rendered();
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
	private class ChainLightning extends Attack{
		private LinkedList<Actor> bounces=new LinkedList<Actor>();
		double maxDistance=250;
		int maxBounces=100;
		int chainDistance=10;
		int countDown=100;
		int damage=1500000;
		int jumptime=100;
		int elapsed=0;
		public ChainLightning(Actor creator, Actor target, Vector2D pos) {
			super(creator, target, creator.getPos());
			bounces.add(this.getCreator());
			
		}

		@Override
		public boolean rendered() {
			return this.countDown>0;
		}
		public boolean exists(){
			return maxBounces>0;
		}

		@Override
		public void progress(int time) {
			elapsed+=time;
			if(elapsed>this.jumptime){
				elapsed=0;

			if (this.maxBounces>0){
				Actor lastNode=this.getCreator();
				if(bounces.size()>0){
					lastNode=bounces.getLast();
				}
				List<Actor> possible=this.getSurrounding(this.maxDistance, lastNode.getPos());
				possible.removeAll(bounces);
				possible.remove(this.getCreator());
				if(possible.size()>0){
					Actor selected= possible.get(Game.rand.nextInt(possible.size()));
					selected.damage(damage, 0, 0);
					bounces.add(selected);
					if(bounces.size()>this.chainDistance){
						bounces.removeFirst();
					}
					maxBounces--;
				}else{
					maxBounces=0;
				}
			}else{
				this.countDown-=time;
			}
			
		}
		}
		
	}
}
