package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;

import consoletools.Console;
import consoletools.ConsoleListener;
import consoletools.Shell;
import Main.DecayingLinkedList;

public class MessagePane extends InternalFrame implements KeyListener,Console{
	int lines;
	int lineHeight=12;
	//LinkedList<String> messages=new LinkedList<String>();
	DecayingLinkedList<String> messages= new DecayingLinkedList<String>();
	private String command="";
	boolean listening=false;
	public MessagePane(int x, int y, int width, int height,int lines) {
		super(x+10, y+10, width, height);
		messages.setDefaultDelay(20000);
		this.lines=lines;
		try {
			in=new PipedInputStream(rawIn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Shell s=new Shell(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int max=Math.min(lines, messages.size());
		int index=0;
		g.setColor(new Color(0,0,0,255/8));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.black);
		for(String s:messages){
			g.drawString(s, 0, max*lineHeight-lineHeight*index++);
		}
		g.drawString("|"+messages.size(), 200, 10);
		g.setColor(Color.RED);
		g.drawString(command,0,this.avaliableHeight()-5);
	}
	

	public void addMessage(String s){
		messages.add(s);
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		 if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
			if(command.length()>0)
				command=command.substring(0, command.length()-1);
		}else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			command="";
			listening=false;
		}else if(listening){
			command+=e.getKeyChar();
			e.consume();
		}
		 if(e.getKeyCode()==KeyEvent.VK_ENTER){
			 
			listening=!listening;
			if(listening==false){
				if(command.length()>0)
					command=command.substring(0, command.length());
				try {
					rawIn.write(command.getBytes());
				} catch (IOException e1) {
					e1.printStackTrace();
				};
				//messages.add(command);
			}
			command="";
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(listening){
			e.consume();
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(listening){
			e.consume();
		}
		// TODO Auto-generated method stub
		
	}
	
	
	
	private class InnerStream extends OutputStream{

		public void close()throws IOException{
			//do nothing
		}
		public void flush()throws IOException{
			//do nothing(as of yet)
		}
		public void write(byte[] b)throws IOException{
			this.write(b,0,b.length);
			
		}
		public void write(byte[] b, int off, int len)throws IOException{
			byte[] temp=new byte[len];
			System.arraycopy(b, off, temp, 0, len);
			messages.add((new String(temp)));
		}
		@Override
		public void write(int arg) throws IOException {
			this.write(new byte[]{(byte)(arg&(0xFF))},0,1);
		}
	}
	PrintStream out=new PrintStream(new InnerStream());
	PipedOutputStream rawIn=new PipedOutputStream();
	PipedInputStream in;
	@Override
	public PrintStream out() {
		// TODO Auto-generated method stub
		return out;
	}

	@Override
	public InputStream in() {
		// TODO Auto-generated method stub
		return in;
	}

	@Override
	public void addConsoleListener(ConsoleListener cl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeConsoleListener(ConsoleListener cl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PrintStream err() {
		// TODO Auto-generated method stub
		return out;
	}


}
