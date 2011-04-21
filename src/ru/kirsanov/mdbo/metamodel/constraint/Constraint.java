package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.IColumn;
import ru.kirsanov.mdbo.metamodel.entity.ITable;

public interface Constraint {

    ITable getTable();

    String getName();

    Iterable<IColumn> getColumns();
}
