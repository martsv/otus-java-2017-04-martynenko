package ru.otus.hw3;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * mart
 * 16.04.2017
 */
public class MyArrayList<T> implements List<T> {

    private Object[] data;
    private int from;
    private int to;
    private int capacity;

    public MyArrayList(int initialCapacity) {
        this.capacity = initialCapacity;
        this.data = new Object[capacity];
        this.from = 0;
        this.to = 0;
    }

    public MyArrayList() {
        this(10);
    }

    private MyArrayList(Object[] data, int fromIndex, int toIndex) {
        this.capacity = toIndex - fromIndex;
        this.data = data;
        this.from = fromIndex;
        this.to = toIndex;
    }

    @Override
    public int size() {
        return to - from;
    }

    @Override
    public boolean isEmpty() {
        return to == from;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = from; i < to; i++) {
            if (o == null) {
                if (data[i] == null) {
                    return true;
                }
            } else if (o.equals(data[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int pos = 0;

            @Override
            public boolean hasNext() {
                return pos < size();
            }

            @Override
            public T next() {
                checkBoundsIterator(pos);
                return getElement(pos++);
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        System.arraycopy(data, from, array, 0, size());
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        checkLength(1);
        data[to++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index > 0) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        for (int i = from; i < to; i++) {
            data[i] = null;
        }
        to = from;
    }

    @Override
    public T get(int index) {
        checkBounds(index);
        return getElement(index);
    }

    @Override
    public T set(int index, T element) {
        checkBounds(index);
        T oldElement = getElement(index);
        data[from + index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        checkBounds(index);
        checkLength(1);
        System.arraycopy(data, from + index, data, from + index + 1, size() - index);
        data[from + index] = element;
        to++;
    }

    @Override
    public T remove(int index) {
        checkBounds(index);
        T oldElement = getElement(index);
        System.arraycopy(data, from + index + 1, data, from + index, size() - index - 1);
        to--;
        return oldElement;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            T elem = getElement(i);
            if (o == null ? elem == null : o.equals(elem)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size() - 1; i >= 0; i--) {
            T elem = getElement(i);
            if (o == null ? elem == null : o.equals(elem)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListIterator<T>() {
            private int pos = index;
            private int last = -1;

            {
                checkBounds(index);
            }

            @Override
            public boolean hasNext() {
                return pos < size();
            }

            @Override
            public T next() {
                checkBoundsIterator(pos);
                last = pos;
                return getElement(pos++);
            }

            @Override
            public boolean hasPrevious() {
                return pos > 0;
            }

            @Override
            public T previous() {
                checkBoundsIterator(pos - 1);
                last = pos;
                return getElement(--pos);
            }

            @Override
            public int nextIndex() {
                return pos;
            }

            @Override
            public int previousIndex() {
                return pos - 1;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(T t) {
                if (last == -1) {
                    throw new IllegalStateException();
                }
                MyArrayList.this.set(last, t);
            }

            @Override
            public void add(T t) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return new MyArrayList<T>(data, fromIndex, toIndex);
    }

    private T getElement(int index) {
        return (T) data[from + index];
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkBoundsIterator(int index) {
        if (index < 0 || index >= size()) {
            throw new NoSuchElementException();
        }
    }

    private void checkLength(int count) {
        if (size() + count > capacity) {
            while (size() + count > capacity) {
                capacity *= 2;
            }
            Object[] newData = new Object[capacity];
            System.arraycopy(data, from, newData, 0, size());
            data = newData;
            to = size();
            from = 0;
        }
    }
}
