package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MysqlModelTest {
    @Test
    public void createdSchemaTest() throws Exception {
        String schemaName = "default";
        Model db = new MysqlModel("testDB");
        ISchema schema = db.createSchema(schemaName);
        assertEquals(schemaName, schema.getName());
    }
}
