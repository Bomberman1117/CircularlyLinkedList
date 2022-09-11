import java.util.NoSuchElementException;

//Adam Buerger
//Creates a Circular LinkedList based on MyLinkedList.java

public class MyCircularLinkedList<E> implements MyList<E> {
    private Node<E> tail;
    private int size = 0; // Number of elements in the list

    /** Create an empty list */
    public MyCircularLinkedList() {}
    /** Create a list from an array of objects */
    public MyCircularLinkedList(E[] objects) {
        for (int i = 0; i < objects.length; i++)
            add(objects[i]);
    }
    /** Return the head element in the list */
    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        else {
            return tail.next.element;
        }
    }
    /** Return the last element in the list */
    public E getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        else {
            return tail.element;
        }
    }
    /** Add an element to the beginning of the list */
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e); // Create a new node
        if (tail == null) // the new node is the only node in list
            tail = newNode;
        newNode.next = tail.next;
        tail.next = newNode; // head points to the new node
        size++; // Increase list size


    }
    /** Add an element to the end of the list */
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e); // Create a new for element e
        if (tail == null) {
             tail = newNode; // The new node is the only node in list
             tail.next = tail;
        }
        else {
        	newNode.next = tail.next;
            tail.next = newNode; // Link the new with the last node
            tail = newNode; // tail now points to the last node
        }
        size++; // Increase size
    }
    @Override /** Add a new element at the specified index
     * in this list. The index of the head element is 0 */
    public void add(int index, E e) {
    	if(index < 0 || index > size) {
    		throw new IndexOutOfBoundsException();
    	}
        if (index == 0) {
            addFirst(e);
        }
        else if (index >= size) {
            addLast(e);
        }
        else {
            Node<E> current = tail.next;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> temp = current.next;
            current.next = new Node<>(e);
            (current.next).next = temp;
            size++;
        }
    }
   /** Remove the head node and
     *  return the object that is contained in the removed node. */
    public E removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        else {
            E temp = tail.next.element;
            tail.next = tail.next.next;
            size--;
            if (tail.next == null) {
                tail = null;
            }
            return temp;
        }
    }
    /** Remove the last node and
     * return the object that is contained in the removed node. */
    public E removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        else if (size == 1) {
            E temp = tail.element;
            tail = null;
            size = 0;
            return temp;
        }
        else {
            E temp = tail.element;
            Node<E> current = tail.next;
            for(int i = 0; i < size-2;i++)
            	current = current.next;
            System.out.println(current.element);
            current.next = tail.next;
            tail = current;
            size--;
            return temp;
        }
    }
    @Override /** Remove the element at the specified position in this
     *  list. Return the element that was removed from the list. */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        else if (index == 0) {
            return removeFirst();
        }
        else if (index == size - 1) {
            return removeLast();
        }
        else {
            Node<E> previous = tail.next;

            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }

            Node<E> current = previous.next;
            previous.next = current.next;
            size--;
            return current.element;
        }
    }
    @Override /** Override toString() to return elements in the list */
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        if(size == 0) {
        	result.append("]");
        	return result.toString();
        }
        Node<E> current = tail.next;
        for (int i = 0; i < size; i++) {
        	result.append(current.element);
            current = current.next;
            if (current != null && i < size-1) {
                result.append(", "); // Separate two elements with a comma
            }
            else {
                result.append("]"); // Insert the closing ] in the string
            }
        }

        return result.toString();
    }
    @Override /** Clear the list */
    public void clear() {
        size = 0;
        tail = null;
    }
    @Override /** Return true if this list contains the element e */
    public boolean contains(Object e) {
    	if(size == 0)
    		return false;
    	Node<E> current = tail.next;
    	for(int i = 0; i < size; i++) {
    		if(current.element == e)
    			return true;
    		current = current.next;
    	}
    	return false;
    }
    @Override /** Return the element at the specified index */
    public E get(int index) {
    	if(tail == null || (index < 0 || index >= size))
    		throw new IndexOutOfBoundsException();
    	Node<E> current = tail.next;
        for(int i = 0; i < index; i++)
        	current = current.next;
        return current.element;
    }
    @Override /** Return the index of the head matching element in
     *  this list. Return -1 if no match. */
    public int indexOf(Object e) {
    	if(size == 0)
    		return -1;
    	Node<E> current = tail.next;
    	for(int i = 0; i < size; i++) {
    		if(current.element == e)
    			return i;
    		else
    			current = current.next;
    	}
    	return -1;
    }
    @Override /** Return the index of the last matching element in
     *  this list. Return -1 if no match. */
    public int lastIndexOf(E e) {
    	if(size == 0)
    		return -1;
    	Node<E> current = tail.next;
    	int indexOfE = -1;
        for(int i = 0; i < size; i++) {
        	if(current.element == e)
        		indexOfE = i;
        	current = current.next;
        }
        return indexOfE;
    }
    @Override /** Replace the element at the specified position
     *  in this list with the specified element. */
    public E set(int index, E e) {
    	if(index < 0 || index >= size)
    		throw new IndexOutOfBoundsException();
    	Node<E> current = tail.next;
    	for(int i = 0; i < index; i++) {
    		current = current.next;
    	}
    	current.element = e;
    	return current.element;
    }

    @Override /** Override iterator() defined in Iterable */
    public java.util.Iterator<E> iterator() {
        return new LinkedListIterator();
    }
    private class LinkedListIterator implements java.util.Iterator<E> {
        private Node<E> current = tail.next; // Current index

        @Override
        public boolean hasNext() {
            return (current != tail);
        }

        @Override
        public E next() {
            E e = current.element;
            current = current.next;
            return e;
        }

        @Override
        public void remove() {
            // Left as an exercise
        }
    }
    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }
    @Override /** Return the number of elements in this list */
    public int size() {
        return size;
    }
}