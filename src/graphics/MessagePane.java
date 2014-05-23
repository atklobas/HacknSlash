package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;

import Main.DecayingLinkedList;

public class MessagePane extends InternalFrame implements KeyListener{
	int lines;
	int lineHeight=12;
	//LinkedList<String> messages=new LinkedList<String>();
	DecayingLinkedList<String> messages= new DecayingLinkedList<String>();
	private String command="";
	boolean listening=false;
	public MessagePane(int x, int y, int width, int height,int lines) {
		super(x+10, y+10, width, height);
		this.lines=lines;
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
				messages.add(command);
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
	
	
	
	


}
