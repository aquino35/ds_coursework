import java.util.AbstractList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class Lab04P3Wrapper {

    public static interface List<E> {

        public int size();

        public boolean isEmpty();

        public boolean contains(Object o);

        public Iterator<E> iterator();

        public Object[] toArray();

        public <T> T[] toArray(T[] a);

        public boolean add(E e);

        public boolean remove(Object o);

        public boolean containsAll(Collection<?> c);

        public boolean addAll(Collection<? extends E> c);

        public boolean addAll(int index, Collection<? extends E> c);

        public boolean removeAll(Collection<?> c);

        public boolean retainAll(Collection<?> c);

        public void clear();

        public boolean equals(Object o);

        public int hashCode();

        public E get(int index);

        public E set(int index, E element);

        public void add(int index, E element);

        public E remove(int index);

        public int indexOf(Object o);

        public int lastIndexOf(Object o);

        public int replaceAll(E e, E f);
    }

    public static class ArrayList<E> extends AbstractList<E> implements List<E> {
        private static final int DEFAULT_CAPACITY = 10;

        private static final Object[] EMPTY_ELEMENTDATA = {};

        transient Object[] elementData; // non-private to simplify nested class access

        private int size;

        public ArrayList(int initialCapacity) {

            super();

            if (initialCapacity < 0)

                throw new IllegalArgumentException("Illegal Capacity: " +

                        initialCapacity);

            this.elementData = new Object[initialCapacity];

        }

        public ArrayList() {

            super();

            this.elementData = EMPTY_ELEMENTDATA;

        }

        public ArrayList(Collection<? extends E> c) {

            elementData = c.toArray();

            size = elementData.length;

            // c.toArray might (incorrectly) not return Object[] (see 6260652)

            if (elementData.getClass() != Object[].class)

                elementData = Arrays.copyOf(elementData, size, Object[].class);

        }

        public void trimToSize() {

            modCount++;

            if (size < elementData.length) {

                elementData = Arrays.copyOf(elementData, size);

            }

        }

        public void ensureCapacity(int minCapacity) {

            int minExpand = (elementData != EMPTY_ELEMENTDATA)

                    // any size if real element table

                    ? 0

                    // larger than default for empty table. It's already supposed to be

                    // at default size.

                    : DEFAULT_CAPACITY;


            if (minCapacity > minExpand) {

                ensureExplicitCapacity(minCapacity);

            }

        }

        private void ensureCapacityInternal(int minCapacity) {

            if (elementData == EMPTY_ELEMENTDATA) {

                minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);

            }


            ensureExplicitCapacity(minCapacity);

        }

        private void ensureExplicitCapacity(int minCapacity) {

            modCount++;


            // overflow-conscious code

            if (minCapacity - elementData.length > 0)

                grow(minCapacity);

        }

        private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

        private void grow(int minCapacity) {

            // overflow-conscious code

            int oldCapacity = elementData.length;

            int newCapacity = oldCapacity + (oldCapacity >> 1);

            if (newCapacity - minCapacity < 0)

                newCapacity = minCapacity;

            if (newCapacity - MAX_ARRAY_SIZE > 0)

                newCapacity = hugeCapacity(minCapacity);

            // minCapacity is usually close to size, so this is a win:

            elementData = Arrays.copyOf(elementData, newCapacity);

        }

        private static int hugeCapacity(int minCapacity) {

            if (minCapacity < 0) // overflow

                throw new OutOfMemoryError();

            return (minCapacity > MAX_ARRAY_SIZE) ?

                    Integer.MAX_VALUE :

                    MAX_ARRAY_SIZE;

        }

        public int size() {

            return size;

        }

        public boolean isEmpty() {

            return size == 0;

        }

        public boolean contains(Object o) {

            return indexOf(o) >= 0;

        }

        public int indexOf(Object o) {

            if (o == null) {

                for (int i = 0; i < size; i++)

                    if (elementData[i] == null)

                        return i;

            } else {

                for (int i = 0; i < size; i++)

                    if (o.equals(elementData[i]))

                        return i;

            }

            return -1;

        }

        public int lastIndexOf(Object o) {

            if (o == null) {

                for (int i = size - 1; i >= 0; i--)

                    if (elementData[i] == null)

                        return i;

            } else {

                for (int i = size - 1; i >= 0; i--)

                    if (o.equals(elementData[i]))

                        return i;

            }

            return -1;

        }

        public Object[] toArray() {

            return Arrays.copyOf(elementData, size);

        }

        @SuppressWarnings("unchecked")

        public <T> T[] toArray(T[] a) {

            if (a.length < size)

                // Make a new array of a's runtime type, but my contents:

                return (T[]) Arrays.copyOf(elementData, size, a.getClass());

            System.arraycopy(elementData, 0, a, 0, size);

            if (a.length > size)

                a[size] = null;

            return a;

        }

        @SuppressWarnings("unchecked")
        E elementData(int index) {

            return (E) elementData[index];

        }

        public E get(int index) {

            rangeCheck(index);


            return elementData(index);

        }

        public E set(int index, E element) {

            rangeCheck(index);


            E oldValue = elementData(index);

            elementData[index] = element;

            return oldValue;

        }

        public boolean add(E e) {

            ensureCapacityInternal(size + 1);  // Increments modCount!!

            elementData[size++] = e;

            return true;

        }

        public void add(int index, E element) {

            rangeCheckForAdd(index);


            ensureCapacityInternal(size + 1);  // Increments modCount!!

            System.arraycopy(elementData, index, elementData, index + 1,

                    size - index);

            elementData[index] = element;

            size++;

        }

        public E remove(int index) {

            rangeCheck(index);


            modCount++;

            E oldValue = elementData(index);


            int numMoved = size - index - 1;

            if (numMoved > 0)

                System.arraycopy(elementData, index + 1, elementData, index,

                        numMoved);

            elementData[--size] = null; // clear to let GC do its work


            return oldValue;

        }

        public boolean remove(Object o) {

            if (o == null) {

                for (int index = 0; index < size; index++)

                    if (elementData[index] == null) {

                        fastRemove(index);

                        return true;

                    }

            } else {

                for (int index = 0; index < size; index++)

                    if (o.equals(elementData[index])) {

                        fastRemove(index);

                        return true;

                    }

            }

            return false;

        }

        private void fastRemove(int index) {

            modCount++;

            int numMoved = size - index - 1;

            if (numMoved > 0)

                System.arraycopy(elementData, index + 1, elementData, index,

                        numMoved);

            elementData[--size] = null; // clear to let GC do its work

        }

        public void clear() {

            modCount++;


            // clear to let GC do its work

            for (int i = 0; i < size; i++)

                elementData[i] = null;


            size = 0;

        }

        public boolean addAll(Collection<? extends E> c) {

            Object[] a = c.toArray();

            int numNew = a.length;

            ensureCapacityInternal(size + numNew);  // Increments modCount

            System.arraycopy(a, 0, elementData, size, numNew);

            size += numNew;

            return numNew != 0;

        }

        public boolean addAll(int index, Collection<? extends E> c) {

            rangeCheckForAdd(index);


            Object[] a = c.toArray();

            int numNew = a.length;

            ensureCapacityInternal(size + numNew);  // Increments modCount


            int numMoved = size - index;

            if (numMoved > 0)

                System.arraycopy(elementData, index, elementData, index + numNew,

                        numMoved);


            System.arraycopy(a, 0, elementData, index, numNew);

            size += numNew;

            return numNew != 0;

        }

        protected void removeRange(int fromIndex, int toIndex) {

            modCount++;

            int numMoved = size - toIndex;

            System.arraycopy(elementData, toIndex, elementData, fromIndex,

                    numMoved);


            // clear to let GC do its work

            int newSize = size - (toIndex - fromIndex);

            for (int i = newSize; i < size; i++) {

                elementData[i] = null;

            }

            size = newSize;

        }

        private void rangeCheck(int index) {

            if (index >= size)

                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));

        }

        private void rangeCheckForAdd(int index) {

            if (index > size || index < 0)

                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));

        }

        private String outOfBoundsMsg(int index) {

            return "Index: " + index + ", Size: " + size;

        }

        public boolean removeAll(Collection<?> c) {

            Objects.requireNonNull(c);

            return batchRemove(c, false);

        }

        public boolean retainAll(Collection<?> c) {

            Objects.requireNonNull(c);

            return batchRemove(c, true);

        }

        private boolean batchRemove(Collection<?> c, boolean complement) {

            final Object[] elementData = this.elementData;

            int r = 0, w = 0;

            boolean modified = false;

            try {

                for (; r < size; r++)

                    if (c.contains(elementData[r]) == complement)

                        elementData[w++] = elementData[r];

            } finally {

                // Preserve behavioral compatibility with AbstractCollection,

                // even if c.contains() throws.

                if (r != size) {

                    System.arraycopy(elementData, r,

                            elementData, w,

                            size - r);

                    w += size - r;

                }

                if (w != size) {

                    // clear to let GC do its work

                    for (int i = w; i < size; i++)

                        elementData[i] = null;

                    modCount += size - w;

                    size = w;

                    modified = true;

                }

            }

            return modified;

        }




        static void subListRangeCheck(int fromIndex, int toIndex, int size) {

            if (fromIndex < 0)

                throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);

            if (toIndex > size)

                throw new IndexOutOfBoundsException("toIndex = " + toIndex);

            if (fromIndex > toIndex)

                throw new IllegalArgumentException("fromIndex(" + fromIndex +

                        ") > toIndex(" + toIndex + ")");

        }


        @Override

        public void forEach(Consumer<? super E> action) {

            Objects.requireNonNull(action);

            final int expectedModCount = modCount;

            @SuppressWarnings("unchecked")

            final E[] elementData = (E[]) this.elementData;

            final int size = this.size;

            for (int i = 0; modCount == expectedModCount && i < size; i++) {

                action.accept(elementData[i]);

            }

            if (modCount != expectedModCount) {

                throw new ConcurrentModificationException();

            }

        }

        @Override

        public boolean removeIf(Predicate<? super E> filter) {

            Objects.requireNonNull(filter);

            // figure out which elements are to be removed

            // any exception thrown from the filter predicate at this stage

            // will leave the collection unmodified

            int removeCount = 0;

            final BitSet removeSet = new BitSet(size);

            final int expectedModCount = modCount;

            final int size = this.size;

            for (int i = 0; modCount == expectedModCount && i < size; i++) {

                @SuppressWarnings("unchecked")

                final E element = (E) elementData[i];

                if (filter.test(element)) {

                    removeSet.set(i);

                    removeCount++;

                }

            }

            if (modCount != expectedModCount) {

                throw new ConcurrentModificationException();

            }


            // shift surviving elements left over the spaces left by removed elements

            final boolean anyToRemove = removeCount > 0;

            if (anyToRemove) {

                final int newSize = size - removeCount;

                for (int i = 0, j = 0; (i < size) && (j < newSize); i++, j++) {

                    i = removeSet.nextClearBit(i);

                    elementData[j] = elementData[i];

                }

                for (int k = newSize; k < size; k++) {

                    elementData[k] = null;  // Let gc do its work

                }

                this.size = newSize;

                if (modCount != expectedModCount) {

                    throw new ConcurrentModificationException();

                }

                modCount++;

            }


            return anyToRemove;

        }

        @Override

        @SuppressWarnings("unchecked")

        public void sort(Comparator<? super E> c) {

            final int expectedModCount = modCount;

            Arrays.sort((E[]) elementData, 0, size, c);

            if (modCount != expectedModCount) {

                throw new ConcurrentModificationException();

            }

            modCount++;

        }

        @Override
        public int replaceAll(E e, E f) {
            //Add your code here
            int count=0;

            for (int i=0; i<this.size();++i){
                if(this.elementData(i).equals(e)){
                    this.elementData[i]= f;
                    count++;
                }
            }
            return count;
        }

    }


}