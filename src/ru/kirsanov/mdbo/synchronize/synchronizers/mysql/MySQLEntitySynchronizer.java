package ru.kirsanov.mdbo.synchronize.synchronizers.mysql;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.util.List;

public class MySQLEntitySynchronizer implements IEntitySynchronizer {
    private List<IEntitySynchronizer> synchronizers;

    public MySQLEntitySynchronizer(List<IEntitySynchronizer> synchronizers) {
        this.synchronizers = synchronizers;
    }

    @Override
    public Model execute(final Model model) throws Throwable {
        Model synchronizeModel = model;
        for (IEntitySynchronizer synchronizer : synchronizers) {
            synchronizeModel = synchronizer.execute(synchronizeModel);
        }
        return synchronizeModel;
    }


}
