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

public class PostgresPrimaryKeySynchronizerTest {
    private PostgresModel testModel;
    private ConnectionManger cm;

    @Before
    public void setUp() throws Throwable {
        cm = new ConnectionManger(new ConnectionData("localhost", "test", "postgresql", "lqip32", "4f3v6"));
        testModel = new PostgresModel("testbase");
    }

    @Test
    public void executeTest() throws Throwable {
        Statement statement = null;
        try {
              ConnectionManger conn = new ConnectionManger(new ConnectionData("localhost", "test", "postgresql", "lqip32", "4f3v6"));
            statement = conn.getConnection().createStatement();
            statement
                    .executeUpdate("DROP TABLE IF EXISTS t1;");
            statement.executeUpdate("CREATE TABLE t1\n" +
                    "(\n" +
                    "    s1 INT,\n" +
                    "    s2 INT,\n" +
                    "    s3 INT,\n" +
                    "    PRIMARY KEY(s3)\n" +
                    ")");
            PostgresTableSynchronizer postgresTableSynchronizer = new PostgresTableSynchronizer(cm.getConnection());
            PostgresPrimaryKeySynchronizer postgresPrimaryKeySynchronizer = new PostgresPrimaryKeySynchronizer(cm.getConnection());
            Model modelWithTable = postgresTableSynchronizer.execute(new PostgresModel("testbase"));
            ISchema schema = testModel.createSchema("public");
            ITable table = new Table("t1");
            schema.addTable(table);
            DataType intDataType = new SimpleDatatype("integer", 32);
            IColumn s1Column = table.createColumn("s1", intDataType);
            s1Column.setNullable(true);
            IColumn s2Column = table.createColumn("s2", intDataType);
            s2Column.setNullable(true);
            IColumn pColumn = table.createColumn("s3", intDataType);
            table.createPrimaryKey(pColumn);
            Model pkSynchronizeModel = postgresPrimaryKeySynchronizer.execute(modelWithTable);
            assertEquals(testModel, pkSynchronizeModel);
        } finally {
            statement.close();
            cm.getConnection().close();
        }
    }

    @After
    public void tearDown
            () throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ConnectionManger conn = new ConnectionManger(new ConnectionData("localhost", "test", "postgresql", "lqip32", "4f3v6"));
        Statement statement = conn.getConnection().createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS t1;");
        statement.close();
    }
}
