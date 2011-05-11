package ru.kirsanov.mdbo.synchronize.synchronizers;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.metamodel.exception.ViewNotFound;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.builders.SynchronizersBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSynchronizer implements IEntitySynchronizer {


    private List<IEntitySynchronizer> entitySynchronizerList = new ArrayList<IEntitySynchronizer>();

    public DatabaseSynchronizer(SynchronizersBuilder synchronizersBuilder) {

        entitySynchronizerList.add(synchronizersBuilder.createTableSynchronizer());
        entitySynchronizerList.add(synchronizersBuilder.createPrimaryKeySynchronizer());
        entitySynchronizerList.add(synchronizersBuilder.createForeignKeySynchronizer());
        entitySynchronizerList.add(synchronizersBuilder.createViewSynchronizer());
        entitySynchronizerList.add(synchronizersBuilder.createIndexSynchronizer());
    }

    @Override
    public Model execute(Model model) throws ColumnAlreadyExistsException, TableNotFound, SQLException, ColumnNotFoundException, ModelSynchronizerNotFound, ViewNotFound {
        Model synchronizeModel = model;
        for (IEntitySynchronizer synchronizer : entitySynchronizerList) {
            synchronizeModel = synchronizer.execute(synchronizeModel);
        }
        return synchronizeModel;
    }
}

