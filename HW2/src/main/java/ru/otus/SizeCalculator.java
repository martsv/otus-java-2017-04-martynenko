package ru.otus;

import java.util.function.Supplier;

/**
 * mart
 * 11.04.2017
 */
public final class SizeCalculator {

    private static final int ARRAY_SIZE = 5 * 1024 * 1024;
    private static final int ITERATIONS = 10;

    private SizeCalculator() {}

    public static <T> int calculate(int referenceSize, Supplier<T> supplier) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        double sizeSum = 0;

        for (int i = 0; i < ITERATIONS; i ++) {
            long before = runtime.freeMemory();

            Object[] array = new Object[ARRAY_SIZE];

            if (referenceSize > 0) {
                for (int j = 0; j < array.length; j++) {
                    array[j] = supplier.get();
                }
            }

            long after = runtime.freeMemory();

            sizeSum += 1.0 * (before - after) / array.length - referenceSize;

            System.gc();
            Thread.sleep(1000);
        }

        return Double.valueOf(sizeSum / ITERATIONS).intValue();
    }

}
