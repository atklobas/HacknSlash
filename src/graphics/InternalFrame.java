package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class InternalFrame implements GraphicsComponent{
	
	public static final int NOT_CONTAINED=-1;
	public static final int IN_PADDING=1;
	public static final int CONTAINED=0;
	public static final int LEFT_PADDING=2;
	public static final int RIGHT_PADDING=4;
	public static final int BOTTOM_PADDING=8;
	public static final int TOP_PADDING=16;
	
	private static final int minwidth=20;
	private static final int minheight=10;
	private int x, y, width,height;
	private int paddingTop=8;
	private int paddingBottom=1;
	private int paddingLeft=1;
	private int paddingRight=1;
	private int resizeSize=paddingTop;
	
	private boolean movable=true;
	private boolean resizable=false;
	public InternalFrame(int x, int y, int width,int height){
		this.x=x;this.y=y;this.width=width;this.height=height;
	}
	
	
	public void paint(Graphics g) {
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, paddingTop);
		g.fillRect(0, 0, paddingLeft, height);
		g.fillRect(0, height-paddingBottom, width, paddingBottom);
		g.fillRect(width-paddingLeft, 0, paddingLeft, height);
		g.translate(paddingLeft, paddingTop);
		g.setClip(0, 0, width-paddingLeft-paddingRight, height-paddingTop-paddingBottom);
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
	public boolean isMovable(){
		return movable;
	}
	public void moveTo(int x, int y){
		this.x=x;
		this.y=y;
	}
	public boolean isResizable(){
		return resizable;
	}
	public void resizeTo(int width, int height){
		if(width<paddingLeft+paddingRight+minwidth){
			width=paddingLeft+paddingRight+minwidth;
		}
		if(height<paddingTop+paddingBottom+minheight){
			height=paddingTop+paddingBottom+minheight;
		}
		this.width=width;
		this.height=height;
		
	}

	public int getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(int paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	public int getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}

	public int getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}

	public int getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}
	protected int avaliableHeight() {
		return this.height-this.paddingBottom-this.paddingTop;
	}


	public int contains(int x, int y) {
		if(x>=this.x&&x<this.x+this.width&&y>=this.y&&y<this.y+this.width){
			int ret=InternalFrame.CONTAINED;
			if(x<this.x+this.paddingLeft){
				ret|=InternalFrame.LEFT_PADDING;
			}
			if(x>this.x+this.width-this.paddingRight){
				ret|=InternalFrame.RIGHT_PADDING;
			}
			if(y<this.y+this.paddingTop){
				ret|=InternalFrame.TOP_PADDING;
			}
			if(y>this.y+this.height-this.paddingBottom){
				ret|=InternalFrame.BOTTOM_PADDING;
			}
			if(y>this.y+this.height-this.resizeSize&&x>this.x+this.width-this.resizeSize){
				ret|=InternalFrame.BOTTOM_PADDING|InternalFrame.RIGHT_PADDING;
			}
			
			return ret;
		}
		return InternalFrame.NOT_CONTAINED;
	}

}
