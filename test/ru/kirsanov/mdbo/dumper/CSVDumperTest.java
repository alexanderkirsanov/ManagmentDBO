package ru.kirsanov.mdbo.dumper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.query.TableDumpQuery;
import ru.kirsanov.mdbo.dumper.writer.CSVWriter;
import ru.kirsanov.mdbo.dumper.writer.IWriter;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionData;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionManger;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CSVDumperTest {

    private ConnectionManger cm;

    @Before
    public void setUp() {
        cm = new ConnectionManger(new ConnectionData("test", "postgresql"));
    }

    @Test
    public void executeTest() throws Exception, NoColumnForDumpException {
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
            PrintWriter mockedPrintWriter = mock(PrintWriter.class);
            IWriter writer = new CSVWriter(mockedPrintWriter);
            CSVDumper csvDumper = new CSVDumper(cm.getConnection(), writer);
            TableDumpQuery tableDumpQuery = new TableDumpQuery("parents");
            tableDumpQuery.addColumn("id");
            csvDumper.execute(tableDumpQuery);
            verify(mockedPrintWriter).write("3");
        } finally {
            statement.close();
        }
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
            statement.close();
        }
    }
}
