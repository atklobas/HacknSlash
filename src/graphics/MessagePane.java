package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

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
	
	
	
	
	private class DecayingLinkedList<T> implements Iterable<T>{
		private Node head;
		private Node tail;
		private int defaultDelay;
		int count=0;
		private int size(){
			return count;
		}
		public DecayingLinkedList(int defaultDelay){
			this.defaultDelay=defaultDelay;
			this.head=null;
			this.tail=null;
		}
		public DecayingLinkedList(){
			this(5000);
		}
		public void add(T item){
			this.add(item, this.defaultDelay);
		}
		public void add(T item, int delay){
			count++;
			long time=System.currentTimeMillis();
			if(head==null){
				head=new Node(null,null,item,time+delay);
			}else{
				head=new Node(null,head,item,time+delay);
				head.next.previous=head;
			}
		}
		private class Node{
			public Node(Node previous, Node next, T data, long decayTime){
				this.previous=previous;
				this.next=next;
				this.data=data;
				this.decayTime=decayTime;
			}
			Node previous;
			Node next;
			T data;
			long decayTime;
		}
		@Override
		public Iterator<T> iterator() {
			return new DecayIterator(this);
		}
		private class DecayIterator implements Iterator<T>{
			public Node current;
			DecayingLinkedList list;
			Long time=System.currentTimeMillis();
			public DecayIterator(DecayingLinkedList list) {
				current=list.head;
				this.list=list;
			}
			
			
			
			@Override
			public boolean hasNext() {
				if(current!=null){
					if(current.decayTime<time){
						if(current.previous==null){
							list.head=current.next;
							if(list.head!=null)list.head.previous=null;
							list.count--;
						}else{
							list.count--;
							current.previous.next=current.next;
						}
						current=current.next;
						return hasNext();
					}
					return true;
				}

				return false;
			}

			@Override
			public T next() {
				Node ret=current;
				
				current=current.next;
				return ret.data;
			}

			@Override
			public void remove() {
				//things are removed automatically
			}
		}
		
		
		
		
	}

}
