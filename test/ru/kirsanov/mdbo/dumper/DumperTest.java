package ru.kirsanov.mdbo.dumper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.dumper.composer.Encoding;
import ru.kirsanov.mdbo.dumper.composer.MultipleInsertSQLComposer;
import ru.kirsanov.mdbo.dumper.composer.PlainComposer;
import ru.kirsanov.mdbo.dumper.exception.DumperNotFound;
import ru.kirsanov.mdbo.dumper.exception.IncorrectDumper;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.query.TableDumpQuery;
import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.entity.PostgresModel;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.metamodel.exception.ViewNotFoundException;
import ru.kirsanov.mdbo.synchronize.Synchronizer;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionData;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionManger;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class DumperTest {

    private ConnectionManger cm;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        cm = new ConnectionManger(new ConnectionData("test", "postgresql"));
        ConnectionManger conn = new ConnectionManger(new ConnectionData("test", "postgresql"));
        Statement statement = null;
        try {
            statement = conn.getConnection().createStatement();
            statement
                    .executeUpdate("DROP TABLE IF EXISTS parents;");

            statement
                    .executeUpdate("CREATE TABLE parents (id INTEGER NOT NULL,\n" +
                            "                     PRIMARY KEY (id)\n" +
                            ") \n");
            statement
                    .executeUpdate("INSERT INTO parents (id) VALUES (1),(2),(3)");
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Test
    public void executeWithTrueEncodingTest() throws NoColumnForDumpException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IncorrectDumper, DumperNotFound {
        Dumper dumper = new Dumper(cm.getConnection(), new MultipleInsertSQLComposer());
        TableDumpQuery tableDumpQuery = new TableDumpQuery("parents");
        tableDumpQuery.addColumn("id");
        dumper.dump(tableDumpQuery);
    }

    @Test
    public void executeWithModelTest() throws NoColumnForDumpException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IncorrectDumper, DumperNotFound, ViewNotFoundException, ColumnAlreadyExistsException, TableNotFound, ColumnNotFoundException, ModelSynchronizerNotFound {
        Dumper dumper = new Dumper(cm.getConnection(), new MultipleInsertSQLComposer());
        Model model = new Synchronizer(cm.getConnection()).synchronize(new PostgresModel("test"));
        dumper.dump(model);
    }
    @Test
    public void executeWithTruePathTest() throws NoColumnForDumpException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IncorrectDumper, DumperNotFound {
        Dumper dumper = new Dumper(cm.getConnection(), new PlainComposer(','), Encoding.UTF8, "/home/lqip32/");
        TableDumpQuery tableDumpQuery = new TableDumpQuery("parents");
        tableDumpQuery.addColumn("id");
        dumper.dump(tableDumpQuery);
    }

    @Test(expected = IOException.class)
    public void setNotExistsPathShouldBeThrowExceptionTest() throws NoColumnForDumpException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IncorrectDumper, DumperNotFound {
        Dumper dumper = new Dumper(cm.getConnection(), new PlainComposer(','), Encoding.UTF8, "/home/lqip32222/");
        TableDumpQuery tableDumpQuery = new TableDumpQuery("parents");
        tableDumpQuery.addColumn("id");
        dumper.dump(tableDumpQuery);
    }

    @Test(expected = IOException.class)
    public void setNullPathShouldBeThrowExceptionTest() throws NoColumnForDumpException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IncorrectDumper, DumperNotFound {
        Dumper dumper = new Dumper(cm.getConnection(), new PlainComposer(','), Encoding.UTF8, "");
        TableDumpQuery tableDumpQuery = new TableDumpQuery("parents");
        tableDumpQuery.addColumn("id");
        dumper.dump(tableDumpQuery);
    }

    @Test(expected = IOException.class)
    public void setNotWritablePathShouldBeThrowExceptionTest() throws NoColumnForDumpException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IncorrectDumper, DumperNotFound {
        Dumper dumper = new Dumper(cm.getConnection(), new PlainComposer(','), Encoding.UTF8, "/");
        TableDumpQuery tableDumpQuery = new TableDumpQuery("parents");
        tableDumpQuery.addColumn("id");
        dumper.dump(tableDumpQuery);
    }

    @After
    public void tearDown() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ConnectionManger conn = new ConnectionManger(new ConnectionData("test", "postgresql"));
        Statement statement = null;
        try {
            statement = conn.getConnection().createStatement();
            statement
                    .executeUpdate("DROP TABLE IF EXISTS parents;");
        } finally {
            if (statement != null) statement.close();
        }
    }
}
