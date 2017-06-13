package ru.otus.hw7.atm;

import java.util.*;
import java.util.Observable;

public class ATM implements java.util.Observer {
    private Cell first;
    private final ATMMemento memento;

    public ATM(List<Cell> cells) {
        Collections.sort(cells);
        memento = new ATMMemento(cells);
        first = cells.get(0);
        linkCells(cells);
    }

    private void setState(ATMMemento memento) {
        List<Cell> cells = memento.getState();
        first = cells.get(0);
        linkCells(cells);
    }

    public boolean withdraw(int requested) {
        return first.withdraw(requested);
    }

    public int getBalance() {
        Iterator<Cell> iterator = first.iterator();
        int balance = 0;
        while (iterator.hasNext()) {
            balance += iterator.next().getBalance();
        }
        return balance;
    }

    private void linkCells(List<Cell> cells) {
        Iterator<Cell> iterator = cells.iterator();
        Cell cellA = iterator.next();
        while (iterator.hasNext()) {
            Cell cellB = iterator.next();
            cellA.setNext(cellB);
            cellA = cellB;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Department.Notify notify = (Department.Notify) arg;

        if (notify.getCommand().equals(ATMCommand.COUNT_BALANCE)) {
            notify.addBalance(getBalance());
        } else if (notify.getCommand().equals(ATMCommand.RESTORE_STATE)) {
            setState(memento);
        }
    }
}
