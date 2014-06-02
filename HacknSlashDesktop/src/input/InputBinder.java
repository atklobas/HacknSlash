package input;

import java.util.EnumMap;
import java.util.HashMap;

import external.io.Command;
import external.io.PlayerCommand;

public class InputBinder {
	HashMap<Integer,PlayerCommand> map= new HashMap<Integer,PlayerCommand>();
	
	public PlayerCommand getCommand(int i){
		return map.get(i);
	}
	public void bind(int i, PlayerCommand c){
		map.put(i, c);
	}

}
