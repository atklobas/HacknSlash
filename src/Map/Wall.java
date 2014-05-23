package Map;

import java.awt.Color;
import java.awt.Graphics2D;

import actors.Actor;
import mathematics.Vector;
import mathematics.Vector2D;
import graphics.Drawable;

public class Wall implements Drawable{
	Vector2D pos;
	int width,height;
	Vector2D point1,point2;
	public Wall(Vector2D point1, Vector2D point2){
		this.point1=point1;
		this.point2=point2;
		pos=new Vector2D(Math.min(point1.getX(), point2.getX()),Math.min(point1.getY(), point2.getY()));
	}
	public Vector2D getPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.BLUE;
	}
	@Override
	public void draw(Graphics2D g) {
		g.setColor(this.getColor());
		g.drawLine((int)point1.getX(), (int)point1.getY(), (int)point2.getX(), (int)point2.getY());
		
	}
	public void Collides(Actor actor){
		Vector wall=point2.subtract(point1);
		Vector pos=actor.getPos().subtract(point1);
		Vector dir=pos.subtract(pos.getProjection(wall));
		double length=dir.getLength();
		
		if(length<actor.getWidth()/2){
			Vector temp=dir.scale((actor.getWidth()/2-length)/length);
			//System.out.println(temp);
			actor.translate(new Vector2D(temp));
		}
		
	}

}
