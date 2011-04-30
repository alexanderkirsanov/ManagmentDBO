package ru.kirsanov.mdbo.synchronize;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.synchronize.exception.ConnectionNotSet;
import ru.kirsanov.mdbo.synchronize.exception.IncorrectDataBaseType;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.sql.SQLException;
import java.util.Map;

public class Synchronizer implements ISynchronizer {

    private Map<Model, IEntitySynchronizer> models;

    public Synchronizer(Map<Model, IEntitySynchronizer> models) {
        this.models = models;
    }

    @Override
    public Model synchronize(Model model) throws Throwable, IncorrectDataBaseType, ConnectionNotSet, SQLException, ColumnAlreadyExistsException, ColumnNotFoundException {
        IEntitySynchronizer entitySynchronizer = models.get(model);
        return entitySynchronizer.execute(model);
    }
}
