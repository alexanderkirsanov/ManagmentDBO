package ru.kirsanov.mdbo.dumper.composer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class MultipleInsertSQLComposerTest {
    private IComposer multipleInsertComposer;

    @Before
    public void setUp() throws Exception {
        multipleInsertComposer = new MultipleInsertSQLComposer();
        List<String> columns = new ArrayList<String>() {{
            add("id");
            add("name");
        }};
        multipleInsertComposer.addHeader("table", columns);
    }

    @Test
    public void addBodyTest() throws Exception {
        multipleInsertComposer.addBody(new String[]{"a", "b"});
        assertEquals("INSERT INTO table(id, name) VALUES('a','b')", multipleInsertComposer.getResults());
    }

    @Test
    public void testAddEnd() throws Exception {
        multipleInsertComposer.addEnd();
        assertEquals(";\n", multipleInsertComposer.getResults());
    }
}
