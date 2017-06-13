package ru.otus.hw7.atm;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * mart
 * 13.06.2017
 */
public class DepartmentTest {

    private ATM atm1, atm2;
    private Department department;

    @Before
    public void before() {
        List<Cell> cells1 = new ArrayList<>();
        cells1.add(new Cell(1, 10));
        cells1.add(new Cell(5, 10));
        cells1.add(new Cell(10, 10));
        atm1 = new ATM(cells1);

        List<Cell> cells2 = new ArrayList<>();
        cells2.add(new Cell(1, 5));
        cells2.add(new Cell(5, 20));
        cells2.add(new Cell(10, 50));
        atm2 = new ATM(cells2);

        department = new Department();
        department.addObserver(atm1);
        department.addObserver(atm2);
    }

    @After
    public void after() {
        atm1 = null;
        atm2 = null;
        department = null;
    }

    @Test
    public void testBalance() {
        Assert.assertEquals(765, department.getBalance());
    }

    @Test
    public void testWithdraw() {
        atm1.withdraw(150);
        atm2.withdraw(215);
        Assert.assertEquals(400, department.getBalance());
    }

    @Test
    public void testRestore() {
        atm1.withdraw(150);
        atm2.withdraw(215);
        department.restoreState();
        Assert.assertEquals(765, department.getBalance());
    }

}
