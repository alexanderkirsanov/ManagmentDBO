package ru.kirsanov.mdbo.synchronize;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.metamodel.exception.ViewNotFound;
import ru.kirsanov.mdbo.synchronize.exception.ConnectionNotSet;
import ru.kirsanov.mdbo.synchronize.exception.IncorrectDataBaseType;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.Configurator;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.sql.Connection;
import java.sql.SQLException;

public class Synchronizer implements ISynchronizer {

    private Configurator configurator;

    public Synchronizer(Connection connection) {
        this.configurator = new Configurator(connection);
    }

    @Override
    public Model synchronize(Model model) throws IncorrectDataBaseType, ConnectionNotSet, SQLException, ColumnAlreadyExistsException, ColumnNotFoundException, ModelSynchronizerNotFound, TableNotFound, ViewNotFound {
        IEntitySynchronizer entitySynchronizer = configurator.getSynchronizers(model);
        return entitySynchronizer.execute(model);
    }
}
