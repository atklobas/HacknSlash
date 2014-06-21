package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import actors.Actor;
import external.graphics.Sprite;
import external.resources.ImageResource;
import external.resources.ResourceLoader;
import graphics.AnimatedSprite;
import graphics.AnimationList;
import graphics.AnimationPointer;
import graphics.Direction;
import graphics.Action;

public class AnimationLoader {
	ResourceLoader loader;
	private HashMap<String, ImageResource> imageMap;
	private HashMap<String, AnimationList> animationMap;
	public AnimationLoader(ResourceLoader loader){
		this.loader=loader;
		imageMap= new HashMap<String, ImageResource>();
		animationMap= new HashMap<String, AnimationList>();
	}
	
	
	
	public void addAntimation(String loc) throws IOException{
		BufferedReader reader=loader.LoadTextResource(loc).getReader();
		Scanner test=new Scanner(reader);
		String imageName=test.nextLine().trim();
		ImageResource ir=imageMap.get(imageName);
		if(ir==null){
			ir=loader.LoadImageResource(imageName);
			ir.setTransparent(0, 0);
			imageMap.put(imageName, ir);
		}
		AnimationList animation=new AnimationList();
		
		
		while(test.hasNext()){
			test.nextLine();
			Direction d=Direction.fromString(test.next());
			Action a=Action.fromString(test.nextLine().trim());
			AnimatedSprite s=new AnimatedSprite();
			while(test.hasNextInt()){
				s.addSprite(createSprite(test.nextLine(),ir), 150);
			}
			animation.addAnimation(a, d, s);
		}
		this.animationMap.put(loc, animation);
		test.close();
	}
	private Sprite createSprite(String s, ImageResource ir){
		Scanner temp=new Scanner(s);
		return ir.createSprite(temp.nextInt(), temp.nextInt(), temp.nextInt(), temp.nextInt(), temp.nextInt(), temp.nextInt());
	}



	public AnimationPointer getAnimationList(String string) {
		// TODO Auto-generated method stub
		return new AnimationPointer(this.animationMap.get(string));
	}
	

}
