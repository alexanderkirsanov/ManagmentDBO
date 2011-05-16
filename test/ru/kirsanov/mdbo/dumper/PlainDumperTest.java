package ru.kirsanov.mdbo.dumper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.query.TableDumpQuery;
import ru.kirsanov.mdbo.dumper.writer.IWriter;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionData;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionManger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Statement;

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
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Test
    public void executeWithTrueEncodingTest() throws NoColumnForDumpException, FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        PlainDumper plainDumper = new PlainDumper(cm.getConnection());
        plainDumper.setEncoding(IWriter.CP1251);
        TableDumpQuery tableDumpQuery = new TableDumpQuery("parents");
        tableDumpQuery.addColumn("id");
        plainDumper.execute(tableDumpQuery);
    }

    @Test
    public void executeWithTruePathTest() throws NoColumnForDumpException, IOException, UnsupportedEncodingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        PlainDumper plainDumper = new PlainDumper(cm.getConnection());
        plainDumper.setPath("/home/lqip32/");
        TableDumpQuery tableDumpQuery = new TableDumpQuery("parents");
        tableDumpQuery.addColumn("id");
        plainDumper.execute(tableDumpQuery);
    }

    @Test(expected = IOException.class)
    public void setNotExistsPathShouldBeThrowExceptionTest() throws NoColumnForDumpException, IOException, UnsupportedEncodingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        PlainDumper plainDumper = new PlainDumper(cm.getConnection());
        plainDumper.setPath("/home/desktop/lqip322");

    }

    @Test(expected = IOException.class)
    public void setNullPathShouldBeThrowExceptionTest() throws NoColumnForDumpException, IOException, UnsupportedEncodingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        PlainDumper plainDumper = new PlainDumper(cm.getConnection());
        plainDumper.setPath("");
    }

    @Test(expected = IOException.class)
    public void setNotWritablePathShouldBeThrowExceptionTest() throws NoColumnForDumpException, IOException, UnsupportedEncodingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        PlainDumper plainDumper = new PlainDumper(cm.getConnection());
        plainDumper.setPath("/");
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
