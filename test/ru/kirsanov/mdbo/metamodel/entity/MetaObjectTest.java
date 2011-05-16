package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MetaObjectTest {
    @Test
    public void nameTextTest() {
        String metaObjectString = "MetaObject";
        MetaObject metaObject = new MetaObject(metaObjectString);
        assertEquals(metaObjectString, metaObject.getName());
    }


}
