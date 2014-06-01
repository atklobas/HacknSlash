package external.graphics;



public abstract class Sprite{
	public abstract String getResourceName();
	public abstract int getResourceID();
	
	public abstract int getSheetX();
	public abstract int getSheetY();
	public abstract int getSheetWidth();
	public abstract int getSheetHeight();
	public abstract int getOffsetX();
	public abstract int getOffsetY();
	
	public abstract void setSheetX(int x);
	public abstract void setSheetY(int y);
	public abstract void setSheetWidth(int width);
	public abstract void setSheetHeight(int height);
	public abstract void setOffsetX(int xOffset);
	public abstract void setOffsetY(int yOffset);
	public abstract double getDuration();
	public abstract void setDuration(double dur);
	
	//sorry for  this looking so crazy;
	public String toXml(){
		StringBuilder ret=new StringBuilder("<bc:sprite resource_name=\"");
		ret.append(this.getResourceName());
		ret.append("\" resource_id=\"");
		ret.append(this.getResourceID());
		ret.append("\"> <bc:x>");
		ret.append(this.getSheetX());
		ret.append("</bc:x> <bc:y>");
		ret.append(this.getSheetY());
		ret.append("</bc:y> <bc:width>");
		ret.append(this.getSheetWidth());
		ret.append("</bc:width> <bc:height>");
		ret.append(this.getSheetHeight());
		ret.append("</bc:height> </bc:sprite>");
		return ret.toString();
		
	}
}
