import input.InputBinder;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import resources.PC_ResourceLoader;
import Console.dumpXML;
import Console.loadImage;
import Console.openEditor;
import view.GamePanel;
import view.LoadingWindow;
import view.spriteEditor.SpriteEditorFrame;
import console.Console;
import console.ConsoleListener;
import console.Shell;
import console.ShellProgram;
import external.io.Command;
import external.io.PlayerCommand;
import external.io.PointerLocation;
import external.io.commands.Move;
import Main.Game;

/**
 * A starting point
 * 
 * this class should contain no logic, it should instantiate whatever object
 * controls the application
 * 
 * @author Anthony Klobas
 * 
 */
public class Main implements MouseListener, MouseMotionListener {

	public static final int width = 800;
	public static final int height = 600;
	private static Shell s = new Shell();

	public static void main(String[] args) {
		
		s.addCommand("editor", new openEditor());
		new Main();
		
	}

	private GamePanel panel;
	private Game game;
	private PointerLocation pointer;
	private InputBinder binder = new InputBinder();

	public Main() {
		LoadingWindow w= new LoadingWindow();
		
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new GamePanel(width, height);
		frame.add(panel);

		game = new Game(s, panel, new PC_ResourceLoader());
		game.addObserver(w);
		game.initialize();
		pointer = new PointerLocation(0, 0);
		frame.pack();
		game.removeObserver(w);
		w.dispose();
		frame.setVisible(true);
		

		panel.requestFocus();
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);

		
		binder.bind(MouseEvent.BUTTON1, new Move(pointer));

		
	}
	private void updatePointer(MouseEvent e){
		pointer.changeLocation(e.getX()-width/2, e.getY()-height/2);
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		this.updatePointer(e);
		
	}

	public void mouseExited(MouseEvent e) {
		this.updatePointer(e);

	}

	public void mousePressed(MouseEvent e) {
		this.updatePointer(e);
		PlayerCommand temp=binder.getCommand(e.getButton());
		temp.held(true);
		game.addCommand(temp);

	}

	public void mouseReleased(MouseEvent e) {
		this.updatePointer(e);
		PlayerCommand temp=binder.getCommand(e.getButton());
		temp.held(false);

	}

	public void mouseDragged(MouseEvent e) {
		this.updatePointer(e);

	}

	public void mouseMoved(MouseEvent e) {
		this.updatePointer(e);

	}

}
