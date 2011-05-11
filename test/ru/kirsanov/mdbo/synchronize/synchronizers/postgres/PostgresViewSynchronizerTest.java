package ru.kirsanov.mdbo.synchronize.synchronizers.postgres;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.*;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionData;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionManger;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class PostgresViewSynchronizerTest {
    private ConnectionManger cm;
    private PostgresModel testModel;

    @Before
    public void setUp() throws ColumnAlreadyExistsException, ColumnNotFoundException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        cm = new ConnectionManger(new ConnectionData("test", "postgresql"));
        testModel = new PostgresModel("testbase");
    }

    @Test
    public void executeTest() throws Throwable {
        ConnectionManger conn = new ConnectionManger(new ConnectionData("test", "postgresql"));
        Statement statement = null;
        try {
            statement = conn.getConnection().createStatement();
            statement
                    .executeUpdate("DROP VIEW IF EXISTS views;");
            statement
                    .executeUpdate("DROP TABLE IF EXISTS t1;");
            statement
                    .executeUpdate("CREATE TABLE t1 (id INT NOT NULL);\n");
            statement
                    .executeUpdate("CREATE VIEW views AS\n" +
                            "  SELECT id FROM t1\n" +
                            "  WHERE id > 5");
            Model model = new PostgresModel("testbase");
            ISchema schema = testModel.createSchema("public");
            ITable t1Table = new Table("t1");
            DataType intDataType = new SimpleDatatype("integer", 32);
            IColumn t1IdColumn = t1Table.createColumn("id", intDataType);
            t1IdColumn.setNullable(false);
            schema.addTable(t1Table);
            IView view = schema.createView("views", "select t1.id from t1 where (t1.id > 5);");
            view.setUpdatable(false);
            view.createColumn("ida", intDataType);
            PostgresTableSynchronizer postgresTableSynchronizer = new PostgresTableSynchronizer(cm.getConnection());
            PostgresViewSynchronizer postgresViewSynchronizer = new PostgresViewSynchronizer(cm.getConnection());
            Model synchronizeModel = postgresViewSynchronizer.execute(postgresTableSynchronizer.execute(model));
            assertEquals(testModel, synchronizeModel);
        } finally {
            statement.close();
        }

    }

    @After
    public void tearDown
            () throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ConnectionManger conn = new ConnectionManger(new ConnectionData("test", "postgresql"));
        Statement statement = conn.getConnection().createStatement();
        statement
                .executeUpdate("DROP VIEW IF EXISTS views;");
        statement
                .executeUpdate("DROP TABLE IF EXISTS t1;");

        statement.close();
        conn.getConnection().close();
    }

}
