package ru.kirsanov.mdbo.synchronize.synchronizers.postgres;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.*;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionData;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionManger;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class PostgresTableSynchronizerTest {
    private ConnectionManger cm;
    private Model testModel;

    @Before
    public void setUp() throws ColumnAlreadyExistsException, ColumnNotFoundException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        cm = new ConnectionManger(new ConnectionData("test", "postgresql"));
        testModel = new PostgresModel("testbase");
    }

    @Test
    public void synchronizeTest() throws Throwable {
        ISchema schema = testModel.createSchema("public");
        Table testTable = new Table("table2");
        schema.addTable(testTable);
        IColumn testId = testTable.createColumn("id", new SimpleDatatype("integer", 32, 0));
        testId.setNullable(false);
        IColumn testName = testTable.createColumn("name", new SimpleDatatype("character varying", 12));
        testName.setNullable(true);

        ConnectionManger conn = new ConnectionManger(new ConnectionData("test", "postgresql"));
        Statement statement = conn.getConnection().createStatement();
        try {
            statement
                    .executeUpdate("DROP TABLE IF EXISTS table2;");
            statement.executeUpdate("CREATE TABLE table2 (\n" +
                    "    id integer NOT NULL,\n" +
                    "    name character varying(12)\n" +
                    ");");
            Model model = new PostgresModel("testbase");
            PostgresTableSynchronizer postgresTableSynchronizer = new PostgresTableSynchronizer(cm.getConnection());
            Model postgresModel = postgresTableSynchronizer.execute(model);
            assertEquals(testModel, postgresModel);
        } finally {
            statement.close();
            conn.getConnection().close();
        }
    }

    @After
    public void tearDown
            () throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ConnectionManger conn = new ConnectionManger(new ConnectionData("test", "postgresql"));
        Statement statement = conn.getConnection().createStatement();
        statement
                .executeUpdate("DROP TABLE IF EXISTS table2;");
        statement
                .executeUpdate("DROP TABLE IF EXISTS test;");
        statement.close();
        conn.getConnection().close();
    }

}
