import java.util.ListIterator;
import java.util.*;
//Adam Buerger
//MyDoublyLinkedList
//Homework 1
//Implements a doubly linked list
public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E>implements Cloneable {
	private Node<E> head = new Node<E>(null);
	private int size = 0;
	public MyDoublyLinkedList() {
		head.next = head;
		head.previous = head;
	}
	public MyDoublyLinkedList(E[] objects) {
		for(int i = 0; i < objects.length; i++)
			add(objects[i]);
	}
	@Override
	public int size() {
		return size;
	}
	@Override
	public boolean equals(Object o) {
		MyDoublyLinkedList temp = (MyDoublyLinkedList) o;
		ListIterator it1 = listIterator();
		ListIterator it2 = temp.listIterator();
		if(temp.size != size)
			return false;
		while(it1.hasNext())
			try {
				if(!it1.next().equals(it2.next()))
					return false;
			}catch(NullPointerException ex) {
				if(this.get(it1.previousIndex()) != temp.get(it2.previousIndex()))
					return false;
			}
		return true;
	}
	@Override
	public void add(int index, E e) {
		if(index > size)
			throw new IndexOutOfBoundsException();
		else {
			
			if(index == 0) {
				addFirst(e);
			}
			else {
				Node temp = new Node(e);
				Node current = head.next.next;
				for(int i = 1; i <= index; i++) {
					if(i == index) {
						temp.next = current;
						temp.previous = current.previous;
						current.previous.next = temp;
						current.previous = temp;
					}
					current = current.next;
				}
				size++;
			}
		}
	}
	@Override
	public void add(E e) {
		addLast(e);
	}
	@Override
	public void clear() {
		head.next = head;
		head.previous = head;
		size = 0;
	}
	@Override
	public boolean contains(E e) {
		if(size == 0)
			return false;
		Node current = head.next;
		for(int i = 0; i < size; i++) {
			if(current.element == e)
				return true;
			else
				current = current.next;
		}
		return false;
	}
	@Override
	public E get(int index) {
		if(index >= size || index < 0)
			throw new IndexOutOfBoundsException();

		Node current = head.next;
		for(int i = 0; i <= index; i++) {
			if(i == index)
				return (E) current.element;
			current = current.next;
		}
		return null;
	}
	@Override
	public int indexOf(E e) {
		Node current = head.next;
		for(int i = 0; i < size; i++) {
			if(current.element == e) {
				return i;
			}
			current = current.next;
		}
		return -1;
	}
	@Override
	public int lastIndexOf(E e) {
		Node current = head.previous;
		for(int i = size-1; i > -1; i--) {
			if(current.element == e)
				return i;
			current = current.previous;
		}
		return -1;
	}
	@Override
	public E remove(int index) {
		Node current = head.next;

		if(index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		else if(index == 0)
			removeFirst();
		else if(index == size)
			removeLast();
		else {
			for(int i = 0; i <= index; i++) {
				if(i == index) {
					current.previous.next = current.next;
					current.next.previous = current.previous;
					size--;
					break;
				}
				current = current.next;
			}
		}
		return (E) current.element;
	}
	@Override
	public boolean remove(E e) {
		Node current = head.next;
		for(int i = 0; i < size; i++) {
			if(current.element == e) {
				current.next.previous = current.previous;
				current.previous.next = current.next;
				size--;
				return true;
			}
			current = current.next;
		}
		return false;
	}
	@Override
	public Object set(int index, E e) {
		if(index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		Node current = head.next;
		for(int i = 0; i <= index; i++) {
			if(i == index)
				current.element = e;
			current = current.next;
		}
		return null;
	}
	@Override
	public E getFirst() {
		return head.next.element;
	}
	@Override
	public E getLast() {
		return head.previous.element;
	}
	@Override
	public void addFirst(E e) {
		Node temp = new Node(e);
		head.next.previous = temp;
		temp.next = head.next;
		temp.previous = head;
		head.next = temp;
		size++;
	}
	@Override
	public void addLast(E e) {
		if(size == 0) {
			Node temp = new Node(e);
			head.next = temp;
			head.previous = temp;
			temp.next = head;
			temp.previous = head;
		}
		else {
			Node temp = new Node(e);
			temp.previous = head.previous;
			head.previous.next = temp;
			temp.next = head;
			head.previous = temp;
		}
		size++;
	}
	@Override
	public E removeFirst() {
		if(size > 0) {
			E temp = head.next.element;
			head.next = head.next.next;
			head.next.previous = head;
			size--;
			return temp;
		}
		else {
			throw new NoSuchElementException();
		}
	}
	@Override
	public E removeLast() {
		if(size > 0) {
			E temp = head.previous.element;
			head.previous = head.previous.previous;
			head.previous.next = head;
			size--;
			return temp;
		}
		else {
			throw new NoSuchElementException();
		}
	}
	@Override
	public ListIterator<E> listIterator(int index) {	
		return new MyDoublyLinkedIterator(index);
	}
	public String toString() {
		StringBuilder result = new StringBuilder("[");
        if(size == 0) {
        	result.append("]");
        	return result.toString();
        }
        Node<E> current = head.next;
        for (int i = 0; i < size; i++) {
        	result.append(current.element);
            current = current.next;
            if (current != head) {
                result.append(", "); // Separate two elements with a comma
            }
            else {
                result.append("]"); // Insert the closing ] in the string
            }
        }
        return result.toString();
	}
	public Object clone() {
		try {
			MyDoublyLinkedList<E> listClone = (MyDoublyLinkedList<E>) super.clone();
			listClone.head = new Node<E>();
			listClone.head.next = listClone.head;
			listClone.head.previous = listClone.head;
			listClone.size = 0;
			for(E e:this) {
				listClone.add(e);
			}
			return listClone;
		}catch(CloneNotSupportedException ex) {
			return null;
		}
	}
    private class MyDoublyLinkedIterator implements java.util.ListIterator<E> {
    	private Node<E> next = head.next;
    	private Node<E> previous = next.previous;
    	private Node<E> recent = null;
    	private int nextIndex = 0, previousIndex = nextIndex-1;
    	
    	public MyDoublyLinkedIterator(int index) {
    		nextIndex = index;
    		for(int i = 0; i < nextIndex; i++)
    			next = next.next;
    		previous = next.previous;
    		previousIndex = nextIndex-1;
    	}

		@Override
		public void add(E e) {
			recent = null;
			if(size == 0)
				addFirst(e);
			else {
				Node temp = new Node(e);
				temp.next = next;
				temp.previous = previous;
				next.previous = temp;
				previous.next = temp;
				previous = temp;
				nextIndex++;
				previousIndex++;
				size++;
			}
		}
		@Override
		public boolean hasNext() {
			return nextIndex < size;
		}
		@Override
		public boolean hasPrevious() {
			return previousIndex >= 0;
		}
		@Override
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			next = next.next;
			previous = next.previous;
			nextIndex++;
			previousIndex++;
			recent = previous;
			return previous.element;
		}
		@Override
		public int nextIndex() {
			return nextIndex;
		}
		@Override
		public E previous() {
			if(!hasPrevious())
				throw new NoSuchElementException();
			next = previous;
			previous = next.previous;
			nextIndex--;
			previousIndex--;
			recent = next;
			return next.element;
		}
		@Override
		public int previousIndex() {
			return previousIndex;
		}
		@Override
		public void remove() {
			if(recent == null)
				throw new IllegalStateException();
			recent.next.previous = recent.previous;
			recent.previous.next = recent.next;
			nextIndex--;
			previousIndex--;
			if(next == recent)
				next = next.next;
			recent = null;
			size--;
		}
		@Override
		public void set(E e) {
			if(recent == null)
				throw new IllegalStateException();
			recent.element = e;
		}    	
	}
	private static class Node<E>{
		E element;
		Node<E> next;
		Node<E> previous;
		public Node(E element) {
			this.element = element;
		}
		public Node() {element = null;}
		public String toString() {
			return element.toString();
		}
	}
}
