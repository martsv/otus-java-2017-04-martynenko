package ru.otus.hw5;

import ru.otus.hw5.tester.After;
import ru.otus.hw5.tester.Before;

/**
 * mart
 * 08.05.2017
 */
public class ParentTest {

    @Before
    public void beforeParent() {
        System.out.println("Before parent ...");
    }

    @After
    public void afterParent() {
        System.out.println("After parent ...");
    }

}
