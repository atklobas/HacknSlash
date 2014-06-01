package graphics;

import external.graphics.Sprite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


public class AnimatedSprite{

	private HashMap<String, Animation> animations;
	private Animation currentAnimation;
	
	
	public AnimatedSprite(){
		animations = new HashMap<String, Animation>();
	}
	
	public Set<String> getAnimations(){
		return animations.keySet();
	}
	
	public void addAnimation(String name, LinkedList<Sprite> images, int animationTime){
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
		currentAnimation = a;
		currentAnimation.restart();
		return true;
	}
	public void progress(int time){
		if(currentAnimation!=null){
			currentAnimation.progress(time);
		}
	}
	
	
	
	
	private class Animation{
		
		LinkedList<Frame> frames;
		int size;
		int animationTime = 1000; //in milliseconds
		int currentTime = 0;
		Frame currentFrame;
		Iterator<Frame> itr;
		
		public Animation(LinkedList<Sprite> images, int animationTime){
			makeFrames(images);
			this.animationTime=animationTime;
			size=images.size();
			
		}
		
		public void makeFrames(LinkedList<Sprite> sprites){
			int frameTime = animationTime/size;
			for(Sprite s:sprites){
				frames.add(new Frame(s, frameTime));
			}
			itr = frames.iterator();
		}
		
		public void progress(int time){
			time -= currentTime;
			while(time>0){
				if(!itr.hasNext()){
					itr = frames.iterator();
				}
				currentFrame=itr.next();
				time = currentFrame.progress(time);
			}
			currentTime = time;
		}
		public void restart(){
			currentTime=0;
			itr=frames.iterator();
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
				return time-this.time;
			}
		}
	}

}