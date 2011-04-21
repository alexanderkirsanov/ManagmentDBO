package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostgresDatabaseTest {
    @Test
    public void createdSchemaTest() throws Exception {
        String schemaName = "test";
        Database db = new PostgresDatabase("db");
        ISchema schema = db.createSchema(schemaName);
        assertEquals(schemaName, schema.getName());
    }
}
