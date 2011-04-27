package ru.kirsanov.mdbo.synchronize;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.*;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.synchronize.exception.ConnectionNotSet;
import ru.kirsanov.mdbo.synchronize.exception.IncorrectDataBaseType;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.mysql.MySQLModelSynchronizer;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionData;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionManger;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class MySQLModelSynchronizerTest {
    private ConnectionManger cm;
    private Model testModel;

    @Before
    public void setUp() throws ColumnAlreadyExistsException, ColumnNotFoundException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        testModel = new MysqlModel("testbase");
        ISchema schema = testModel.createSchema("testbase");
        Table testFkTable = new Table("test_fk");
        schema.addTable(testFkTable);
        IColumn idFkColumn = testFkTable.createColumn("id", new SimpleDatatype("int", 11));
        idFkColumn.setNullable(false);
        // testFkTable.createPrimaryKey(idFkColumn);
        IColumn testId = testFkTable.createColumn("test_id", new SimpleDatatype("int", 11));
        testId.setNullable(true);
        Table testTable = new Table("test");
        schema.addTable(testTable);
        IColumn idColumn = testTable.createColumn("id", new SimpleDatatype("int", 11));
        idColumn.setNullable(true);
        IColumn numberColumn = testTable.createColumn("numbers", new SimpleDatatype("int", 11));
        numberColumn.setNullable(true);
        IColumn testColumn = testTable.createColumn("test", new SimpleDatatype("varchar", 30));
        testColumn.setDefaultValue("text");
        testColumn.setNullable(true);
        IColumn testRealColumn = testTable.createColumn("test_real", new SimpleDatatype("double", 10, 10));
        testRealColumn.setNullable(true);


        cm = new ConnectionManger(new ConnectionData("localhost", "information_schema", "mysql", "lqip32", "4f3v6"));
        ConnectionManger conn = new ConnectionManger(new ConnectionData("localhost", "testbase", "mysql", "lqip32", "4f3v6"));
        Statement statement = conn.getConnection().createStatement();
        try {
            statement
                    .executeUpdate("DROP TABLE IF EXISTS test;");
            statement.executeUpdate("CREATE TABLE test(" +
                    "  id int," +
                    "  numbers int ," +
                    "  test varchar(30) DEFAULT 'text'," +
                    "  test_real double(10,10)\n" +
                    ");");
            statement.executeUpdate("DROP TABLE IF EXISTS test_fk;\n");
            statement.executeUpdate(
                    "CREATE TABLE test_fk (\n" +
                            "  id int(11) NOT NULL,\n" +
                            "  test_id int(11) DEFAULT NULL,\n" +
                            "  PRIMARY KEY (id)\n" +
                            ");");
        } finally {
            statement.close();
        }
    }

    @Test
    public void createDataTypeTest() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        MySQLModelSynchronizer mySqlModel = new MySQLModelSynchronizer(cm.getConnection());
        DataType dataType = mySqlModel.createDataType("integer(1,1)", "integer");
        DataType integerType = new SimpleDatatype("integer", 1, 1);
        assertEquals(integerType, dataType);
    }

    @Test
    public void synchronizeTest() throws SQLException, IncorrectDataBaseType, ConnectionNotSet, ColumnAlreadyExistsException, ColumnNotFoundException, ModelSynchronizerNotFound, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Model model = new MysqlModel("testbase");
        MySQLModelSynchronizer mySqlModel = new MySQLModelSynchronizer(cm.getConnection());
        Model mysqlModel = mySqlModel.execute(model);
        assertEquals(testModel, mysqlModel);
    }
}
