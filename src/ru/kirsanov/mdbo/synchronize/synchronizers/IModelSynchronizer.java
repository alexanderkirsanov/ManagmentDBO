package ru.kirsanov.mdbo.synchronize.synchronizers;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.synchronize.exception.ConnectionNotSet;
import ru.kirsanov.mdbo.synchronize.exception.IncorrectDataBaseType;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 23.04.11
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface IModelSynchronizer {
    Model execute(Model model) throws IncorrectDataBaseType, ConnectionNotSet, SQLException, ModelSynchronizerNotFound, ColumnAlreadyExistsException, ColumnNotFoundException;
}
