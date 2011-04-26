package ru.kirsanov.mdbo.metamodel.constraint;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.IColumn;
import ru.kirsanov.mdbo.metamodel.entity.ITable;
import ru.kirsanov.mdbo.metamodel.entity.Table;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

import static org.junit.Assert.assertEquals;

public class PrimaryKeyTest {
    private ITable table;
    private String pkName;
    private PrimaryKey pk;
    private IColumn column;

    @Before
    public void setUp() throws ColumnAlreadyExistsException {
        table = new Table("test");
        column = table.createColumn("testColumn", new SimpleDatatype("Smallint"));
        pkName = "testPK";
        pk = new PrimaryKey(table, pkName);
    }

    @Test
    public void nameTest() {
        assertEquals(pkName, pk.getName());
    }

    @Test
    public void columnTest() throws ColumnNotFoundException {
        pk.addColumn(column);
        assertEquals(column, pk.getColumns().get(0));
    }

}
