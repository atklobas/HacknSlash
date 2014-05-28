package interaction;

import java.util.EnumMap;

public class InputBinder {
	EnumMap<Input,Command> map= new EnumMap<Input,Command>(Input.class);
	
	public Command getCommand(Input i){
		return map.get(i);
	}
	public void bind(Input i, Command c){
		map.put(i, c);
	}

}
