package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.Column;
import ru.kirsanov.mdbo.metamodel.entity.Table;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConstraint implements Constraint {
    private Table table;
    private String name;
    private List<Column> columns;

    public AbstractConstraint(Table table, String name) {
        this.table = table;
        this.name = name;
        columns = new ArrayList<Column>();
    }

    public Table getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void addColumn(final Column column) throws ColumnNotFoundException {

        if (!column.getTable().equals(getTable())) {
            throw new ColumnNotFoundException();
        }
        columns.add(column);
    }
}
