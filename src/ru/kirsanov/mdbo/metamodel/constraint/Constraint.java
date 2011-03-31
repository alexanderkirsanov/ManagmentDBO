package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.Column;
import ru.kirsanov.mdbo.metamodel.entity.Table;

public interface Constraint {

    Table getTable();

    String getName();

    Iterable<Column> getColumns();
}
