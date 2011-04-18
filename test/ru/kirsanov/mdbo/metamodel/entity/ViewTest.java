package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;

import static org.junit.Assert.assertEquals;

public class ViewTest {
    private Column column;
    private Table table;
    private View view;

    @Before
    public void setUp() {
        view = new View("MyView");
        table = new Table("MyTable");
        column = table.createColumn("myColumn", new SimpleDatatype("VARCHAR",60));

    }

    @Test
    public void columnsTest() throws Exception {
        view.addColumn(column);
        assertEquals(column, view.getColumns().get(0));
    }
}
