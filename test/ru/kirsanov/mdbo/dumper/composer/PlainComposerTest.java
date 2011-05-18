package ru.kirsanov.mdbo.dumper.composer;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class PlainComposerTest {
    private IComposer plainComposer;

    @Before
    public void setUp() throws Exception {
        plainComposer = new PlainComposer(',');
    }

    @Test
    public void addHeaderTest() throws Exception {
        List<String> columns = new LinkedList<String>();
        columns.add("id");
        columns.add("name");
        plainComposer.addHeader("table", columns);
        assertEquals("# table:id, name\n", plainComposer.getResults());
    }

    @Test
    public void addBodyTest() throws Exception {
        String[] line = new String[]{"id1", "name1"};
        plainComposer.addBody(line);
        assertEquals("id1,name1", plainComposer.getResults());
    }

    @Test
    public void addBodyWithDelimiterTest() {
        String[] line = {"a,as", "b", "c"};
        plainComposer.addBody(line);
        assertEquals("\"a,as\",b,c", plainComposer.getResults());
    }

    @Test
    public void addBodyWithSpaceTest() {
        String[] line = {"a as", "b", "c"};
        plainComposer.addBody(line);
        assertEquals("a as,b,c", plainComposer.getResults());
    }

    @Test
    public void addBodyWithSpaceAndSpaceDelimiterTest() {
        String[] line = {"a as", "b", "c"};
        plainComposer = new PlainComposer(' ');
        plainComposer.addBody(line);
        assertEquals("\"a as\" b c", plainComposer.getResults());
    }

    @Test
    public void textWithSpecialSymbolsWriteTest() {
        String[] line = {"a\\as", "b", "c"};
        plainComposer = new PlainComposer(';');
        plainComposer.addBody(line);
        assertEquals("\"a\\as\";b;c", plainComposer.getResults());
    }

    @Test
    public void addEndTest() throws Exception {
        plainComposer.addEnd();
        assertEquals("\n#end", plainComposer.getResults());
    }
}
