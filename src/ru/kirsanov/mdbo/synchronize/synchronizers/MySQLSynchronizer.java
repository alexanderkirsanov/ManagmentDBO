package ru.kirsanov.mdbo.synchronize.synchronizers;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.synchronize.synchronizers.builders.MySQLSynchronizersBuilder;
import ru.kirsanov.mdbo.synchronize.synchronizers.builders.SynchronizersBuilder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MySQLSynchronizer implements IEntitySynchronizer {


    private List<IEntitySynchronizer> entitySynchronizerList = new ArrayList<IEntitySynchronizer>();

    public MySQLSynchronizer(Connection connection) {
        SynchronizersBuilder synchronizersBuilder = new MySQLSynchronizersBuilder(connection);
        entitySynchronizerList.add(synchronizersBuilder.createTableSynchronizer());
        entitySynchronizerList.add(synchronizersBuilder.createPrimaryKeySynchronizer());
        entitySynchronizerList.add(synchronizersBuilder.createForeignKeySynchronizer());
        entitySynchronizerList.add(synchronizersBuilder.createViewSynchronizer());
        entitySynchronizerList.add(synchronizersBuilder.createIndexSynchronizer());
    }

    @Override
    public Model execute(Model model) throws Throwable {
        Model synchronizeModel = model;
        for (IEntitySynchronizer synchronizer : entitySynchronizerList) {
            synchronizeModel = synchronizer.execute(synchronizeModel);
        }
        return synchronizeModel;
    }
}

