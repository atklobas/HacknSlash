package external.resources;

import external.graphics.Sprite;


public abstract class ImageResource extends Resource {
	public abstract Sprite createSprite(int x, int y, int width, int height);
	@Override
	public String getType(){
		return "image";
	}
	public abstract Sprite createSprite(int x, int y, int width, int height, int offsetX, int offsetY);
	public abstract void setTransparent(int x, int y);
	
}
