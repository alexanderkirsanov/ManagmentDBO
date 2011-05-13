package ru.kirsanov.mdbo.dumper;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;

import static org.junit.Assert.assertEquals;

public class TableDumperTest {
    private ITableDumper tableDumper;
    private String tableName;

    @Before
    public void setUp() {
        tableName = "tablename";
        tableDumper = new TableDumper(tableName);
    }

    @Test
    public void getSqlForOneColumnTest() throws NoColumnForDumpException {
        String columnName = "*";
        tableDumper.addColumn(columnName);
        String query = "select " + columnName + " from " + tableName + ";";
        assertEquals(query, tableDumper.getSql().toLowerCase());
    }

    @Test
    public void getSqlForManyColumnTest() throws NoColumnForDumpException {
        String firstColumnName = "id";
        String secondColumnName = "name";
        tableDumper.addColumn(firstColumnName);
        tableDumper.addColumn(secondColumnName);
        String query = "select " + firstColumnName + " ," + secondColumnName + " from " + tableName + ";";
        assertEquals(query, tableDumper.getSql().toLowerCase());
    }

    @Test(expected = NoColumnForDumpException.class)
    public void getSqlForNoColumnShouldBeThrowsExceptionTest() throws NoColumnForDumpException {
        tableDumper.getSql().toLowerCase();
    }
}
