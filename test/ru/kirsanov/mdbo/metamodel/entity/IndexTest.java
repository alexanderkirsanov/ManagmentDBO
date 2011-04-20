package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;

import static org.junit.Assert.assertEquals;

public class IndexTest {
    private Table table;
    private Column column;
    private Index index;

    @Before
    public void setUp() throws ColumnAlreadyExistsException {
        table = new Table("MyTable");
        column = table.createColumn("myColumn", new SimpleDatatype("VARCHAR", 60));
        index = new Index("test", column, 19);
    }

    @Test
    public void constraintsTest() throws Exception {
        index.createConstraint(">0");
        assertEquals(">0", index.getConstraint().getConstraintString());
    }

}
