package graphics;

import external.graphics.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class AnimatedSprite{

	//private HashMap<String, Animation> animations;
	//private Animation currentAnimation;
	private boolean loop = true;
	
	ArrayList<Frame> frames=new ArrayList<Frame>();
	int totalTime=0;
	public void addSprite(Sprite s, int length){
		totalTime+=length;
		frames.add(new Frame(s,length));
	}
	public Sprite getSprite(int time){
		for(Frame f:frames){
			time-=f.time;
			if(time<0){
				return f.sprite;
			}
		}
		return frames.get(0).sprite;
	}
	public int getTotalTime(){
		return this.totalTime;
	}
	
	
	
	private class Frame{
		Sprite sprite;
		private int time;
		
		public Frame(Sprite sprite, int time){
			this.sprite=sprite;
			this.time=time;
		}
		public Sprite getSprite(){
			return sprite;
		}
	}
	
	
	/*
	public AnimatedSprite(){
		animations = new HashMap<String, Animation>();
	}
	
	public Set<String> getAnimations(){
		return animations.keySet();
	}
	
	public void addAnimation(String name, List<Sprite> images, int animationTime){
		animations.put(name, new Animation(images, animationTime));
	}
	
	
	public Sprite getSprite(){
		return currentAnimation.getSprite();
	}
	
	public boolean animate(String name){ //return false if string not found
		Animation a = animations.get(name);
		if(a==null){
			return false;
		}
		if(!a.equals(currentAnimation)){
			currentAnimation = a;
			currentAnimation.restart();
		}
		return true;
	}
	public void progress(int time){
		if(currentAnimation!=null){
			currentAnimation.progress(time);
		}
	}
	
	
	
	
	private class Animation{
		
		LinkedList<Frame> frames = new LinkedList<Frame>();
		int size;
		Frame currentFrame;
		Iterator<Frame> itr;
		
		public Animation(List<Sprite> images, int animationTime){
			size=images.size();
			//this.animationTime=animationTime;
			makeFrames(images, animationTime);
		}
		
		public void makeFrames(List<Sprite> sprites, int animationTime){
			int frameTime = animationTime/size;
			for(Sprite s:sprites){
				frames.add(new Frame(s, frameTime));
			}
			restart();
		}
		
		public void progress(int time){
			int remainingTime = currentFrame.progress(time);
			if(remainingTime<0){
				if(!itr.hasNext() && loop){
					restart();
				}else{
					currentFrame=itr.next();
				}
				currentFrame.restart();
				
			}
			
			
		}
		public void restart(){
			itr=frames.iterator();
			currentFrame=itr.next();
		}
		public Sprite getSprite(){
			return currentFrame.getSprite();
		}
		
		/**/
	

}
