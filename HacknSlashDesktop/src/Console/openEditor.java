package Console;

import view.spriteEditor.SpriteEditorFrame;
import Main.Game;
import console.Shell;
import console.ShellProgram;

public class openEditor implements ShellProgram {

	public int Execute(Shell s, String... args) throws Exception {
		SpriteEditorFrame frame=new SpriteEditorFrame();
		s.addCommand("load", new loadImage(frame));
		s.addCommand("xml", new dumpXML(frame));
		return 0;
	}

}
