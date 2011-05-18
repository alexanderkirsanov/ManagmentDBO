package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.ViewNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewTest {
    private IView view;
    private String definition;

    @Before
    public void setUp() {
        definition = "select testbase.t.s1 AS s1,testbase.t.s2 AS s2,testbase.t2.s3 AS s3,testbase.t2.s4 AS s4 from testbase.t join testbase.t2 where (testbase.t.s1 = testbase.t.s2)";
        view = new View("MyView", definition);
    }

    @Test
    public void definitionTest() throws Exception {
        assertEquals(definition, view.getDefinition());
    }

    @Test
    public void updatableTest() throws Exception {
        view.setUpdatable(true);
        assertTrue(view.isUpdatable());
    }

    @Test
    public void checkOptionTest() throws Exception {
        String checkOption = "INSERT";
        view.setCheckOption(checkOption);
        assertEquals(checkOption, view.getCheckOption());
    }

    @Test
    public void columnTest() throws ColumnAlreadyExistsException {
        DataType dataType = new SimpleDatatype("integer", 32);
        Table table = new Table("myTable");
        IColumn column = view.createColumn("first", dataType);
        assertEquals(column, view.getColumns().get(0));
    }

    @Test
    public void getColumnByNameTest() throws ColumnAlreadyExistsException, ColumnNotFoundException {
        DataType dataType = new SimpleDatatype("integer", 32);
        Table table = new Table("myTable");
        String first = "first";
        IColumn column = view.createColumn(first, dataType);
        assertEquals(column, view.getColumn(first));
    }

    @Test(expected = ColumnNotFoundException.class)
    public void getNotExistsColumnByNameTest() throws ColumnAlreadyExistsException, ColumnNotFoundException {
        DataType dataType = new SimpleDatatype("integer", 32);
        Table table = new Table("myTable");
        String existsColumnName = "existsColumnName";
        String notExistsColumnName = "notExistsColumnName";
        IColumn column = view.createColumn(existsColumnName, dataType);
        view.getColumn(notExistsColumnName);
    }

    @Test(expected = ColumnAlreadyExistsException.class)
    public void addAlreadyExistsColumnMustThrowExceptionTest() throws ViewNotFoundException, ColumnAlreadyExistsException {
        ISchema schema = new Schema("mySchema");
        DataType dataType = new SimpleDatatype("int");
        String viewName = "view";
        IView view = schema.createView(viewName, "Select test");
        view.createColumn("fisrtColumn", dataType);
        view.createColumn("fisrtColumn", dataType);
    }

    @Test
    public void containerTest() {
        ISchema schema = new Schema("test");
        view.setContainer(schema);
        assertEquals(schema, view.getParent());
    }
}
