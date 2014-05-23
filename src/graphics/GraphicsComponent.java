package graphics;

import java.awt.AWTEvent;
import java.awt.Graphics;

public interface GraphicsComponent {
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	public void paint(Graphics g);
}
