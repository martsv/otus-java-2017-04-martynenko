package ru.otus.hw5.tester;

/**
 * mart
 * 07.05.2017
 */
public final class Assert {

    private Assert() {
    }

    public static void fail(String message) {
        throw new AssertionError(message);
    }

    public static void assertTrue(String message, boolean check) {
        if (!check) {
            fail(message);
        }
    }

    public static void assertNotNull(String message, Object object) {
        assertTrue(message, object != null);
    }

}
