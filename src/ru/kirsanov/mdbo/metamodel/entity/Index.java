package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.Constraint;
import ru.kirsanov.mdbo.metamodel.constraint.EntityConstraint;

public class Index extends MetaObject implements IIndex {
    private IColumn column;
    private int count;
    private int type = 0;
    private ITable table;
    private EntityConstraint constraint = null;

    public Index(final String name, IColumn column, int count) {
        super(name);
        this.column = column;
        this.table = column.getTable();
        this.count = count;
    }

    public Index(final String name, IColumn column, int count, int type) {
        this(name, column, count);
        this.type = type;
    }

    public Index(final String name, IColumn column) {
        super(name);
        this.column = column;
        this.table = column.getTable();
    }

    @Override
    public Constraint createConstraint(String constraint) {
        EntityConstraint entityConstraint = new EntityConstraint(column, constraint);
        this.constraint = entityConstraint;
        return entityConstraint;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public EntityConstraint getConstraint() {
        return this.constraint;
    }

    @Override
    public IColumn getColumn() {
        return this.column;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Index index = (Index) o;

        if (count != index.count) return false;
        if (type != index.type) return false;
        if (column != null ? !column.equals(index.column) : index.column != null) return false;
        if (constraint != null ? !constraint.equals(index.constraint) : index.constraint != null) return false;
        if (table != null ? !table.getName().equals(index.table.getName()) : index.table != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = column != null ? column.hashCode() : 0;
        result = 31 * result + count;
        result = 31 * result + type;
        result = 31 * result + (table != null ? table.hashCode() : 0);
        result = 31 * result + (constraint != null ? constraint.hashCode() : 0);
        return result;
    }
}
