package ru.otus;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;

import static ru.otus.SizeCalculator.calculate;

/**
 * mart
 * 11.04.2017
 */
//VM options -Xmx512m -Xms512m
public class Main {

    public static void main(String... args) throws InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        int refSize = calculate(0, null);
        System.out.println("Reference size: " + refSize);
        System.out.println("String size: " + calculate(refSize, String::new));
        System.out.println("String size 2: " + calculate(refSize, () -> new String("")));
        System.out.println("Object size: " + calculate(refSize, Object::new));
        System.out.println("ArrayList size: " + calculate(refSize, ArrayList<Integer>::new));
        System.out.println("HashMap size: " + calculate(refSize, HashMap<Integer, String>::new));
        System.out.println("Array size: " + calculate(refSize, () -> new byte[0]));
    }

}
