import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author Ssai Sanjanna Ganji (szg@auburn.edu)
 *
 */
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

    //////////////////////////////////////////////////////////
    // Do not change the following three fields in any way. //
    //////////////////////////////////////////////////////////

    /** References to the first and last node of the list. */
    Node front;
    Node rear;

    /** The number of nodes in the list. */
    int size;

    /////////////////////////////////////////////////////////
    // Do not change the following constructor in any way. //
    /////////////////////////////////////////////////////////

    /**
     * Instantiates an empty LinkedSet.
     */
    public LinkedSet() {
        front = null;
        rear = null;
        size = 0;
    }


    //////////////////////////////////////////////////
    // Public interface and class-specific methods. //
    //////////////////////////////////////////////////

    ///////////////////////////////////////
    // DO NOT CHANGE THE TOSTRING METHOD //
    ///////////////////////////////////////
    /**
     * Return a string representation of this LinkedSet.
     *
     * @return a string representation of this LinkedSet
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (T element : this) {
            result.append(element + ", ");
        }
        result.delete(result.length() - 2, result.length());
        result.append("]");
        return result.toString();
    }


    ///////////////////////////////////
    // DO NOT CHANGE THE SIZE METHOD //
    ///////////////////////////////////
    /**
     * Returns the current size of this collection.
     *
     * @return  the number of elements in this collection.
     */
    public int size() {
        return size;
    }

    //////////////////////////////////////
    // DO NOT CHANGE THE ISEMPTY METHOD //
    //////////////////////////////////////
    /**
     * Tests to see if this collection is empty.
     *
     * @return  true if this collection contains no elements, false otherwise.
     */
    public boolean isEmpty() {
        return (size == 0);
    }


    /**
     * Ensures the collection contains the specified element. Neither duplicate
     * nor null values are allowed. This method ensures that the elements in the
     * linked list are maintained in ascending natural order.
     *
     * @param  element  The element whose presence is to be ensured.
     * @return true if collection is changed, false otherwise.
     */
    public boolean add(T element) {
        Node p = new Node(element);
      Node current = front;
   
      if(element == null) {
         throw new NullPointerException();
      }
      
      if(this.contains(element)) {
         return false;
      }
   
      if(isEmpty()) {
         front = p;
         rear = p;
      }
      else if (front.element.compareTo(element) > 0) {
        p.next = front;
         front.prev = p;
         front = p;
        
      }
      else if (front.element.compareTo(element) < 0) {
        
          rear.next = p;
         p.prev = rear;
         rear = p;
         
      }
      else {
         Node previous = beforeNode(element);
         previous.next.prev = p;
         p.next = previous.next;
         previous.next = p;
         p.prev = p;
        
      }
  size++;
        return true;
    }
     private Node beforeNode(T element) {
      Node q = front;
      while (q != null) {
         if (q.element.compareTo(element) > 0) {
            return q.next;
         }
         q = q.prev;
      }
      return q.next;
   }


    /**
     * Ensures the collection does not contain the specified element.
     * If the specified element is present, this method removes it
     * from the collection. This method, consistent with add, ensures
     * that the elements in the linked lists are maintained in ascending
     * natural order.
     *
     * @param   element  The element to be removed.
     * @return  true if collection is changed, false otherwise.
     */
    public boolean remove(T element) {
        Node current = front;
         if ((element == null) || (isEmpty())) {
         return false;
         }
        if (!(this.contains(element))) {
            return false;
        }
        while (current != null) {
            if (current.element.equals(element)) {
                if (current.equals(front)) {
                    front = front.next;
                    if (front == null) {
                        rear = null;
                    }
                    else if (front != null) {
                        front.prev = null;
                    }
                }
                    else if (current.equals(rear)) {
                        rear = rear.prev;
                        rear.next = null;
                    }
                    else {
                        current.prev.next = current.next;
                        current.next.prev = current.prev;
                    }
                    size--;
                    return true;
                }
                current = current.next;
            }
            return false;
    }


    /**
     * Searches for specified element in this collection.
     *
     * @param   element  The element whose presence in this collection is to be tested.
     * @return  true if this collection contains the specified element, false otherwise.
     */
    public boolean contains(T element) {
        Node current = front;
        
        if (element == null) {
            return false;
        }
        if (isEmpty()) {
            return false;
        }
        while (current != null) {
            if (current.element.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    /**
     * Tests for equality between this set and the parameter set.
     * Returns true if this set contains exactly the same elements
     * as the parameter set, regardless of order.
     *
     * @return  true if this set contains exactly the same elements as
     *               the parameter set, false otherwise
     */
    public boolean equals(Set<T> s) {
       if (size == s.size() && complement(s).size() == 0) {
           return true;
       }
        return false;
    }


    /**
     * Tests for equality between this set and the parameter set.
     * Returns true if this set contains exactly the same elements
     * as the parameter set, regardless of order.
     *
     * @return  true if this set contains exactly the same elements as
     *               the parameter set, false otherwise
     */
    public boolean equals(LinkedSet<T> s) {
      if (size != s.size()) {
         return false;
      }
        Iterator<T> itr1 = this.descendingIterator();
        Iterator<T> itr2 = s.descendingIterator();
        if (!itr1.hasNext() && !(itr2.hasNext())) {
            return true;
        }
        else if (itr1.hasNext() && itr2.hasNext()) {
            while (itr1.next().equals(itr2.next()))  {
                if (!(itr1.hasNext())) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Returns a set that is the union of this set and the parameter set.
     *
     * @return  a set that contains all the elements of this set and the parameter set
     */
    public Set<T> union(Set<T> s){
         Node current = front;
        Set<T> uSet = new LinkedSet<T>();
        Iterator<T> itr = s.iterator();
        while (itr.hasNext()) {
            uSet.add(itr.next());
        }
        while (current != null) {
            uSet.add(current.element);
            current = current.next;
        }
        return uSet;
    }


    /**
     * Returns a set that is the union of this set and the parameter set.
     *
     * @return  a set that contains all the elements of this set and the parameter set
     */
    public Set<T> union(LinkedSet<T> s){
        if(s == null) {
         throw new NullPointerException();
      }
   
      LinkedSet<T> uSet = new LinkedSet<T>();
   
      Node current = front;
   
      while(current != null) {
         uSet.add(current.element);
         current = current.next;
      }
   
      Iterator<T> itr = s.iterator();
   
      while(itr.hasNext()) {
         uSet.add(itr.next());
      }
   
      return uSet;

    }


    /**
     * Returns a set that is the intersection of this set and the parameter set.
     *
     * @return  a set that contains elements that are in both this set and the parameter set
     */
    public Set<T> intersection(Set<T> s) {
        Set<T> iSet = new LinkedSet<T>();
 
        Iterator<T> itr = this.iterator();
  
        while (itr.hasNext()) {
            T current = itr.next();
            if (s.contains(current)) {
                iSet.add(current);
            }
        }   
        return iSet;
    }

    /**
     * Returns a set that is the intersection of this set and
     * the parameter set.
     *
     * @return  a set that contains elements that are in both
     *            this set and the parameter set
     */
    public Set<T> intersection(LinkedSet<T> s) {
       Set<T> iSet1 = this.union(s);
        Set<T> iSet2 = this.complement(s);
        Set<T> iSet3 = s.complement(this);
        Set<T> iSet4 = iSet2.union(iSet3);
        Set<T> iSet5 = iSet1.complement(iSet4);
        return iSet5;
    }


    /**
     * Returns a set that is the complement of this set and the parameter set.
     *
     * @return  a set that contains elements that are in this set but not the parameter set
     */
    public Set<T> complement(Set<T> s) {
        Set<T> compSet = new LinkedSet<T>();
 
        Iterator<T> itr1 = this.iterator();
  
        while (itr1.hasNext()) {
            compSet.add(itr1.next());
        }   
  
        Iterator<T> itr2 = s.iterator();

        while (itr2.hasNext()) {
            compSet.remove(itr2.next());
        }
        return compSet;
    }

    /**
     * Returns a set that is the complement of this set and
     * the parameter set.
     *
     * @return  a set that contains elements that are in this
     *            set but not the parameter set
     */
    public Set<T> complement(LinkedSet<T> s) {
        if (s == null)
           throw new NullPointerException();
        LinkedSet<T> compSet = new LinkedSet<T>();
        Node current = front;
        while(current!=null)
        {
            if(!s.contains((T)current.element))
                compSet.add((T)current.element);
            current = current.next;
        }
        return compSet;

    }


    /**
     * Returns an iterator over the elements in this LinkedSet.
     * Elements are returned in ascending natural order.
     *
     * @return  an iterator over the elements in this LinkedSet
     */
    public Iterator<T> iterator() {
        return new AscendingIterator();
    }
    
    private class AscendingIterator implements Iterator<T> {
        private Node current = front;

        public boolean hasNext() {
            return (current != null);
        }
  
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T result = current.element;
            current = current.next;
            return result;
        }
  
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns an iterator over the elements in this LinkedSet.
     * Elements are returned in descending natural order.
     *
     * @return  an iterator over the elements in this LinkedSet
     */
    public Iterator<T> descendingIterator() {
        return new DescendingIterator(rear);
    }
    
    private class DescendingIterator implements Iterator<T> {
        private Node current;
  
        public DescendingIterator(Node n) {
            current = n;
        }

        public boolean hasNext() {
            return ((current != null) && (current.element != null));
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (current != null) {
                T result = current.element;
                current = current.prev;
                return result;
            }
            return null;
        }
  
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    /**
     * Returns an iterator over the members of the power set
     * of this LinkedSet. No specific order can be assumed.
     *
     * @return  an iterator over members of the power set
     */
    public Iterator<Set<T>> powerSetIterator() {
        return new PowerSetIterator(rear, size);
    }

    private class PowerSetIterator implements Iterator<Set<T>> {
        private Node current;
        private int siz;
        private int count;

        public PowerSetIterator(Node rear,int size) {
            current = rear;
            siz = size;
            count = 0;
        }

        public boolean hasNext() {
            if (count == 0) {
                return true;
            }
            return ((count < (int) Math.pow(2,siz)) && (current != null));
        }

        public Set<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Set<T> result = new LinkedSet<T>();
            if (count == 0) {
                count++;
                return result;
            }
            String binary = Integer.toBinaryString(count);
            for (int i = binary.length() - 1; i >= 0; i--) {
                if (binary.charAt(i) == '1') {
                    result.add(current.element);
                }
                current = current.prev;
            }
            count++;
            current = rear;
            return result;
        }
  
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    //////////////////////////////
    // Private utility methods. //
    //////////////////////////////

    // Feel free to add as many private methods as you need.
    
    private Node find(T element) {
      Node current = front;
      while (current != null) {
         if (current.element.equals(element)) {
            return current;
         }
          current = current.next;
      }
      return null;
   }
    ////////////////////
    // Nested classes //
    ////////////////////
    
    //////////////////////////////////////////////
    // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
    //////////////////////////////////////////////

    /**
     * Defines a node class for a doubly-linked list.
     */
    class Node {
        /** the value stored in this node. */
        T element;
        /** a reference to the node after this node. */
        Node next;
        /** a reference to the node before this node. */
        Node prev;

        /**
         * Instantiate an empty node.
         */
        public Node() {
            element = null;
            next = null;
            prev = null;
        }

        /**
         * Instantiate a node that containts element
         * and with no node before or after it.
         */
        public Node(T e) {
            element = e;
            next = null;
            prev = null;
        }
    }
}
