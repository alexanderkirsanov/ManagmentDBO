package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.ITable;

public class PrimaryKey extends AbstractConstraint implements Constraint {
    private String name;

    public PrimaryKey(ITable table, String name) {
        super(table, name);
    }
}
