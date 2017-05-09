package ru.otus.hw5;


import ru.otus.hw5.tester.Executor;

/**
 * mart
 * 07.05.2017
 */
public class Main {

    public static void main(String[] args) {
        Class[] classes = { SomeTest.class };

        Executor.execute(classes);
    }

}
