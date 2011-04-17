package ru.kirsanov.mdbo.metamodel.entity;

import java.util.LinkedList;
import java.util.List;

public class Schema extends MetaObject {
    private List<Table> tables;

    public Schema(final String name) {
        super(name);
        tables = new LinkedList<Table>();
    }

    public void addTable(Table table) {
        this.tables.add(table);
    }

    public void removeTable(Table table) {
        this.tables.remove(table);
    }

    public List<Table> getTables() {
        return this.tables;
    }
}
