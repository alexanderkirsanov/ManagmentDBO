package ru.kirsanov.mdbo.metamodel;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.SmallInt;
import ru.kirsanov.mdbo.metamodel.datatype.Varchar;
import ru.kirsanov.mdbo.metamodel.entity.Column;
import ru.kirsanov.mdbo.metamodel.entity.Table;
import ru.kirsanov.mdbo.metamodel.exception.ElementNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ColumnTest {
    private Column column;
    private SmallInt integer;

    @Before
    public void setUp() throws Exception {
        String fieldName = "My_Field";
        integer = new SmallInt();
        Table table = new Table("test");
        column = new Column(table, fieldName, integer);
    }

    @Test
    public void dataTypeTest() throws Exception {
        assertEquals(integer, column.getDataType());
        Varchar string = new Varchar(10);
        column.setDataType(string);

        assertEquals(string, column.getDataType());
    }

    @Test
    public void addCorrectVariableTest() throws Exception, IncorrectVariableTypeException {
        SmallInt integerVariable = new SmallInt();
        column.addVariable(integerVariable);
        assertEquals(integerVariable, column.getVariable(0));
    }

    @Test(expected = IncorrectVariableTypeException.class)
    public void AddIncorrectVariableTest() throws Exception, IncorrectVariableTypeException {
        Varchar stringVariable = new Varchar(10);
        column.addVariable(stringVariable);
        assertEquals(stringVariable, column.getVariable(0));
    }

    @Test(expected = ElementNotFoundException.class)
    public void removeVariableTest() throws Exception, IncorrectVariableTypeException {
        SmallInt integerVariable = new SmallInt();
        column.addVariable(integerVariable);
        column.removeVariable(0);
        column.getVariable(0);
    }

    @Test
    public void correctChangeVariableTest() throws Exception, IncorrectVariableTypeException {
        SmallInt integerVariable = new SmallInt();
        column.addVariable(integerVariable);
        SmallInt newIntegerValue = new SmallInt();
        column.changeVariable(newIntegerValue, 0);
    }

    @Test(expected = ElementNotFoundException.class)
    public void incorrectChangeVariableTest() throws Exception, IncorrectVariableTypeException {
        SmallInt integerVariable = new SmallInt();
        column.addVariable(integerVariable);
        SmallInt newIntegerValue = new SmallInt();
        column.changeVariable(newIntegerValue, 1);
    }

    @Test(expected = IncorrectVariableTypeException.class)
    public void incorrectVariableTypeChangeTest() throws Exception, IncorrectVariableTypeException {
        SmallInt integerVariable = new SmallInt();
        column.addVariable(integerVariable);
        String string = new String("10");
        column.changeVariable(string, 0);
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
