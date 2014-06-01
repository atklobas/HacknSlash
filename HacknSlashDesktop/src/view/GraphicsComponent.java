package view;

import java.awt.AWTEvent;
import java.awt.Graphics;

public interface GraphicsComponent {
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	public void paint(Graphics g);
	public boolean isMovable();
	public void moveTo(int x, int y);
	public boolean isResizable();
	public void resizeTo(int widht,int height);
	
}
