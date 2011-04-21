package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.IColumn;
import ru.kirsanov.mdbo.metamodel.entity.ITable;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

public class NotNullKey extends AbstractConstraint {
    public NotNullKey(ITable table, String name) {
        super(table, name);
    }

    @Override
    public void addColumn(IColumn column) throws ColumnNotFoundException {
        super.addColumn(column);
        column.setNullable(false);
    }
}
