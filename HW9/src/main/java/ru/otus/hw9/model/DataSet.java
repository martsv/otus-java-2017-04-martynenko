package ru.otus.hw9.model;

import javax.persistence.Id;

public class DataSet {

    @Id
    private long id;

    public DataSet() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
