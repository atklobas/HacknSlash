package Map;

import java.io.IOException;

import external.graphics.Drawable;
import external.graphics.Pen;
import external.graphics.Sprite;
import external.resources.ImageResource;
import external.resources.ResourceLoader;
/* world will contain a list of regions which contain a list references to tiles
 * tiles contain information about pasability and their image
 * 
 * in the future regions will be loaded and unloaded based on things like proxemety and
 * 
 */
public class World implements Drawable{
	Tile stone;
	Tile sand;
	Tile oldstone;
	Tile dirt;
	int tileSize=16*3;
	int regionSize=32;
	int Xoffset=1;
	int Yoffset=1;
	Region[][] regions=new Region[2][2];
	public World(ResourceLoader loader) {
		
		try {
			ImageResource ir=loader.LoadImageResource("smalltiles.png");
			stone=new Tile(ir.createSprite(0, 16, 16, 16),tileSize,tileSize,true);
			sand=new Tile(ir.createSprite(2*16, 1*16, 16, 16),tileSize,tileSize,true);
			oldstone=new Tile(ir.createSprite(4*16, 2*16, 16, 16),tileSize,tileSize,true);
			dirt=new Tile(ir.createSprite(2*16, 0*16, 16, 16),tileSize,tileSize,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		regions[0][0]=new Region(stone,-regionSize*tileSize,-regionSize*tileSize,regionSize,regionSize);
		regions[1][0]=new Region(sand,0,-regionSize*tileSize,regionSize,regionSize);
		regions[0][1]=new Region(dirt,-regionSize*tileSize,0,regionSize,regionSize);
		regions[1][1]=new Region(oldstone,0,0,regionSize,regionSize);
		
		
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean rendered() {
		return true;
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean drawn() {
		return true;
	}

	@Override
	public void draw(Pen p,int minx, int miny, int width, int height) {
		p.setColor(0x0);
		regions[0][0].draw(p, minx,miny, width, height);
		regions[1][0].draw(p, minx,miny, width, height);
		regions[0][1].draw(p, minx,miny, width, height);
		regions[1][1].draw(p, minx,miny, width, height);
		
		
	}

}
