package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.Constraint;
import ru.kirsanov.mdbo.metamodel.constraint.EntityConstraint;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 21.04.11
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public interface IView {
    void addColumn(IColumn column);

    List<IColumn> getColumns();

    Constraint createConstraint(IColumn column, String constraint);

    List<EntityConstraint> getConstraints();
}
