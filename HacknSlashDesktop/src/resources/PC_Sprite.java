package resources;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class PC_Sprite extends external.graphics.Sprite{
	int x,y,width,height,offsetX,offsetY;
//	Image image;
	PC_ImageResource resource;
	

	public PC_Sprite(int x, int y, int width, int height, PC_ImageResource resource) {
		
		this(x,y,width,height,0,0,resource);
		
	}

	public PC_Sprite(int x, int y, int width, int height, int offsetX, int offsetY, PC_ImageResource resource) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.offsetX=offsetX;
		this.offsetY=offsetY;
		this.resource=resource;
	}

	public void draw(Graphics g,int x, int y) {
		
		resource.draw(g,this,x-offsetX,y-offsetY);
	}
	public void draw(Graphics g,int x, int y, int width,int height) {
		resource.draw(g,this,x-offsetX*width/this.width,y-offsetY*width/this.width,width,height);
	}
	public PC_ImageResource getResource(){
		return this.resource;
	}
	
	public String getResourceName() {
		return resource.getName();
	}

	public int getResourceID() {
		return resource.getID();
	}

	
	public int SheetX() {
		return x;
	}

	
	public int SheetY() {
		return y;
	}

	
	public int SheetWidth() {
		return width;
	}

	
	public int SheetHeight() {
		return height;
	}
	public int offsetX(){
		return 0;
	}
	public int offsetY(){
		return 0;
	}

	@Override
	public double getDuration() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void setDuration(double dur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSheetX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSheetY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSheetWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSheetHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOffsetX() {
		// TODO Auto-generated method stub
		return this.offsetX;
	}

	@Override
	public int getOffsetY() {
		return this.offsetY;
	}
	

	
	@Override
	public void setSheetX(int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSheetY(int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSheetWidth(int width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSheetHeight(int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOffsetX(int xOffset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOffsetY(int yOffset) {
		// TODO Auto-generated method stub
		
	}

	public void draw(Graphics2D g, int x, int y, double scale) {
		resource.draw(g,this,x-(int)(offsetX*scale),y-(int)(offsetY*scale),(int)(width*scale),(int)(height*scale));
		
	}
	

}
