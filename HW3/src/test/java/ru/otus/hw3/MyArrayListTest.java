package ru.otus.hw3;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * mart
 * 17.04.2017
 */
public class MyArrayListTest {

    @Test
    public void testSort() {
        List<Integer> list = new MyArrayList<>();
        list.add(7);
        list.add(3);
        list.add(15);
        list.add(-3);
        Collections.sort(list, Integer::compareTo);
        assertArrayEquals(list.toArray(), new Integer[] { -3, 3, 7, 15 });
    }

    @Test
    public void testSort2() {
        List<Integer> list = new MyArrayList<>();
        Collections.sort(list, Integer::compareTo);
        assertArrayEquals(list.toArray(), new Integer[] {});
    }

    @Test
    public void testAddAll() {
        List<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Collections.addAll(list, 4, 5, 6);
        assertArrayEquals(list.toArray(), new Integer[] { 1, 2, 3, 4, 5, 6 });
    }

    @Test
    public void testCopy() {
        List<Integer> src = new MyArrayList<>();
        src.add(1);
        src.add(2);
        src.add(3);
        List<Integer> dst = new MyArrayList<>();
        dst.add(4);
        dst.add(5);
        dst.add(6);
        dst.add(7);
        Collections.copy(dst, src);
        assertArrayEquals(dst.toArray(), new Integer[] { 1, 2, 3, 7 });
    }

    @Test
    public void testSizeGetContains() {
        List<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertTrue(list.size() == 3);
        assertTrue(list.get(0).equals(1));
        assertTrue(list.get(1).equals(2));
        assertTrue(list.get(2).equals(3));
        assertTrue(list.contains(2));
        assertFalse(list.contains(10));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testBounds() {
        List<Integer> list = new MyArrayList<>();
        list.get(0);
    }

    @Test
    public void testCapacity() {
        List<Integer> list = new MyArrayList<>(1);
        list.add(1);
        list.add(2);
        list.add(3);
        assertArrayEquals(list.toArray(), new Integer[] {1, 2, 3});
    }

    @Test
    public void testCapacity2() {
        List<Integer> list = new MyArrayList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        for (int i = 0; i < 100000; i++) {
            assertEquals(list.get(i), Integer.valueOf(i));
        }
    }

    @Test
    public void testRemoveIndex() {
        List<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.remove(3);
        assertArrayEquals(list.toArray(), new Integer[] { 1, 2, 3 });
        list.remove(1);
        assertArrayEquals(list.toArray(), new Integer[] { 1, 3 });
        list.remove(0);
        assertArrayEquals(list.toArray(), Collections.singletonList(3).toArray());
    }

    @Test
    public void testRemoveObject() {
        List<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        assertTrue(list.remove(Integer.valueOf(2)));
        assertArrayEquals(list.toArray(), new Integer[] { 1, 3, 4 });
        assertFalse(list.remove(Integer.valueOf(5)));
        assertArrayEquals(list.toArray(), new Integer[] { 1, 3, 4 });
    }

    @Test
    public void testIndexOf() {
        List<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        assertEquals(list.indexOf(2), 1);
        assertEquals(list.lastIndexOf(2), 2);
        assertEquals(list.indexOf(5), -1);
    }

}
