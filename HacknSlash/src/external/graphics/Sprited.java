package external.graphics;

public interface Sprited extends Renderable{
	public boolean drawn();
	public Sprite getSprite();
	public int getX();
	public int getY();
}
