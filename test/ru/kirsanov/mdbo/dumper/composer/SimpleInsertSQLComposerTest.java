package ru.kirsanov.mdbo.dumper.composer;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class SimpleInsertSQLComposerTest {

    private SimpleInsertSQLComposer simpleInsertSqlComposer;

    @Before
    public void setUp() throws Exception {
        simpleInsertSqlComposer = new SimpleInsertSQLComposer();
    }

    @Test
    public void addHeaderTest() throws Exception {
        List<String> columns = new LinkedList<String>();
        columns.add("id");
        columns.add("name");
        simpleInsertSqlComposer.addHeader("table", columns);
        assertEquals("INSERT INTO table(id, name) VALUES", simpleInsertSqlComposer.getResults());
    }

    @Test
    public void addBodyTest() throws Exception {
        String[] line = new String[]{"id", "name"};
        simpleInsertSqlComposer.addBody(line);
        assertEquals("('id','name')", simpleInsertSqlComposer.getResults());
    }

    @Test
    public void addEndLineTest() throws Exception {
        simpleInsertSqlComposer.addEndLine();
        assertEquals(",", simpleInsertSqlComposer.getResults());
    }

    @Test
    public void addEndTest() throws Exception {
        simpleInsertSqlComposer.addEnd();
        assertEquals(";", simpleInsertSqlComposer.getResults());
    }

}
