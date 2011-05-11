package ru.kirsanov.mdbo.synchronize;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.metamodel.exception.ViewNotFoundException;
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
    public Model synchronize(Model model) throws SQLException, ColumnAlreadyExistsException, ColumnNotFoundException, ModelSynchronizerNotFound, TableNotFound, ViewNotFoundException {
        IEntitySynchronizer entitySynchronizer = configurator.getSynchronizers(model);
        return entitySynchronizer.execute(model);
    }
}
