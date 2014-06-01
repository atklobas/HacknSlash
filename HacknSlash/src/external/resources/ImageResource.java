package external.resources;

import external.graphics.Sprite;


public abstract class ImageResource extends Resource {
	public abstract Sprite createSprite(int x, int y, int width, int height);
	@Override
	public String getType(){
		return "image";
	}
	
}
