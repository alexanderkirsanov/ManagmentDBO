package ru.kirsanov.mdbo.metamodel.entity;

import org.junit.Test;
import ru.kirsanov.mdbo.synchronize.exception.TableNotFound;

import static org.junit.Assert.assertEquals;

public class SchemaTest {
    private String myTable;

    @Test
    public void tableTest() throws Exception {
        ISchema schema = new Schema("mySchema");
        ITable table = new Table("myTable");
        schema.addTable(new Table("myTable"));
        schema.removeTable(table);
        assertEquals(0, schema.getTables().size());
    }

    @Test
    public void getTableTest() throws Exception, TableNotFound {
        ISchema schema = new Schema("mySchema");
        myTable = "myTable";
        ITable table = new Table(myTable);
        schema.addTable(table);
        assertEquals(table, schema.getTable(myTable));
    }
}
