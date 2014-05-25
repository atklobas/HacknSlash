package attacks;


import mathematics.Vector2D;

public class Knockback extends Effect{
	int distance = 0;
	Vector2D center;
	int duration = 50;
	int speed = 30;
	
	public Knockback(Vector2D center){
		//System.out.println("Knockback created: "+center);
		this.center = center;
	}

	
	@Override
	public void progress(int time){
		if(duration>0){
			//System.out.println("Knockback progress "+affected.toString()+" ...");
			Vector2D vel = affected.getPos().subtract(center).scale(time*.001*speed);
			affected.translate(vel);
			duration -= time;
		}else{
			this.end(affected);
		}
	}

}
