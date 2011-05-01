package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;

import static org.junit.Assert.assertEquals;

public class IndexTest {
    private ITable table;
    private IColumn column;
    private IIndex index;

    @Before
    public void setUp() throws ColumnAlreadyExistsException {
        table = new Table("MyTable");
        column = table.createColumn("myColumn", new SimpleDatatype("VARCHAR", 60));
        index = new Index("test", column, 19, index.UNIQUE);
    }

    @Test
    public void constraintsTest() throws Exception {
        index.createConstraint(">0");
        assertEquals(">0", index.getConstraint().getConstraintString());
    }

    @Test
    public void indexTest() throws Exception {
        assertEquals(column, index.getColumn());
        assertEquals(19, index.getCount());
        assertEquals(index.UNIQUE, index.getType());
    }

    @Test
    public void countTest() throws Exception {
        int count = 10;
        index.setCount(count);
        assertEquals(count, index.getCount());
    }

    @Test
    public void typeTest() throws Exception {
        index.setType(IIndex.FULL_TEXT_SEARCH);
        assertEquals(IIndex.FULL_TEXT_SEARCH, index.getType());
    }

}
