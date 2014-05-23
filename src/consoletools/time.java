package consoletools;

import Main.Main;

public class time implements ShellProgram {
	Main main;
	public time(Main main){
		this.main=main;
	}
	@Override
	public int Execute(Shell s, String... args) throws Exception {
		main.delay=Integer.parseInt(args[0]);
		return 0;
	}

}
