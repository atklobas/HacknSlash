package external.graphics;


public abstract class Pen {
	public static final int BLACK=0x000000;	
	public static final int	BLUE=0x0000ff;
	public static final int	CYAN=0x00ffff;
	public static final int	DARK_GRAY=0x2E2E2E;
	public static final int	LIGHT_GRAY=0x9D9D9D;
	public static final int	GRAY=0x777777;
	public static final int	GREEN=0x00ff00;
	public static final int	MAGENTA=0xff00ff;
	public static final int	RED=0xff0000;
	public static final int	WHITE=0xffffff;
	public static final int	YELLOW=0xffff00;
	
	public abstract void drawCircle(int x, int y, int radius);
	public abstract void fillCircle(int x, int y, int radius);
	public abstract void drawRect(int x, int y, int widht, int height);
	public abstract void fillRect(int x, int y, int widht, int height);
	public abstract void setColor(int color);
	public abstract void drawLine(int x, int y, int x2, int y2);
	public abstract void drawSprite(int x, int y, Sprite s);
	public abstract void drawSprite(int x, int y, int width, int height,Sprite sprite);

}
