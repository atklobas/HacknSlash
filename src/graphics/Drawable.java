package graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import mathematics.Vector;
import mathematics.Vector2D;


public interface Drawable {
	public Vector2D getPos();
	public int getWidth();
	public int getHeight();
	public Color getColor();
	public void draw(Graphics2D g);
	public boolean drawn();

}
