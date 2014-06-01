package external.graphics;


public interface Drawable extends Renderable{
	public int getColor();
	public boolean drawn();
	public void draw(Pen p, int x, int y, int widht, int height);

}
