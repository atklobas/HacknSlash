package external.io;

public class PointerLocation {
	//X and Y relative to the center of the screen
	private int x;
	private int y;
	public PointerLocation(int x, int y){
		this.x=x;
		this.y=y;
	}
	public void changeLocation(int x, int y){
		this.x=x;
		this.y=y;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public String toString(){
		return "("+this.x+","+this.y+")";
	}
}
