package ru.kirsanov.mdbo.synchronize.synchronizers;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.synchronize.synchronizers.builders.ISynchronizersBuilder;
import ru.kirsanov.mdbo.synchronize.synchronizers.builders.MySQLSynchronizersBuilder;
import ru.kirsanov.mdbo.synchronize.synchronizers.mysql.MySQLEntitySynchronizer;

import java.sql.Connection;

public class MySQLSynchronizer implements IEntitySynchronizer{


    private MySQLEntitySynchronizer mySQLEntitySynchronizer;

    public MySQLSynchronizer(Connection connection) {
        ISynchronizersBuilder synchronizersBuilder = new MySQLSynchronizersBuilder(connection);
        mySQLEntitySynchronizer = new MySQLEntitySynchronizer(synchronizersBuilder.getSynchronizers());
    }

    @Override
    public Model execute(Model model) throws Throwable {
        return mySQLEntitySynchronizer.execute(model);
    }
}
