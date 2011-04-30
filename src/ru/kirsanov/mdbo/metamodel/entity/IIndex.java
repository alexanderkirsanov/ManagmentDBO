package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.Constraint;
import ru.kirsanov.mdbo.metamodel.constraint.EntityConstraint;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 21.04.11
 * Time: 16:06
 * To change this template use File | Settings | File Templates.
 */
public interface IIndex {
    int UNIQUE = 1;
    int FULL_TEXT_SEARCH = 2;

    Constraint createConstraint(String constraint);

    EntityConstraint getConstraint();

    IColumn getColumn();

    int getType();

    int getCount();

    String getName();
}
