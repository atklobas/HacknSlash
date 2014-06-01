package graphics;

import external.graphics.Sprite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class AnimatedSprite{

	private HashMap<String, Animation> animations;
	private Animation currentAnimation;
	private boolean loop = true;
	
	
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
		int animationTime = 1000; //in milliseconds
		int currentTime = 0;
		Frame currentFrame;
		Iterator<Frame> itr;
		long hash;
		
		public Animation(List<Sprite> images, int animationTime){
			size=images.size();
			this.animationTime=animationTime;
			makeFrames(images);
			hash=System.currentTimeMillis();
		}
		
		public void makeFrames(List<Sprite> sprites){
			int frameTime = animationTime/size;
			for(Sprite s:sprites){
				frames.add(new Frame(s, frameTime));
			}
			itr = frames.iterator();
			currentFrame=itr.next();
		}
		
		public void progress(int time){
			int remainingTime = currentFrame.progress(time);
			currentTime-=time;
			if(remainingTime>0){
				return;
			}else if((currentTime<0 || !itr.hasNext()) && loop){
				restart();
			}
			currentFrame=itr.next();
		}
		public void restart(){
			itr=frames.iterator();
			currentTime=animationTime;
		}
		public Sprite getSprite(){
			return currentFrame.getSprite();
		}
		
		
		private class Frame{
			Sprite sprite;
			public int time;
			public Frame(Sprite sprite, int time){
				this.sprite=sprite;
				this.time=time;
			}
			public Sprite getSprite(){
				return sprite;
			}
			public int progress(int time){
				this.time-=time;
				return this.time;
			}
		}
	}

}
