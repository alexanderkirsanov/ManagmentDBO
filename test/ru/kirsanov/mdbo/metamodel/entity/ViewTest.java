package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;

import static org.junit.Assert.assertEquals;

public class ViewTest {
    private IColumn column;
    private ITable table;
    private IView view;

    @Before
    public void setUp() throws ColumnAlreadyExistsException {
        view = new View("MyView");
        table = new Table("MyTable");
        column = table.createColumn("myColumn", new SimpleDatatype("VARCHAR", 60));

    }

    @Test
    public void columnsTest() throws Exception {
        view.addColumn(column);
        assertEquals(column, view.getColumns().get(0));
    }

    @Test
    public void constraintsTest() throws Exception {
        view.addColumn(column);
        view.createConstraint(column, ">0");
        assertEquals(">0", view.getConstraints().get(0).getConstraintString());
    }
}
