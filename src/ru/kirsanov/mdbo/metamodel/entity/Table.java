package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.PrimaryKey;
import ru.kirsanov.mdbo.metamodel.constraint.UniqueKey;
import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.exception.ElementNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.NotExistsColumnException;

import java.util.LinkedList;
import java.util.List;

public class Table extends MetaObject implements Container {

    private List<Column> columns;
    private Container container;
    private List<PrimaryKey> primaryKeys = new LinkedList<PrimaryKey>();
    private List<UniqueKey> uniqueKeys = new LinkedList<UniqueKey>();

    public Table(final String name) {
        super(name);
        columns = new LinkedList<Column>();
    }

    public Column createColumn(String name, DataType dataType) {
        Column column = new Column(this, name, dataType);
        this.columns.add(column);
        return column;
    }

    public PrimaryKey createPrimaryKey(Column column) throws NotExistsColumnException {
        if (columns.contains(column)) {
            PrimaryKey primaryKey = new PrimaryKey(this, column.getName());
            if (!primaryKeys.contains(primaryKey)) {
                primaryKeys.add(primaryKey);
            }
            return primaryKey;
        } else {
            throw new NotExistsColumnException();
        }
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public UniqueKey createUniqueKey(String name) {
        UniqueKey uniqueKey = new UniqueKey(this, name);
        uniqueKeys.add(uniqueKey);
        return uniqueKey;
    }

    public List<UniqueKey> getUniqueKeys() {
        return uniqueKeys;
    }

    public Container getParent() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKeys.get(0);
    }

    public List<PrimaryKey> getPrimaryKeys() {
        return primaryKeys;
    }

    public void addTuple(String... value) throws IllegalArgumentException {
        if (value.length == columns.size()) {
            int i = 0;
            for (String currentStr : value) {
                columns.get(i).addVariable(currentStr);
                i++;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void removeTuple(int number) throws ElementNotFoundException {
        if (number < columns.size()) {
            for (Column column : columns) {
                column.removeVariable(number);
            }
        }
    }
}
