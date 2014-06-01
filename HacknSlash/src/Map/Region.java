package Map;

import external.graphics.Pen;

public class Region {
	private int width;
	private int height;
	private int x;
	private int y;
	private int tileWidth;
	private int tileHeight;
	private Tile[][] tiles;
	public Region(Tile defaultTile,int x, int y, int width, int height){
		this.width=width;
		this.height=height;
		this.x=x;
		this.y=y;
		this.tileWidth=defaultTile.getWidth();
		this.tileHeight=defaultTile.getHeight();
		tiles=new Tile[width][height];
		for(int xx=0;xx<width;xx++){
			for(int yy=0;yy<height;yy++){
				tiles[xx][yy]=defaultTile;
			}
		}
	}
	public void draw(Pen p,int minX, int minY, int width, int height) {
		int xmin= Math.max((minX-this.x)/tileWidth,0);
		int ymin=  Math.max((minY-this.y)/tileHeight,0);
		int maxX=Math.min((minX+width-this.x)/tileWidth+1, this.width);
		int maxY=Math.min((minY+height-this.y)/tileHeight+1, this.height);
		for(int x=xmin;x<maxX;x++){
			for(int y=ymin;y<maxY;y++){
				p.drawSprite(this.x+x*tileWidth, this.y+y*(tileHeight),tileWidth,tileHeight,tiles[x][y].getSprite() );
			}
		}
		
	}
}
