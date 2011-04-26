package ru.kirsanov.mdbo.synchronize;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.synchronize.exception.ConnectionNotSet;
import ru.kirsanov.mdbo.synchronize.exception.IncorrectDataBaseType;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.IModelSynchronizer;

import java.sql.SQLException;
import java.util.Map;

public class Synchronizer implements ISynchronizer {

    private Map<Model, IModelSynchronizer> models;

    public Synchronizer(Map<Model, IModelSynchronizer> models) {
        this.models = models;
    }

    @Override
    public Model synchronize(Model model) throws ModelSynchronizerNotFound, IncorrectDataBaseType, ConnectionNotSet, SQLException, ColumnAlreadyExistsException {
        IModelSynchronizer modelSynchronizer = models.get(model);
        return modelSynchronizer.execute(model);
    }
}
