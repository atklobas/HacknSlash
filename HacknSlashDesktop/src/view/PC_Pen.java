package view;

import java.awt.Color;
import java.awt.Graphics2D;

import resources.PC_Sprite;
import external.graphics.Pen;
import external.graphics.Sprite;

public class PC_Pen extends Pen{
	Graphics2D g;

	public PC_Pen(Graphics2D g){
		this.g=g;
	}
	
	
	@Override
	public void drawCircle(int x, int y, int radius) {
		g.drawOval(x, y, radius*2, radius*2);
		
	}

	@Override
	public void fillCircle(int x, int y, int radius) {
		g.fillOval(x, y, radius*2, radius*2);
		
	}

	@Override
	public void drawRect(int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
		
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		g.fillRect(x, y, width, height);
		
	}

	@Override
	public void setColor(int color) {
		g.setColor(new Color(color));
		
	}

	@Override
	public void drawLine(int x, int y, int x2, int y2) {
		g.drawLine(x, y, x2, y2);
		
	}

	@Override
	public void drawSprite(int x, int y, Sprite s) {
		if(s instanceof PC_Sprite){
			
			((PC_Sprite) s).draw(g, x, y);
		}
		
	}
	public void drawSprite(int x, int y,int width, int height, Sprite s) {
		if(s instanceof PC_Sprite){
			
			((PC_Sprite) s).draw(g, x, y, width, height);
		}
		
	}

}
