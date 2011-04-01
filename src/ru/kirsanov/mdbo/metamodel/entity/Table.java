package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.PrimaryKey;
import ru.kirsanov.mdbo.metamodel.datatype.Datatype;
import ru.kirsanov.mdbo.metamodel.exception.NotExistsColumnException;

import java.util.LinkedList;
import java.util.List;

public class Table extends MetaObject implements Container {

    private List<Column> columns;
    private Container container;
    private List<PrimaryKey> primaryKeys = new LinkedList<PrimaryKey>();

    public Table(final String name) {
        super(name);
        columns = new LinkedList<Column>();
    }

    public Column createColumn(String name, Datatype datatype) {
        Column column = new Column(this, name, datatype);
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

    public Container getParent() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKeys.get(0);
    }
}
