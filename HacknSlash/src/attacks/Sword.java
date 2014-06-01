package attacks;


import mathematics.Vector2D;
import actors.Actor;

public class Sword extends Weapon {

	private double range=20;
	int damage=50000;
	public Sword(){
		this.setCooldown(100);
	}
	@Override
	public Attack createAttack(String attack, Actor creator, Actor target, Vector2D targetPos) {
		if(this.canAttack()){
			this.resetCoolDown();
			if(attack.equals("slash")){
				return new Slash(creator,targetPos,range,damage);
			}
			
		}
		
		return null;
	}

	@Override
	public String[] getAttacks() {
		return new String[]{"slash","stab"};
	}
	
	
	private class Slash extends Attack{
		
		private double range;
		int imageDuration=150;
		boolean firstTime=true;
		int damage;
		
		
		
		public Slash(Actor creator, Vector2D target , double range, int damage) {
			super(creator, null, target.subtract(creator.getPos()).getUnitVector().scale(range*2/3.).add(creator.getPos()));
			this.damage=damage;
			this.range=range;
		}

	
	
		@Override
		public boolean rendered() {
			return imageDuration>0;
		}
	
		@Override
		public void progress(int time) {
			if(this.firstTime){
				this.firstTime=false;
				for(Actor a:this.getSurrounding(range)){
					if(a!=this.getCreator()){
						a.damage(damage, 0, 0);
						a.addEffect(new Knockback(this.getPos()));
					}
				}
			}
			imageDuration-=time;
			
		}

		@Override
		public boolean exists() {
			return rendered();
		}	
	}

}
