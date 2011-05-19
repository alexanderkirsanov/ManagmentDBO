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

    @Test
    public void renameTest() {
        String metaObjectString = "MetaObject";
        MetaObject metaObject = new MetaObject(metaObjectString);
        String newName = "name";
        metaObject.rename(newName);
        assertEquals(newName, metaObject.getName());
    }

}
