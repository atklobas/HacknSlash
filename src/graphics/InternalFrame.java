package graphics;

import java.awt.image.BufferedImage;

public abstract class InternalFrame implements GraphicsComponent{
	private int x, y, width,height;
	private BufferedImage image;
	public InternalFrame(int x, int y, int width,int height){
		this.x=x;this.y=y;this.width=width;this.height=height;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

}
