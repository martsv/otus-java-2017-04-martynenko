package ru.otus.hw5.tester;

/**
 * mart
 * 08.05.2017
 */
public final class Executor {

    private Executor() {
    }

    public static void execute(Class[] classes) {
        for (Class clazz: classes) {
            new TestClass(clazz).run();
        }
    }

}
