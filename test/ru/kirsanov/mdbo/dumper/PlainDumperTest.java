package ru.kirsanov.mdbo.dumper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.query.TableDumpQuery;
import ru.kirsanov.mdbo.dumper.writer.PlainWriter;
import ru.kirsanov.mdbo.dumper.writer.IWriter;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionData;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionManger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlainDumperTest {

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
            statement.close();
        }
    }

    @Test
    public void executeTest() throws NoColumnForDumpException, FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        PrintWriter mockedPrintWriter = mock(PrintWriter.class);
        IWriter writer = new PlainWriter(mockedPrintWriter);
        PlainDumper plainDumper = new PlainDumper(cm.getConnection(), writer);
        TableDumpQuery tableDumpQuery = new TableDumpQuery("parents");
        tableDumpQuery.addColumn("id");
        plainDumper.execute(tableDumpQuery);
        verify(mockedPrintWriter).write("3");
    }

    @Test
    public void executeWithTrueEncodingTest() throws NoColumnForDumpException, FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        IWriter writer = new PlainWriter("text.txt", PlainWriter.CP1251);
        PlainDumper plainDumper = new PlainDumper(cm.getConnection(), writer);
        TableDumpQuery tableDumpQuery = new TableDumpQuery("parents");
        tableDumpQuery.addColumn("id");
        plainDumper.execute(tableDumpQuery);
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
