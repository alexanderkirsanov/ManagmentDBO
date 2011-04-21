package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MysqlDatabaseTest {
    @Test
    public void createdSchemaTest() throws Exception {
        String schemaName = "default";
        Database db = new MysqlDatabase("testDB");
        ISchema schema = db.createSchema(schemaName);
        assertEquals(schemaName, schema.getName());
    }
}
