import java.util.Iterator;
import java.util.NoSuchElementException;

public class Map1Wrapper {

    public interface List<E> extends Iterable<E> {

        public int size();

        public boolean isEmpty();

        public boolean isMember(E e);

        public void add(E e);

        public void add(E e, int index);

        public E first();

        public E last();

        public int firstIndex(E e);

        public int lastIndex(E e);

        public E get(int index);

        public E replace(E e, int index);

        public E remove(int index);

        public boolean remove(E e);

        public int removeAll(E e);

        public void clear();

        public Object[] toArray();


    }

    public static class SinglyLinkedList<E> implements List<E> {

        @SuppressWarnings("hiding")
        private class SinglyLinkedListIterator<E> implements Iterator<E>{

            private Node<E> nextNode;


            @SuppressWarnings("unchecked")
            public SinglyLinkedListIterator() {
                super();
                this.nextNode = (Node<E>) header.getNext();
            }

            @Override
            public boolean hasNext() {
                return this.nextNode != null;
            }

            @Override
            public E next() {
                if (this.hasNext()) {
                    E result = this.nextNode.getElement();
                    this.nextNode = this.nextNode.getNext();
                    return result;
                }
                else {
                    throw new NoSuchElementException();
                }
            }

        }


        // node class
        private static class Node<E>{
            private E element;
            private Node<E> next;
            public Node(E element, Node<E> next) {
                super();
                this.element = element;
                this.next = next;
            }
            public Node() {
                super();
                this.element = null;
                this.next = null;

            }
            public E getElement() {
                return element;
            }
            public void setElement(E element) {
                this.element = element;
            }
            public Node<E> getNext() {
                return next;
            }
            public void setNext(Node<E> next) {
                this.next = next;
            }


        }

        // private fields
        private Node<E> header;
        private int currentSize;



        public SinglyLinkedList() {
            this.header = new Node<>();
            this.currentSize = 0;

        }

        @Override
        public Iterator<E> iterator() {
            return new SinglyLinkedListIterator<E>();
        }

        @Override
        public int size() {
            return this.currentSize;
        }

        @Override
        public boolean isEmpty() {
            return this.size() == 0;
        }

        @Override
        public boolean isMember(E e) {
            return this.firstIndex(e) >= 0;
        }

        @Override
        public void add(E e) {

            Node<E> temp = this.header;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(new Node<E>(e, null));
            this.currentSize++;

            // cool
            //this.getPosition(this.size() -1).setNext(new Node<E>(e, null));
            //this.currentSize++;
        }

        @Override
        public void add(E e, int index) {
            if ((index <0) || (index > this.size())){
                throw new IndexOutOfBoundsException();
            }
            if (index == this.size()) {
                this.add(e);
            }
            else {
                Node<E> temp = null;
                if (index == 0) {
                    temp = this.header;
                }
                else {
                    temp = this.getPosition(index -1);
                }
                // add new node
                Node<E> newNode = new Node<>();
                newNode.setElement(e);
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);
                this.currentSize++;
            }
        }

        @Override
        public E first() {
            if (this.isEmpty()) {
                return null;
            }
            else {
                return this.header.getNext().getElement();
            }
        }

        @Override
        public E last() {
            if (this.isEmpty()) {
                return null;
            }
            else {
                Node<E> temp = this.header.getNext();
                while (temp.getNext() != null) {
                    temp = temp.getNext();
                }

//				Cool code!
//				Node<E> temp;
//				for (temp = this.header.getNext();
//						temp.getNext() != null;
//						temp = temp.getNext());


                return temp.getElement();
            }
        }

        @Override
        public int firstIndex(E e) {
            int currentPosition = 0;
            Node<E> temp = this.header.getNext();

            while(temp != null) {
                if (temp.getElement().equals(e)) {
                    return currentPosition;
                }
                else {
                    temp = temp.getNext();
                    currentPosition++;
                }
            }
            return -1;

        }

        @Override
        public int lastIndex(E e) {
            int i=0;
            int lastIndex = -1;
            Node<E> temp = this.header.getNext();
            while (temp != null) {
                if (temp.getElement().equals(e)) {
                    lastIndex = i;
                }
                i++;
                temp = temp.getNext();
            }
            return lastIndex;
        }

        private Node<E> getPosition(int index){
            int currentPosition = 0;
            Node<E> temp = this.header.getNext();

            while (currentPosition != index) {
                temp = temp.getNext();
                currentPosition++;
            }
            return temp;
        }

        @Override
        public E get(int index) {
            if ((index <0) || (index >= this.currentSize)) {
                throw new IndexOutOfBoundsException();
            }
            return this.getPosition(index).getElement();
        }

        @Override
        public E replace(E e, int index) {
            if ((index <0) || (index >= this.currentSize)) {
                throw new IndexOutOfBoundsException();
            }
            Node<E> temp = this.getPosition(index);
            E result = temp.getElement();
            temp.setElement(e);
            return result;
        }

        @Override
        public E remove(int index) {
            if ((index < 0) || (index >= this.currentSize)){
                throw new IndexOutOfBoundsException();
            }
            int currentPosition =0;
            Node<E> temp = this.header;
            while(currentPosition != index) {
                temp = temp.getNext();
                currentPosition++;
            }
            Node<E> target = temp.getNext();
            E result = target.getElement();
            temp.setNext(target.getNext());
            target.setElement(null);
            target.setNext(null);
            this.currentSize--;
            return result;

        }

        @Override
        public boolean remove(E e) {
            int target = this.firstIndex(e);
            if (target < 0) {
                return false;
            }
            else {
                this.remove(target);
                return true;
            }
            // cool!
            // return (this.firstIndex(e) < 0 ? false :
            //	(this.remove(this.firstIndex(e)) != null));
        }

        @Override
        public int removeAll(E e) {
            int count = 0;
            while (this.remove(e)) {
                count++;
            }
            return count;
        }

        @Override
        public void clear() {
            while(!this.isEmpty()) {
                this.remove(0);
            }

        }

        @Override
        public Object[] toArray() {
            Object[] result = new Object[this.size()];
            for (int i=0; i < this.size(); ++i) {
                result[i]= this.get(i);
            }
            return result;
        }

    }

    public interface Map<K, V> {

        public int size();

        public boolean isEmpty();

        public V get(K key);

        public V put(K key, V value);

        public V remove(K key);

        public boolean contains(K key);

        public void clear();

        public List<K> getKeys();

        public List<V> getValues();



    }

    @SuppressWarnings("unchecked")
    public static class SinglyLinkedMap<K,V> implements Map<K, V> {


        public static class MapEntry<K,V> {
            private K key;
            private V value;

            public K getKey() {
                return key;
            }

            public void setKey(K key) {
                this.key = key;
            }

            public V getValue() {
                return value;
            }

            public void setValue(V value) {
                this.value = value;
            }

            public MapEntry(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private int currentSize;

        private List<MapEntry<K,V>> elements;

        public SinglyLinkedMap() {

            this.elements = new SinglyLinkedList<>();
        }

        @Override
        public int size() {
            return this.elements.size();
        }

        @Override
        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        @Override
        public V get(K key) {
            if (this.isEmpty()){
                return null;
            }
            else {
                int position = findPosition(key);
                if (position < 0){
                    return null;
                }
                else {
                    MapEntry<K, V> entry = this.elements.get(position);
                    return entry.getValue();
                }
            }
        }

        private int findPosition(K key) {
            int result = 0;
            for (MapEntry<K,V> e: this.elements){
                if (e.getKey().equals(key)){
                    return result;
                }
                else {
                    result++;
                }
            }
            return -1;
        }

        @Override
        public V put(K key, V value) {
            // erase old key
            V result = this.remove(key);

            MapEntry<K,V> newEntry = new MapEntry<>(key, value);
            this.elements.add(newEntry, 0);
            return result;
        }

        @Override
        public V remove(K key) {
            int target = findPosition(key);
            if (target < 0){
                return null;
            }
            else {
//            V result = this.elements.get(target).getValue();
//            this.elements.remove(target);
//            return result;

                return this.elements.remove(target).getValue();
            }
        }

        @Override
        public boolean contains(K key) {
            return this.get(key) != null;
        }

        @Override
        public void clear() {
            this.elements.clear();


        }

        @Override
        public List<K> getKeys() {
            List<K> result = new SinglyLinkedList<>();

            for (MapEntry<K,V> e : this.elements){
                result.add(e.getKey(), 0);
            }
            return result;
        }

        @Override
        public List<V> getValues() {
            List<V> result = new SinglyLinkedList<>();

            for (MapEntry<K,V> e : this.elements){
                result.add(e.getValue(), 0);
            }
            return result;
        }
    }


    /*
     * Write a non-member method dataFrequency which receives a list of strings L.
     * The method returns a map containing String, Integer value pairs, such that
     * each string is present in the list L, and the accompanying integer is the number
     * of times (frequency) that the string appears in L.
     * For example if L = {Joe, Ned, Ron, Ron, Joe, Bob, Ned, Ron}, then map will have the
     * following key-value pairs: {(Joe, 2), (Ned, 2), (Ron, 3), (Bob, 1)}.
     */
    public static  Map<String,Integer> dataFrequency(List<String> L){
        Map<String, Integer> result = new SinglyLinkedMap<String, Integer>();
        int count = 0;
        for(int i = 0; i < L.size();i++){
            for(int j = 0; j<L.size();j++){
                if(L.get(i).equals(L.get(j))){
                    count++;
                }
            }
            result.put(L.get(i), count);
            count = 0;
            L.removeAll(L.get(i));
            i--;
        }
        return result;




    }

//
//    // ADD YOUR CODE HERE
//    List<String> result = new SinglyLinkedList<>();
//        if (M.isEmpty()){
//        return result;
//    }
//        for (int i = searchKey; i > 0; i--) {
//        if (M.contains(i)){
//            result.add(M.get(i));
//        }
//    }
//        return result;
}