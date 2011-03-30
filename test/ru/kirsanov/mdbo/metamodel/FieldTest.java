package ru.kirsanov.mdbo.metamodel;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.IntegerType;
import ru.kirsanov.mdbo.metamodel.datatype.StringType;
import ru.kirsanov.mdbo.metamodel.entity.Field;
import ru.kirsanov.mdbo.metamodel.exception.ElementNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

import static org.junit.Assert.assertEquals;

public class FieldTest {
    private Field field;
    private IntegerType integer;

    @Before
    public void setUp() throws Exception {
        String fieldName = "My_Field";
        integer = new IntegerType();
        field = new Field(fieldName, integer);
    }

    @Test
    public void dataTypeTest() throws Exception {
        assertEquals(integer, field.getDataType());
        StringType string = new StringType(10);
        field.setDataType(string);

        assertEquals(string, field.getDataType());
    }

    @Test
    public void addCorrectVariableTest() throws Exception, IncorrectVariableTypeException {
        Integer integerVariable = new Integer(10);
        field.addVariable(integerVariable);
        assertEquals(integerVariable, field.getVariable(0));
    }

    @Test(expected = IncorrectVariableTypeException.class)
    public void AddIncorrectVariableTest() throws Exception, IncorrectVariableTypeException {
        String stringVariable = new String("10");
        field.addVariable(stringVariable);
        assertEquals(stringVariable, field.getVariable(0));
    }

    @Test(expected = ElementNotFoundException.class)
    public void removeVariableTest() throws Exception, IncorrectVariableTypeException {
        Integer integerVariable = new Integer(10);
        field.addVariable(integerVariable);
        field.removeVariable(0);
        field.getVariable(0);
    }

    @Test
    public void correctChangeVariableTest() throws Exception, IncorrectVariableTypeException {
        Integer integerVariable = new Integer(10);
        field.addVariable(integerVariable);
        Integer newIntegerValue = new Integer(20);
        field.changeVariable(newIntegerValue, 0);
    }

    @Test(expected = ElementNotFoundException.class)
    public void incorrectChangeVariableTest() throws Exception, IncorrectVariableTypeException {
        Integer integerVariable = new Integer(10);
        field.addVariable(integerVariable);
        Integer newIntegerValue = new Integer(20);
        field.changeVariable(newIntegerValue, 1);
    }

    @Test(expected = IncorrectVariableTypeException.class)
    public void incorrectVariableTypeChangeTest() throws Exception, IncorrectVariableTypeException {
        Integer integerVariable = new Integer(10);
        field.addVariable(integerVariable);
        String string = new String("10");
        field.changeVariable(string, 0);
    }
}
