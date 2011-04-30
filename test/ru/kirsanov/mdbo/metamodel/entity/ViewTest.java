package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Before;
import org.junit.Test;

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
}
