package ru.kirsanov.mdbo.synchronize.synchronizers;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.entity.ISchema;
import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.entity.MysqlModel;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionData;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionManger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConfiguratorTest {

    private Configurator configurator;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection connection = new ConnectionManger(new ConnectionData("information_schema", "mysql")).getConnection();
        configurator = new Configurator(connection);
    }

    @Test(expected = ModelSynchronizerNotFound.class)
    public void getNotExistsSynchronizerTest() throws ModelSynchronizerNotFound {
        Model model = new Model("test") {
            @Override
            public ISchema createSchema(String schema) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public List<ISchema> getSchemas() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        configurator.getSynchronizers(model);
    }


    @Test
    public void getExistsSynchronizerTest() throws ModelSynchronizerNotFound {
        Model model = new MysqlModel("test");
        IEntitySynchronizer entitySynchronizer = configurator.getSynchronizers(model);
        assertEquals(DatabaseSynchronizer.class.getSimpleName(), entitySynchronizer.getClass().getSimpleName());
    }
}
