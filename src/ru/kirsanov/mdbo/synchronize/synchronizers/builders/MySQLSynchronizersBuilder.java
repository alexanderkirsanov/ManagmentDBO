package ru.kirsanov.mdbo.synchronize.synchronizers.builders;

import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;
import ru.kirsanov.mdbo.synchronize.synchronizers.mysql.MySQLForeignKeySynchronizer;
import ru.kirsanov.mdbo.synchronize.synchronizers.mysql.MySQLPrimaryKeySynchronizer;
import ru.kirsanov.mdbo.synchronize.synchronizers.mysql.MySQLTableSynchronizer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MySQLSynchronizersBuilder implements ISynchronizersBuilder {

    private Connection connection;

    public MySQLSynchronizersBuilder(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<IEntitySynchronizer> getSynchronizers() {
        IEntitySynchronizer mySQLTableSynchronizer = new MySQLTableSynchronizer(connection);
        IEntitySynchronizer mySQLPrimaryKeySynchronizer = new MySQLPrimaryKeySynchronizer(connection);
        IEntitySynchronizer mySQLForeignKeySynchronizer = new MySQLForeignKeySynchronizer(connection);
        IEntitySynchronizer mySQLViewSynchronizer = new MySQLForeignKeySynchronizer(connection);
        List<IEntitySynchronizer> synchronizers = new ArrayList<IEntitySynchronizer>();
        synchronizers.add(mySQLTableSynchronizer);
        synchronizers.add(mySQLPrimaryKeySynchronizer);
        synchronizers.add(mySQLForeignKeySynchronizer);
        synchronizers.add(mySQLViewSynchronizer);
        return synchronizers;
    }

}
