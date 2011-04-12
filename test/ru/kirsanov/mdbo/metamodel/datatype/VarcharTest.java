package ru.kirsanov.mdbo.metamodel.datatype;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

import static org.junit.Assert.assertEquals;

public class VarcharTest {
    private Datatype stringType;
    private int length;

    @Before
    public void setUp() {
        length = 10;
        stringType = new Varchar(length);
    }

    @Test
    public void getNameTest() throws Exception {
        assertEquals("Varchar", stringType.getName());
    }

    @Test
    public void getSqlStringTest() throws Exception {
        assertEquals("Varchar (" + String.valueOf(length) + ")", stringType.getSqlString());
    }

    @Test
    public void checkCorrectTest() throws Exception, IncorrectVariableTypeException {
        Varchar test = new Varchar(9);
        stringType.checkCorrect(test);
    }

    @Test(expected = IncorrectVariableTypeException.class)
    public void checkIncorrectTest() throws Exception, IncorrectVariableTypeException {
        Varchar test = new Varchar(12);  //length >size
        stringType.checkCorrect(test);
    }
}
