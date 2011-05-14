package ru.kirsanov.mdbo.dumper;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.query.ITableDumpQuery;
import ru.kirsanov.mdbo.dumper.query.TableDumpQuery;

import static org.junit.Assert.assertEquals;

public class TableDumpQueryTest {
    private ITableDumpQuery tableDumpQuery;
    private String tableName;

    @Before
    public void setUp() {
        tableName = "tablename";
        tableDumpQuery = new TableDumpQuery(tableName);
    }

    @Test
    public void getSqlForOneColumnTest() throws NoColumnForDumpException {
        String columnName = "*";
        tableDumpQuery.addColumn(columnName);
        String query = "select " + columnName + " from " + tableName + ";";
        assertEquals(query, tableDumpQuery.getSql().toLowerCase());
    }

    @Test
    public void getSqlForManyColumnTest() throws NoColumnForDumpException {
        String firstColumnName = "id";
        String secondColumnName = "name";
        tableDumpQuery.addColumn(firstColumnName);
        tableDumpQuery.addColumn(secondColumnName);
        String query = "select " + firstColumnName + " ," + secondColumnName + " from " + tableName + ";";
        assertEquals(query, tableDumpQuery.getSql().toLowerCase());
    }

    @Test(expected = NoColumnForDumpException.class)
    public void getSqlForNoColumnShouldBeThrowsExceptionTest() throws NoColumnForDumpException {
        tableDumpQuery.getSql().toLowerCase();
    }
}
