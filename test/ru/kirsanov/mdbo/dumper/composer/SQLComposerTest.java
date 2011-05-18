package ru.kirsanov.mdbo.dumper.composer;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class SQLComposerTest {

    private SQLComposer sqlComposer;

    @Before
    public void setUp() throws Exception {
        sqlComposer = new SQLComposer();
    }


    @Test
    public void addHeaderTest() throws Exception {
        List<String> columns = new LinkedList<String>();
        columns.add("id");
        columns.add("name");
        sqlComposer.addHeader("table", columns);
        assertEquals("INSERT INTO table(id, name) VALUES", sqlComposer.getResults());
    }

    @Test
    public void addBodyTest() throws Exception {
        String[] line = new String[]{"id", "name"};
        sqlComposer.addBody(line);
        assertEquals("('id','name')", sqlComposer.getResults());
    }

    @Test
    public void addEndLineTest() throws Exception {
        sqlComposer.addEndLine();
        assertEquals(",", sqlComposer.getResults());
    }

    @Test
    public void addEndTest() throws Exception {
        sqlComposer.addEnd();
        assertEquals(";", sqlComposer.getResults());
    }

}
