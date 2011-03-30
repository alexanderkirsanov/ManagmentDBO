package ru.kirsanov.mdbo.metamodel.datatype;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

import static org.junit.Assert.assertEquals;

public class IntegerTypeTest {

    private Datatype datatype;

    @Before
    public void setUp(){
        datatype = new IntegerType();
    }
    @Test
    public void getNameTest() throws Exception {
        assertEquals("Integer", datatype.getName());
    }

    @Test
    public void getSqlStringTest() throws Exception {
        assertEquals("integer", datatype.getSqlString());
    }

    @Test
    public void checkCorrectTest() throws Exception, IncorrectVariableTypeException {
        Integer integer = new Integer(10);
        datatype.checkCorrect(integer);
    }

    @Test(expected = IncorrectVariableTypeException.class)
    public void checkIncorrectTest() throws Exception, IncorrectVariableTypeException {
        String str = new String("10");
        datatype.checkCorrect(str);
    }
}
