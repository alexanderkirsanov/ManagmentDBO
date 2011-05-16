package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.datatype.DataType;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 21.04.11
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */
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
}
