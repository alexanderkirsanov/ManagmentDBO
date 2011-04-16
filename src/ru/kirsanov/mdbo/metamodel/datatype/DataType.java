package ru.kirsanov.mdbo.metamodel.datatype;

import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * date: 13.04.11
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
public interface DataType {
    String getName();

    String getSqlString();

    void checkCorrect(Object o) throws IncorrectVariableTypeException;
}
