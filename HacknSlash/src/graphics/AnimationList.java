package graphics;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Scanner;
import java.util.Set;

public class AnimationList {
	
	private EnumMap<Action,EnumMap<Direction,AnimatedSprite>> map=new EnumMap<Action,EnumMap<Direction,AnimatedSprite>>(Action.class);
	
	public AnimationList(){
	}
	public EnumSet<Action> avaliableActions= EnumSet.noneOf(Action.class);
	public EnumSet<Direction> avaliableDirections=EnumSet.noneOf(Direction.class);
	
	public void addAnimation(Action a, Direction d, AnimatedSprite animation){
		EnumMap<Direction,AnimatedSprite> current=map.get(a);
		if(current==null){
			current=new EnumMap<Direction,AnimatedSprite>(Direction.class);
			map.put(a, current);
			avaliableActions.add(a);
		}
		current.put(d, animation);
		avaliableDirections.add(d);
	}
	public AnimatedSprite getAnimation(Action a, Direction d){
		return map.get(a).get(d);
	}
	
	public Set<Action> getAvaliableActions(){
		return this.avaliableActions;
	}
	public Set<Direction>getAvaliableDirection(){
		return this.avaliableDirections;
	}

}
