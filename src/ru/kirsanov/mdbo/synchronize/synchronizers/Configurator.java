package ru.kirsanov.mdbo.synchronize.synchronizers;

import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.entity.MysqlModel;
import ru.kirsanov.mdbo.metamodel.entity.PostgresModel;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.builders.MySQLSynchronizersBuilder;
import ru.kirsanov.mdbo.synchronize.synchronizers.builders.PostgresSynchronizersBuilder;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Configurator {
    private Connection connection;
    private Map<String, IEntitySynchronizer> modelSynchronizers;

    public Configurator(Connection connection) {
        this.connection = connection;
        modelSynchronizers = new HashMap<String, IEntitySynchronizer>();
        modelSynchronizers.put(MysqlModel.class.getSimpleName(), new DatabaseSynchronizer(new MySQLSynchronizersBuilder(connection)));
        modelSynchronizers.put(PostgresModel.class.getSimpleName(), new DatabaseSynchronizer(new PostgresSynchronizersBuilder(connection)));
    }

    public IEntitySynchronizer getSynchronizers(Model model) throws ModelSynchronizerNotFound {
        IEntitySynchronizer returnSynchronizer = modelSynchronizers.get(model.getClass().getSimpleName());
        if (returnSynchronizer == null) throw new ModelSynchronizerNotFound();
        return returnSynchronizer;
    }
}
