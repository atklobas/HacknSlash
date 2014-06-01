package Main;

import java.util.Iterator;

	public class DecayingLinkedList<T> implements Iterable<T>{
		private Node head;
		private Node tail;
		private int defaultDelay;
		int count=0;
		public int size(){
			return count;
		}
		public void setDefaultDelay(int delay){
			this.defaultDelay=delay;
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
							if(current.next!=null)current.next.previous=current.previous;
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