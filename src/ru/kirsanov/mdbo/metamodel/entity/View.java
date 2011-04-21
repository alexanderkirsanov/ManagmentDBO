package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.Constraint;
import ru.kirsanov.mdbo.metamodel.constraint.EntityConstraint;

import java.util.ArrayList;
import java.util.List;

public class View extends MetaObject implements IView {
    private List<IColumn> columns;
    private List<EntityConstraint> constraints;

    public View(final String name) {
        super(name);
        constraints = new ArrayList<EntityConstraint>();
        columns = new ArrayList<IColumn>();
    }

    @Override
    public void addColumn(IColumn column) {
        this.columns.add(column);
    }

    @Override
    public List<IColumn> getColumns() {
        return this.columns;
    }

    @Override
    public Constraint createConstraint(IColumn column, String constraint) {
        EntityConstraint entityConstraint = new EntityConstraint(column, constraint);
        this.constraints.add(entityConstraint);
        return entityConstraint;
    }

    @Override
    public List<EntityConstraint> getConstraints() {
        return this.constraints;
    }


}
