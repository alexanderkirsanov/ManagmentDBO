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

public class UniqueKeyTest{
    private ITable table;
    private IColumn column;
    private String uniqueKeyName;
    private UniqueKey uniqueKey;

    @Before
    public void setUp() throws ColumnAlreadyExistsException {
        table = new Table("test");
        column = table.createColumn("testColumn", new SimpleDatatype("Smallint"));
        uniqueKeyName = "uKey";
        uniqueKey = new UniqueKey(table, uniqueKeyName);
    }

    @Test
    public void nameTest() {
        assertEquals(uniqueKeyName, uniqueKey.getName());
    }

    @Test
    public void columnTest() throws ColumnNotFoundException {
        uniqueKey.addColumn(column);
        assertEquals(column, uniqueKey.getColumns().get(0));
    }

}
