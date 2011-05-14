package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.PrimaryKey;
import ru.kirsanov.mdbo.metamodel.constraint.UniqueKey;
import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.ElementNotFoundException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 21.04.11
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public interface ITable extends Container {
    IColumn createColumn(String name, DataType dataType) throws ColumnAlreadyExistsException;

    IColumn getColumn(String name) throws ColumnNotFoundException;

    void removeColumn(IColumn column) throws ColumnNotFoundException;

    PrimaryKey createPrimaryKey(IColumn column) throws ColumnNotFoundException;

    List<IColumn> getColumns();

    UniqueKey createUniqueKey(IColumn column) throws ColumnNotFoundException;

    UniqueKey getUniqueKey();

    PrimaryKey getPrimaryKey();
}
