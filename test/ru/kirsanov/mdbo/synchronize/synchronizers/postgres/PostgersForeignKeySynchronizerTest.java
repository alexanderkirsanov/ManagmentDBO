package ru.kirsanov.mdbo.synchronize.synchronizers.postgres;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.constraint.ForeignKey;
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

public class PostgersForeignKeySynchronizerTest {
    private PostgresModel testModel;
    private ConnectionManger cm;

    @Before
    public void setUp() throws ColumnAlreadyExistsException, ColumnNotFoundException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
                    .executeUpdate("DROP TABLE IF EXISTS childs;");

            statement
                    .executeUpdate("DROP TABLE IF EXISTS parents;");
            statement
                    .executeUpdate("CREATE TABLE parents (id INTEGER NOT NULL,\n" +
                            "                     PRIMARY KEY (id)\n" +
                            ") \n");
            statement
                    .executeUpdate("CREATE TABLE childs (id INT, parent_id INT\n" +
                            "                     REFERENCES parents(id)\n" +
                            "                      ON DELETE CASCADE\n" +
                            ")");
            Model model = new PostgresModel(ConnectionData.getBaseName());
            ISchema schema = testModel.createSchema("public");
            ITable parentsTable = new Table("parents");
            schema.addTable(parentsTable);
            DataType intDataType = new SimpleDatatype("integer", 32);
            IColumn parentsIdColumn = parentsTable.createColumn("id", intDataType);
            parentsIdColumn.setNullable(false);
            ITable childsTable = new Table("childs");
            schema.addTable(childsTable);
            IColumn childsIdColumn = childsTable.createColumn("id", intDataType);
            childsIdColumn.setNullable(true);
            IColumn childsParentIdColumn = childsTable.createColumn("parent_id", intDataType);
            childsParentIdColumn.setNullable(true);
            PostgresTableSynchronizer postgresTableSynchronizer = new PostgresTableSynchronizer(cm.getConnection());
            PostgresForeignKeySynchronizer postgresForeignKeySynchronizer = new PostgresForeignKeySynchronizer(cm.getConnection());
            Model synchronizeModel = postgresForeignKeySynchronizer.execute(postgresTableSynchronizer.execute(model));
            ForeignKey fk = schema.createForeignKey("childs_parent_id_fkey", childsTable, parentsTable);
            fk.addColumnMapping(childsParentIdColumn, parentsIdColumn);
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
                .executeUpdate("DROP TABLE IF EXISTS childs;");
        statement
                .executeUpdate("DROP TABLE IF EXISTS parents;");
        statement.close();
        conn.getConnection().close();
    }

}
