package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import actors.Actor;
import external.graphics.Sprite;
import external.resources.ImageResource;
import external.resources.ResourceLoader;

public class AnimationLoader {
	ResourceLoader loader;
	public AnimationLoader(ResourceLoader loader){
		this.loader=loader;
	}
	public void addAntimation(Actor a, String loc) throws IOException{
		BufferedReader reader=loader.LoadTextResource(loc).getReader();
		
		Scanner test=new Scanner(reader);
		String imageName=test.nextLine().trim();
		ImageResource ir=loader.LoadImageResource(imageName);
		ir.setTransparent(0, 0);
		while(test.hasNext()){
			test.nextLine();
			String animationName=test.nextLine().trim();
			LinkedList<Sprite> sprites=new LinkedList<Sprite>();
			while(test.hasNextInt()){
				sprites.add(createSprite(test.nextLine(),ir));
			}
			a.addAnimation(animationName, sprites, 500);
		}
	}
	private Sprite createSprite(String s, ImageResource ir){
		Scanner temp=new Scanner(s);
		return ir.createSprite(temp.nextInt(), temp.nextInt(), temp.nextInt(), temp.nextInt(), temp.nextInt(), temp.nextInt());
	}
	

}
