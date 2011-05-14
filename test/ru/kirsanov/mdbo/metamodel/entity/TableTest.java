package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.constraint.PrimaryKey;
import ru.kirsanov.mdbo.metamodel.constraint.UniqueKey;
import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

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
        ISchema schema = new Schema("testSchema");
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
    public void removeColumnTest() throws ColumnNotFoundException, ColumnAlreadyExistsException {
        DataType dataType = new SimpleDatatype("INTEGER");
        IColumn column = table.createColumn("test", dataType);
        table.removeColumn(column);
        assertEquals(0, table.getColumns().size());
    }

    @Test(expected = ColumnNotFoundException.class)
    public void removeNotExistsColumnTest() throws ColumnNotFoundException, ColumnAlreadyExistsException {
        DataType dataType = new SimpleDatatype("INTEGER");
        IColumn column = table.createColumn("test", dataType);
        table.removeColumn(column);
        table.removeColumn(column);
    }

    @Test
    public void primaryKeyTest() throws ColumnAlreadyExistsException, ColumnNotFoundException {
        DataType dataType = new SimpleDatatype("INTEGER");
        table.createColumn("test", dataType);
        PrimaryKey pk = table.createPrimaryKey(table.getColumn("test"));
        assertEquals(pk, table.getPrimaryKey());
    }

    @Test(expected = ColumnNotFoundException.class)
    public void addNotExistsColumnToPKTest() throws ColumnAlreadyExistsException, ColumnNotFoundException {
        DataType dataType = new SimpleDatatype("INTEGER");
        table.createColumn("test", dataType);
        PrimaryKey pk = table.createPrimaryKey(table.getColumn("test"));
        Column column = new Column(new Table("test"), "test", new SimpleDatatype("VARCHAR", 60));
        pk.addColumn(column);
    }

    @Test
    public void uniqueKeyTest() throws ColumnAlreadyExistsException, ColumnNotFoundException {
        DataType dataType = new SimpleDatatype("INTEGER");
        table.createColumn("test", dataType);
        UniqueKey uk = table.createUniqueKey(table.getColumn("test"));
        assertEquals(uk, table.getUniqueKey());
    }

    @Test(expected = ColumnNotFoundException.class)
    public void addNotExistsColumnToUKTest() throws ColumnAlreadyExistsException, ColumnNotFoundException {
        DataType dataType = new SimpleDatatype("INTEGER");
        table.createColumn("test", dataType);
        UniqueKey uk = table.createUniqueKey(table.getColumn("test"));
        Column column = new Column(new Table("test"), "test", new SimpleDatatype("VARCHAR", 60));
        uk.addColumn(column);
    }

    @Test
    public void containerTest() {
        ISchema schema = new Schema("test");
        table.setContainer(schema);
        assertEquals(schema, table.getParent());
    }
}
