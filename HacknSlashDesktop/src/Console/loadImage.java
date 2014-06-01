package Console;

import java.io.File;

import view.spriteEditor.SpriteEditorFrame;
import console.Shell;
import console.ShellProgram;

public class loadImage implements ShellProgram{
	SpriteEditorFrame f;
	public loadImage(SpriteEditorFrame f){
		this.f=f;
	}
	public int Execute(Shell s, String... args) {
		if(args.length>0){
			File file=new File(s.currentDirectory().getAbsolutePath()+"/"+args[0]);
			s.out.println("loading"+file.getAbsolutePath());
			f.loadImage(file);
			return 0;
		}
		s.out.println("no file specified");
		return 1;
	}
}
