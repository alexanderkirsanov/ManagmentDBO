package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.ITable;

public class PrimaryKey extends AbstractConstraint implements Constraint {
    private String name;

    public PrimaryKey(ITable table, String name) {
        super(table, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrimaryKey that = (PrimaryKey) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
