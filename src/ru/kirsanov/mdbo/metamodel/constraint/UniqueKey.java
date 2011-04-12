package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.Column;
import ru.kirsanov.mdbo.metamodel.entity.Table;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

public class UniqueKey extends AbstractConstraint{

    public UniqueKey(Table table, String name) {
        super(table, name);
    }
    @Override
    public void addColumn(Column column) throws ColumnNotFoundException {
        super.addColumn(column);
        column.setUnique(true);
    }
}
