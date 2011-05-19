package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.datatype.DataType;

public interface IColumn extends Container {
    Container getParent();

    ITable getTable();

    void setContainer(Container container);

    void setDataType(DataType dataType);

    DataType getDataType();

    boolean isNullable();

    void setNullable(boolean nullable);

    boolean isUnique();

    void setUnique(boolean unique);

    String getDefaultValue();

    void setDefaultValue(String defaultValue);

    void rename(String newName);
}
