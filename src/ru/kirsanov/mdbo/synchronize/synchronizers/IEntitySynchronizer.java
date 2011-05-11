package ru.kirsanov.mdbo.synchronize.synchronizers;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.metamodel.exception.ViewNotFoundException;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 23.04.11
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface IEntitySynchronizer {
    Model execute(Model model) throws ModelSynchronizerNotFound, SQLException, ViewNotFoundException, ColumnAlreadyExistsException, TableNotFound, ColumnNotFoundException;
}
