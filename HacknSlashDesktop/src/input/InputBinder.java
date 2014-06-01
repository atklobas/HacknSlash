package input;

import java.util.EnumMap;
import java.util.HashMap;

import external.io.Command;

public class InputBinder {
	HashMap<Integer,Command> map= new HashMap<Integer,Command>();
	
	public Command getCommand(int i){
		return map.get(i);
	}
	public void bind(int i, Command c){
		map.put(i, c);
	}

}
