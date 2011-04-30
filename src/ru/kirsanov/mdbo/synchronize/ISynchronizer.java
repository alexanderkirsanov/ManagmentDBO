package ru.kirsanov.mdbo.synchronize;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.synchronize.exception.ConnectionNotSet;
import ru.kirsanov.mdbo.synchronize.exception.IncorrectDataBaseType;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 23.04.11
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
public interface ISynchronizer {
    Model synchronize(Model model) throws Throwable, IncorrectDataBaseType, ConnectionNotSet, SQLException, ColumnAlreadyExistsException, ColumnNotFoundException;
}
