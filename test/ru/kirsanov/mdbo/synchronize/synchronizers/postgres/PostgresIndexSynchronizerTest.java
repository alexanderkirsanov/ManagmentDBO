package ru.kirsanov.mdbo.synchronize.synchronizers.postgres;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.*;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionData;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionManger;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class PostgresIndexSynchronizerTest {
    private PostgresModel testModel;
    private ConnectionManger cm;

    @Before
    public void setUp() {
        cm = new ConnectionManger(new ConnectionData(ConnectionData.getBaseName(), "postgresql"));
        testModel = new PostgresModel(ConnectionData.getBaseName());
    }

    @Test
    public void executeTest() throws Throwable {
        ConnectionManger conn = new ConnectionManger(new ConnectionData(ConnectionData.getBaseName(), "postgresql"));
        Statement statement = null;
        try {
            statement = conn.getConnection().createStatement();
            statement
                    .executeUpdate("DROP TABLE IF EXISTS t1 CASCADE;");
            statement
                    .executeUpdate("CREATE TABLE t1 (id INT NOT NULL);\n");
            statement
                    .executeUpdate("CREATE Index myIndex ON t1(id) ");
            Model model = new PostgresModel(ConnectionData.getBaseName());
            ISchema schema = testModel.createSchema(ConnectionData.getBaseName());
            ITable t1Table = new Table("t1");
            DataType intDataType = new SimpleDatatype("integer", 32);
            IColumn t1IdColumn = t1Table.createColumn("id", intDataType);
            t1IdColumn.setNullable(false);
            schema.addTable(t1Table);
            schema.createIndex("myIndex", t1IdColumn);
            PostgresTableSynchronizer postgresTableSynchronizer = new PostgresTableSynchronizer(cm.getConnection());
            PostgresIndexSynchronizer postgresIndexSynchronizer = new PostgresIndexSynchronizer(cm.getConnection());
            Model synchronizeModel = postgresIndexSynchronizer.execute(postgresTableSynchronizer.execute(model));
            assertEquals(testModel, synchronizeModel);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

    }

    @After
    public void tearDown
            () throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ConnectionManger conn = new ConnectionManger(new ConnectionData(ConnectionData.getBaseName(), "postgresql"));
        Statement statement = conn.getConnection().createStatement();
        statement
                .executeUpdate("DROP TABLE IF EXISTS t1;");
        statement.close();
        conn.getConnection().close();
    }
}

