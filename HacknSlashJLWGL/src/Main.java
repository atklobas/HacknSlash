import java.io.File;

import org.lwjgl.LWJGLException;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import external.io.PointerLocation;


public class Main {
	public static final int width=800;
	public static final int height=600;
	PointerLocation mouseLocation=new PointerLocation(0,0);
	public Main() throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(width,height));
		Display.create();
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		
		while(!Display.isCloseRequested()){
			
			Display.update();
			input();
		}
		Display.destroy();
	}
	public void input(){
		mouseLocation.changeLocation(Mouse.getX(), height-Mouse.getY());
		while(Keyboard.next()){
			System.out.println(Keyboard.getEventKey()+"mouse at"+mouseLocation);
		}
	}
	
	
	public static void main(String[] args) throws LWJGLException{
		new Main();
	}
}
