package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SchemaTest {
    @Test
    public void tableTest() throws Exception {
        Schema schema = new Schema("mySchema");
        Table table = new Table("myTable");
        schema.addTable(new Table("myTable"));
        schema.removeTable(table);
        assertEquals(0, schema.getTables().size());
    }
}
