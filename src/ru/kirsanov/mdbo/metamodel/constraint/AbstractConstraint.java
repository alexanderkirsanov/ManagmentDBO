package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.IColumn;
import ru.kirsanov.mdbo.metamodel.entity.ITable;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConstraint implements Constraint {
    private ITable table;
    private String name;
    private List<IColumn> columns;

    public AbstractConstraint(ITable table, String name) {
        this.table = table;
        this.name = name;
        columns = new ArrayList<IColumn>();
    }

    public ITable getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public List<IColumn> getColumns() {
        return columns;
    }

    public void addColumn(final IColumn column) throws ColumnNotFoundException {

        if (!column.getTable().equals(getTable())) {
            throw new ColumnNotFoundException();
        }
        columns.add(column);
    }
}
