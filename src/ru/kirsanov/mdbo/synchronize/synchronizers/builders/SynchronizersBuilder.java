package ru.kirsanov.mdbo.synchronize.synchronizers.builders;

import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 02.05.11
 * Time: 2:22
 * To change this template use File | Settings | File Templates.
 */
public interface SynchronizersBuilder {
    IEntitySynchronizer createTableSynchronizer();

    IEntitySynchronizer createPrimaryKeySynchronizer();

    IEntitySynchronizer createForeignKeySynchronizer();

    IEntitySynchronizer createViewSynchronizer();

    IEntitySynchronizer createIndexSynchronizer();
}
