package Map;

import external.graphics.Sprite;

public class Tile {
	private Sprite s;
	private int width;
	private int height;
	boolean passible;
	public Tile(Sprite s, int width, int height, boolean passable){
		this.s=s;
		this.width=width;
		this.height=height;
		this.passible=passible;
	}
	public boolean getPassible(){
		return this.passible;
	}
	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
	public Sprite getSprite(){
		return this.s;
	}
}
