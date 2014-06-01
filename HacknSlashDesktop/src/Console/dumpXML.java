package Console;

import view.spriteEditor.SpriteEditorFrame;
import console.Shell;
import console.ShellProgram;

public class dumpXML implements ShellProgram{
	SpriteEditorFrame f;
	public dumpXML(SpriteEditorFrame f){
		this.f=f;
	}
	public int Execute(Shell s, String... args) {
		f.dumpXML();
		return 0;
	}

}