package ru.kirsanov.mdbo.synchronize.synchronizers.mysql;

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

public class MySQLViewSynchronizerTest {
    private ConnectionManger cm;
    private MysqlModel testModel;

    @Before
    public void setUp() throws ColumnAlreadyExistsException, ColumnNotFoundException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        cm = new ConnectionManger(new ConnectionData("information_schema", "mysql"));
        testModel = new MysqlModel("testbase");
    }

    @Test
    public void executeTest() throws Throwable {
        ConnectionManger conn = new ConnectionManger(new ConnectionData("testbase", "mysql"));
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
            Model model = new MysqlModel("testbase");
            ISchema schema = testModel.createSchema("testbase");
            ITable t1Table = new Table("t1");
            DataType intDataType = new SimpleDatatype("int", 11);
            IColumn t1IdColumn = t1Table.createColumn("id", intDataType);
            t1IdColumn.setNullable(false);
            schema.addTable(t1Table);
            IView view = schema.createView("views", "select `testbase`.`t1`.`id` as `id` from `testbase`.`t1` where (`testbase`.`t1`.`id` > 5)");
            view.setUpdatable(true);
            view.createColumn("id",intDataType);
            MySQLTableSynchronizer mySQLTableSynchronizer = new MySQLTableSynchronizer(cm.getConnection());
            MySQLViewSynchronizer mySQlViewSynchronizer = new MySQLViewSynchronizer(cm.getConnection());
            Model synchronizeModel = mySQlViewSynchronizer.execute(mySQLTableSynchronizer.execute(model));
            assertEquals(testModel, synchronizeModel);
        } finally {
            statement.close();
        }

    }

    @After
    public void tearDown
            () throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ConnectionManger conn = new ConnectionManger(new ConnectionData("testbase", "mysql"));
        Statement statement = conn.getConnection().createStatement();
        statement
                .executeUpdate("DROP VIEW IF EXISTS views;");
        statement
                .executeUpdate("DROP TABLE IF EXISTS t1;");

        statement.close();
        conn.getConnection().close();
    }

}
