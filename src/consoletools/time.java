package consoletools;

import Main.Main;

public class time implements ShellProgram {
	Main main;
	public time(Main main){
		this.main=main;
	}
	@Override
	public int Execute(Shell s, String... args) throws Exception {
		int delay=Integer.parseInt(args[0]);
		if(delay<1){
			main.pause();
		}else{
			main.delay=delay;
			main.unPause();
		}
		
		return 0;
	}

}
