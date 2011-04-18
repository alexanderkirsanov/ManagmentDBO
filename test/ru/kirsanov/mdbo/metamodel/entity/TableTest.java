package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.NotFoundColumnException;

import static org.junit.Assert.assertEquals;

public class TableTest {
    private Table table;
    private String name;

    @Before
    public void setUp() {
        name = "MyTable";
        table = new Table(name);
    }

    @Test
    public void nameTest() {
        assertEquals(name, table.getName());
    }

    @Test
    public void parentTest() {
        assertEquals(null, table.getParent());
        Schema schema = new Schema("testSchema");
        table = new Table(name, schema);
        assertEquals(schema, table.getParent());
    }

    @Test
    public void columnTest() throws ColumnAlreadyExistsException {
        DataType dataType = new SimpleDatatype("INTEGER");
        table.createColumn("test", dataType);
        assertEquals(1, table.getColumns().size());
    }

    @Test(expected = ColumnAlreadyExistsException.class)
    public void addExistsColumnMustReturnExceptionTest() throws ColumnAlreadyExistsException {
        DataType dataType = new SimpleDatatype("INTEGER");
        table.createColumn("test", dataType);
        table.createColumn("test", dataType);
    }

    @Test
    public void removeColumnTest() throws ColumnNotFoundException, ColumnAlreadyExistsException, NotFoundColumnException {
        DataType dataType = new SimpleDatatype("INTEGER");
        Column column = table.createColumn("test", dataType);
        table.removeColumn(column);
        assertEquals(0, table.getColumns().size());
    }

    @Test(expected = NotFoundColumnException.class)
    public void removeNotExistsColumnTest() throws ColumnNotFoundException, ColumnAlreadyExistsException, NotFoundColumnException {
        DataType dataType = new SimpleDatatype("INTEGER");
        Column column = table.createColumn("test", dataType);
        table.removeColumn(column);
        table.removeColumn(column);
    }

    @Test
    public void primaryKeyTest() throws ColumnAlreadyExistsException {
        DataType dataType = new SimpleDatatype("INTEGER");
        table.createColumn("test", dataType);
    }
}
