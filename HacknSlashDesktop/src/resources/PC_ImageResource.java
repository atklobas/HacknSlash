package resources;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import external.graphics.Sprite;

public class PC_ImageResource extends external.resources.ImageResource{
	
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsConfiguration gc;
	private VolatileImage image;
	private BufferedImage presistent;
	private String name;
	
	public PC_ImageResource(String location, String name) throws IOException{
		this(location);
		this.name=name;
	}
	public PC_ImageResource(String location) throws IOException{
		File temp=(new File(location));
		gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		presistent=ImageIO.read(temp);
		image=gc.createCompatibleVolatileImage(presistent.getWidth(), presistent.getHeight());
		image.createGraphics().drawImage(presistent, 0, 0, null);
		name=temp.getName();
	}
	private void ensureImage(){
		switch(image.validate(gc)){
		case VolatileImage.IMAGE_INCOMPATIBLE:
			image=gc.createCompatibleVolatileImage(presistent.getWidth(), presistent.getHeight());
		case VolatileImage.IMAGE_RESTORED:
			image.createGraphics().drawImage(presistent, 0, 0, null);
		default:
			
		}
	}
	
	@Override
	public Sprite createSprite(int x, int y, int width, int height) {
		return new PC_Sprite(x,y,width,height,this);
	}
	

	@Override
	public String getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return name.hashCode();
	}

	public void draw(Graphics g, PC_Sprite s, int x, int y, int width, int height) {
		ensureImage();
		//don't even worry about it... really
		g.drawImage(image,x,y,x+width,y+height,s.x,s.y,s.x+s.width,s.y+s.height,null);
		
	}
	public void draw(Graphics g, PC_Sprite s,int x, int y) {
		draw(g,s,x,y,s.width,s.height);
	}
	public BufferedImage getImage() {
		return this.presistent;
	}




}
