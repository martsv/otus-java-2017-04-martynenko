package ru.otus.hw5;

import ru.otus.hw5.tester.After;
import ru.otus.hw5.tester.Assert;
import ru.otus.hw5.tester.Before;
import ru.otus.hw5.tester.Test;

/**
 * mart
 * 07.05.2017
 */
public class SomeTest extends ParentTest {

    private int number;
    private String string;

    @Before
    public void before() {
        number = 1;
        string = "Hello world";
        System.out.println("Before test ...");
    }

    @After
    public void after() {
        System.out.println("After test ...");
    }

    @Test
    public void testNumber() {
        System.out.println("Number should be equal to 1");
        Assert.assertTrue("Number should be equal to 1", number == 1);
    }

    @Test
    public void testString() {
        System.out.println("String should be equal to \"Hello world\"");
        Assert.assertTrue("String should be equal to \"Hello world\"", string.equals("Hello world"));
    }

    @Test
    public void testStringNotNull() {
        System.out.println("String should be not null");
        Assert.assertNotNull("String should be not null", string);
    }

}
