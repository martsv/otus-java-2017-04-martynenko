package ru.otus.hw7;

import ru.otus.hw7.atm.ATM;
import ru.otus.hw7.atm.Cell;
import ru.otus.hw7.atm.Department;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Cell> cells1 = new ArrayList<>();
        List<Cell> cells2 = new ArrayList<>();

        cells1.add(new Cell(1, 10));
        cells1.add(new Cell(5, 10));
        cells1.add(new Cell(10, 10));

        ATM atm1 = new ATM(cells1);
        System.out.println(atm1.getBalance());

        cells2.add(new Cell(1, 5));
        cells2.add(new Cell(5, 20));
        cells2.add(new Cell(10, 50));

        ATM atm2 = new ATM(cells2);
        System.out.println(atm2.getBalance());

        Department department = new Department();
        department.addObserver(atm1);
        department.addObserver(atm2);

        System.out.println(department.getBalance());

        atm1.withdraw(150);
        atm2.withdraw(220);

        System.out.println(department.getBalance());

        department.restoreState();

        System.out.println(department.getBalance());
    }

}
