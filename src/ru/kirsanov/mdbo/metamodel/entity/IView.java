package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

import java.util.List;


public interface IView extends Container {
    String getDefinition();

    String getCheckOption();

    void setCheckOption(String str);

    boolean isUpdatable();

    void setUpdatable(boolean value);

    IColumn createColumn(String columnName, DataType dataType) throws ColumnAlreadyExistsException;

    List<IColumn> getColumns();

    void removeColumn(IColumn column) throws ColumnNotFoundException;

    IColumn getColumn(String name) throws ColumnNotFoundException;

    void rename(String newName);
}
