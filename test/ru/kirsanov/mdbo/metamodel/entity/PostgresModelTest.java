package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostgresModelTest {
    @Test
    public void createdSchemaTest() throws Exception {
        String schemaName = "test";
        Model db = new PostgresModel("db");
        ISchema schema = db.createSchema(schemaName);
        assertEquals(schemaName, schema.getName());
    }
}
