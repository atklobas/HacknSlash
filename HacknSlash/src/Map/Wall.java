package Map;


import external.graphics.Drawable;
import external.graphics.Pen;
import actors.Actor;
import mathematics.Vector;
import mathematics.Vector2D;

public class Wall implements Drawable{
	Vector2D pos;
	int width,height;
	Vector2D point1,point2;
	public Wall(Vector2D point1, Vector2D point2){
		this.point1=point1;
		this.point2=point2;
		pos=new Vector2D(Math.min(point1.getX(), point2.getX()),Math.min(point1.getY(), point2.getY()));
	}
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getColor() {
		return Pen.BLUE;
	}
	@Override
	public boolean drawn() {
		return true;
	}
	@Override
	public void draw(Pen p, int x, int y, int widht, int height) {
		p.setColor(this.getColor());
		p.drawLine((int)point1.getX(), (int)point1.getY(), (int)point2.getX(), (int)point2.getY());
		
	}
	
	public void Collides(Actor actor){
		Vector wall=point2.subtract(point1);
		Vector pos=actor.getPos().subtract(point1);
		Vector dir=pos.subtract(pos.getProjection(wall));
		double length=dir.getLength();
		
		if(length<actor.getRadius()){
			Vector temp=dir.scale((actor.getRadius()-length)/length);
			actor.translate(new Vector2D(temp));
		}
		
	}
	@Override
	public boolean rendered() {
		return true;
	}



	
}
