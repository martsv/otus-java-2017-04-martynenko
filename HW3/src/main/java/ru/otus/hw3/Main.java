package ru.otus.hw3;

import java.util.List;

/**
 * mart
 * 16.04.2017
 */
public class Main {

    public static void main(String[] args) {
        List<String> list = new MyArrayList<>();
        list.add("I");
        list.add("like");
        list.add("Java");
        for (String s: list) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

}
