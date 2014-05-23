package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

import Main.DecayingLinkedList;

public class MessagePane extends InternalFrame{
	int lines;
	int lineHeight=12;
	//LinkedList<String> messages=new LinkedList<String>();
	DecayingLinkedList<String> messages= new DecayingLinkedList<String>();

	public MessagePane(int x, int y, int width, int height,int lines) {
		super(x, y, width, height);
		this.lines=lines;
	}

	@Override
	public void paint(Graphics g) {
		
		int max=Math.min(lines, messages.size());
		int index=0;
		g.setColor(new Color(0,0,0,255/8));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.black);
		for(String s:messages){
			g.drawString(s, 0, max*lineHeight-lineHeight*index++);
		}
		g.drawString("|"+messages.size(), 200, 10);

	}
	public void addMessage(String s){
		messages.add(s);
	}
	
	
	
	


}
