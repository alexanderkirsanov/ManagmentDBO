package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.Constraint;
import ru.kirsanov.mdbo.metamodel.constraint.EntityConstraint;

import java.util.ArrayList;
import java.util.List;

public class View extends MetaObject {
    private List<Column> columns;
    private List<EntityConstraint> constraints;

    public View(final String name) {
        super(name);
        constraints = new ArrayList<EntityConstraint>();
        columns = new ArrayList<Column>();
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public Constraint createConstraint(Column column, String constraint) {
        EntityConstraint entityConstraint = new EntityConstraint(column, constraint);
        this.constraints.add(entityConstraint);
        return entityConstraint;
    }

    public List<EntityConstraint> getConstraints() {
        return this.constraints;
    }


}
