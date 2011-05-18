package ru.kirsanov.mdbo.synchronize.synchronizers.builders;

import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;
import ru.kirsanov.mdbo.synchronize.synchronizers.mysql.*;

import java.sql.Connection;

public class MySQLSynchronizersBuilder implements SynchronizersBuilder {

    private Connection connection;

    public MySQLSynchronizersBuilder(Connection connection) {
        this.connection = connection;
    }

    @Override
    public IEntitySynchronizer createTableSynchronizer() {
        return new MySQLTableSynchronizer(connection);
    }

    @Override
    public IEntitySynchronizer createPrimaryKeySynchronizer() {
        return new MySQLPrimaryKeySynchronizer(connection);
    }

    @Override
    public IEntitySynchronizer createForeignKeySynchronizer() {
        return new MySQLForeignKeySynchronizer(connection);
    }

    @Override
    public IEntitySynchronizer createViewSynchronizer() {
        return new MySQLViewSynchronizer(connection);
    }

    @Override
    public IEntitySynchronizer createIndexSynchronizer() {
        return new MySQLIndexSynchronizer(connection);
    }

}
