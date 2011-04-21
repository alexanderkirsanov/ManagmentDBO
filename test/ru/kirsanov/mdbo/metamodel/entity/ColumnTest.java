package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.exception.ElementNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ColumnTest {
    private Column column;
    private DataType integer;
    private Table table;

    @Before
    public void setUp() throws Exception {
        String fieldName = "My_Field";
        integer = new SimpleDatatype("integer");
        table = new Table("test");
        column = new Column(table, fieldName, integer);
    }

    @Test
    public void parentTest(){
        assertEquals(table, column.getParent());
    }
    @Test
    public void dataTypeTest() throws Exception {
        assertEquals(integer, column.getDataType());
        DataType varchar = new SimpleDatatype("varchar", 10);
        column.setDataType(varchar);
        assertEquals(varchar, column.getDataType());
    }

    @Test
    public void addCorrectVariableTest() throws Exception{
        String str = new String("test");
        column.addVariable(str);
        assertEquals(str, column.getVariable(0));
    }

    @Test(expected = ElementNotFoundException.class)
    public void removeVariableTest() throws Exception {
        String str = new String("test");
        column.addVariable(str);
        column.removeVariable(0);
        column.getVariable(0);
    }

    @Test
    public void correctChangeVariableTest() throws Exception {
        String str = new String("test");
        column.addVariable(str);
        String newStr = new String("test2");
        column.changeVariable(newStr, 0);
    }

    @Test(expected = ElementNotFoundException.class)
    public void incorrectChangeVariableTest() throws Exception{
        String str = new String("test");
        column.addVariable(str);
        String newStr = new String("test2");
        column.changeVariable(newStr, 1);
    }


    @Test
    public void nullableTest() {
        column.setNullable(true);
        assertTrue(column.isNullable());
    }

    @Test
    public void uniqueTest() {
        column.setUnique(true);
        assertTrue(column.isUnique());
    }

    @Test
    public void defaultValueTest() {
        String value = "test";
        column.setDefaultValue(value);
        assertEquals(value, column.getDefaultValue());
    }
}
