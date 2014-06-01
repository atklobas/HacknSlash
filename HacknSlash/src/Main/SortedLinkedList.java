package Main;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SortedLinkedList<T>implements Iterable<T>{
	Node head;
	int count=0;
	
	public void add(T d, double rank){
		count++;
		if(head==null){
			head=new Node();
			head.data=d;
			head.rank=rank;
		}else{
			Node current=head;
			Node previous=null;
			while(current!=null&&current.rank<rank){
				previous=current;
				current=current.next;
			}
			Node n=new Node();
			n.data=d;
			n.rank=rank;
			n.next=current;
			if(previous!=null){
				previous.next=n;
			}else{
				head=n;
			}
			
			
		}
	}
	public T getFirst(){
		if(head!=null){
			return head.data;
		}
		return null;
	}
	public double getFirstRank(){
		if(head!=null){
			return head.rank;
		}
		return Double.MAX_VALUE;
	}
	public int getCount(){
		return count;
	}
	
	private class Node{
		double rank;
		T data;
		Node next;
	}
	private class itr implements Iterator<T>{
		Node current;
		public itr(SortedLinkedList s){
			current=s.head;
		}
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current!=null;
		}

		@Override
		public T next() {
			Node temp=current;
			current=current.next;
			return temp.data;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new itr(this);
	}

	
	
	


}
