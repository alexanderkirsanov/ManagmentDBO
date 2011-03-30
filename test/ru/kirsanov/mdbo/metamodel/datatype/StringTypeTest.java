package ru.kirsanov.mdbo.metamodel.datatype;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

import static org.junit.Assert.assertEquals;

public class StringTypeTest {
    private Datatype stringType;
    private int length;

    @Before
    public void setUp() {
        length = 10;
        stringType = new StringType(length);
    }

    @Test
    public void getNameTest() throws Exception {
        assertEquals("String", stringType.getName());
    }

    @Test
    public void getSqlStringTest() throws Exception {
        assertEquals("VARCHAR (" + String.valueOf(length) + ")", stringType.getSqlString());
    }

    @Test
    public void checkCorrectTest() throws Exception, IncorrectVariableTypeException {
        String test = "test";
        stringType.checkCorrect(test);
    }

    @Test(expected = IncorrectVariableTypeException.class)
    public void checkIncorrectTest() throws Exception, IncorrectVariableTypeException {
        String test = "test test test";  //length >size
        stringType.checkCorrect(test);
    }
}
