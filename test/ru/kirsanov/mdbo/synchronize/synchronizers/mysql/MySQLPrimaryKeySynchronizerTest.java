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

public class MySQLPrimaryKeySynchronizerTest {
    private MysqlModel testModel;
    private ConnectionManger cm;

    @Before
    public void setUp() throws Throwable {
        cm = new ConnectionManger(new ConnectionData("information_schema", "mysql"));
        testModel = new MysqlModel("testbase");

    }

    @Test
    public void executeTest() throws Throwable {
        Statement statement = null;
        try {
            ConnectionManger conn = new ConnectionManger(new ConnectionData("testbase", "mysql"));
            statement = conn.getConnection().createStatement();
            statement
                    .executeUpdate("DROP TABLE IF EXISTS t1;");
            statement.executeUpdate("CREATE TABLE t1\n" +
                    "(\n" +
                    "    s1 INT,\n" +
                    "    s2 INT,\n" +
                    "    s3 INT,\n" +
                    "    PRIMARY KEY(s3)\n" +
                    ") ENGINE=InnoDB;");
            MySQLTableSynchronizer mySQLTableSynchronizer = new MySQLTableSynchronizer(cm.getConnection());
            MySQLPrimaryKeySynchronizer mySQLPrimaryKeySynchronizer = new MySQLPrimaryKeySynchronizer(cm.getConnection());
            Model modelWithTable = mySQLTableSynchronizer.execute(new MysqlModel("testbase"));
            ISchema schema = testModel.createSchema("testbase");
            ITable table = new Table("t1");
            schema.addTable(table);
            DataType intDataType = new SimpleDatatype("INT", 11);
            IColumn s1Column = table.createColumn("s1", intDataType);
            s1Column.setNullable(true);
            IColumn s2Column = table.createColumn("s2", intDataType);
            s2Column.setNullable(true);
            IColumn pColumn = table.createColumn("s3", intDataType);
            table.createPrimaryKey(pColumn);
            Model pkSynchronizeModel = mySQLPrimaryKeySynchronizer.execute(modelWithTable);
            assertEquals(testModel, pkSynchronizeModel);
        } finally {
            if (statement != null) {
                statement.close();
            }

            cm.getConnection().close();
        }
    }

    @After
    public void tearDown
            () throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ConnectionManger conn = new ConnectionManger(new ConnectionData("testbase", "mysql"));
        Statement statement = conn.getConnection().createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS t1;");
        statement.close();
    }
}
