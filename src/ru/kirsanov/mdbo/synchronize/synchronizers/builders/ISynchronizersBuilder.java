package ru.kirsanov.mdbo.synchronize.synchronizers.builders;

import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 30.04.11
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
public interface ISynchronizersBuilder {
    List<IEntitySynchronizer> getSynchronizers();
}
