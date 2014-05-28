package attacks;


import mathematics.Vector2D;

public class Knockback extends Effect{
	int distance = 50;
	Vector2D center;
	int duration = 10000;
	int speed = 500;
	
	public Knockback(Vector2D center){
		//System.out.println("Knockback created: "+center);
		this.center = center;
	}
	
	public Knockback(Vector2D center, int distance){
		this.distance=distance;
	}

	
	@Override
	public void progress(int time){
		if(duration>0 && distance>0){
			//System.out.println("Knockback progress "+affected.toString()+" ...");
			Vector2D vel = affected.getPos().subtract(center).getUnitVector().scale(time*speed*.001);
			//System.out.println(vel);
			affected.translate(vel);
			duration -= time;
			distance -= time*.001*speed;
		}else{
			this.end(affected);
		}
	}

}
