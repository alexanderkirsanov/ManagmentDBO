package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.PrimaryKey;
import ru.kirsanov.mdbo.metamodel.constraint.UniqueKey;
import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ElementNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.NotFoundColumnException;

import java.util.ArrayList;
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

    public Table(final String name, final Schema schema) {
        super(name);
        this.container = schema;
        columns = new LinkedList<Column>();
    }

    public Column createColumn(final String name, final DataType dataType) throws ColumnAlreadyExistsException {
        if (!(getColumn(name) == null)) throw new ColumnAlreadyExistsException();
        Column column = new Column(this, name, dataType);
        this.columns.add(column);
        return column;
    }

    public Column getColumn(String name) {
        Column foundColumn = null;
        for (Column column : columns) {
            if (column.getName().equals(name)) {
                return column;
            }
        }
        return foundColumn;
    }

    public void removeColumn(final Column column) throws NotFoundColumnException {
        if (getColumn(column.getName()) == null) throw new NotFoundColumnException();
        this.columns.remove(column);
    }

    public PrimaryKey createPrimaryKey(Column column) throws NotFoundColumnException {
        if (columns.contains(column)) {
            PrimaryKey primaryKey = new PrimaryKey(this, column.getName());
            if (!primaryKeys.contains(primaryKey)) {
                primaryKeys.add(primaryKey);
            }
            return primaryKey;
        } else {
            throw new NotFoundColumnException();
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

    public List<String> getTuple(int id) throws ElementNotFoundException {
        List<String> tuple = new ArrayList<String>();
        if (id < columns.size()) {
            for (Column column : columns) {
                tuple.add(column.getVariable(id));
            }
        } else {
            throw new ElementNotFoundException();
        }
        return tuple;
    }

    public void removeTuple(int id) throws ElementNotFoundException {
        if (id < columns.size()) {
            for (Column column : columns) {
                column.removeVariable(id);
            }
        }
    }
}
