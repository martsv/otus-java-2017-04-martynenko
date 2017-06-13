package ru.otus.hw7.atm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * mart
 * 31.05.2017
 */
public class ATMMemento {

    private final List<Cell> cells;

    public ATMMemento(List<Cell> cells) {
        this.cells = cloneCells(cells);
    }

    public List<Cell> getState() {
        return cloneCells(cells);
    }

    private static List<Cell> cloneCells(List<Cell> cells) {
        List<Cell> clonedCells = new ArrayList<>(cells.size());
        for (Cell cell: cells) {
            clonedCells.add(new Cell(cell));
        }
        return clonedCells;
    }

}
