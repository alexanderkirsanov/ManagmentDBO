package ru.kirsanov.mdbo.synchronize.synchronizers.builders;

import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;
import ru.kirsanov.mdbo.synchronize.synchronizers.postgres.*;

import java.sql.Connection;

public class PostgresSynchronizersBuilder implements SynchronizersBuilder {

    private Connection connection;

    public PostgresSynchronizersBuilder(Connection connection) {
        this.connection = connection;
    }

    @Override
    public IEntitySynchronizer createTableSynchronizer() {
        return new PostgresTableSynchronizer(connection);
    }

    @Override
    public IEntitySynchronizer createPrimaryKeySynchronizer() {
        return new PostgresPrimaryKeySynchronizer(connection);
    }

    @Override
    public IEntitySynchronizer createForeignKeySynchronizer() {
        return new PostgresForeignKeySynchronizer(connection);
    }

    @Override
    public IEntitySynchronizer createViewSynchronizer() {
        return new PostgresViewSynchronizer(connection);
    }

    @Override
    public IEntitySynchronizer createIndexSynchronizer() {
        return new PostgresIndexSynchronizer(connection);
    }
}
