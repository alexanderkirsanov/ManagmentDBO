package ru.kirsanov.mdbo.synchronize.synchronizers.mysql;

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

public class MySQLIndexSynchronizerTest {
    private MysqlModel testModel;
    private ConnectionManger cm;

    @Before
    public void setUp() {
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
                    .executeUpdate("DROP TABLE IF EXISTS t1;");
            statement
                    .executeUpdate("CREATE TABLE t1 (id INT NOT NULL);\n");
            statement
                    .executeUpdate("CREATE Index myIndex ON t1(id) ");
            Model model = new MysqlModel("testbase");
            ISchema schema = testModel.createSchema("testbase");
            ITable t1Table = new Table("t1");
            DataType intDataType = new SimpleDatatype("INT", 11);
            IColumn t1IdColumn = t1Table.createColumn("id", intDataType);
            t1IdColumn.setNullable(false);
            schema.addTable(t1Table);
            schema.createIndex("myIndex", t1IdColumn);
            MySQLTableSynchronizer mySQLTableSynchronizer = new MySQLTableSynchronizer(cm.getConnection());
            MySQLIndexSynchronizer mySQlIndexSynchronizer = new MySQLIndexSynchronizer(cm.getConnection());
            Model synchronizeModel = mySQlIndexSynchronizer.execute(mySQLTableSynchronizer.execute(model));
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
        ConnectionManger conn = new ConnectionManger(new ConnectionData("testbase", "mysql"));
        Statement statement = conn.getConnection().createStatement();
        statement
                .executeUpdate("DROP TABLE IF EXISTS t1;");
        statement.close();
        conn.getConnection().close();
    }
}

