package ru.kirsanov.mdbo.metamodel.constraint;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.Column;
import ru.kirsanov.mdbo.metamodel.entity.Table;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

import static org.junit.Assert.assertEquals;

public class ForeignKeyTest {
    private Table sourceTable;
    private Table targetTable;
    private Column sourceColumn;
    private Column targetColumn;
    private ForeignKey foreignKey;

    @Before
    public void setUp() throws Exception, ColumnAlreadyExistsException {
        sourceTable = new Table("source");
        sourceColumn = sourceTable.createColumn("sourceColumn", new SimpleDatatype("Smallint"));
        targetTable = new Table("target");
        targetColumn = targetTable.createColumn("targetColumn", new SimpleDatatype("Smallint"));
        foreignKey = new ForeignKey(sourceTable, targetTable);
    }

    @Test
    public void getSourceTableTest() throws Exception {
        assertEquals(sourceTable, foreignKey.getSourceTable());
    }

    @Test
    public void getTargetTableTest() throws Exception {
        assertEquals(targetTable, foreignKey.getTargetTable());
    }


    @Test
    public void getSourceColumnsTest() throws Exception, ColumnNotFoundException {
        foreignKey.addColumnMapping(sourceColumn,targetColumn);
        assertEquals(sourceColumn, foreignKey.getSourceColumns().get(0));
    }

       @Test
    public void getTargetColumnsTest() throws Exception, ColumnNotFoundException {
            foreignKey.addColumnMapping(sourceColumn,targetColumn);
        assertEquals(targetColumn, foreignKey.getTargetColumns().get(0));
    }

    @Test
    public void testGetDeleteRule() throws Exception {
        foreignKey.setDeleteRule(ForeignKey.ReferentialAction.CASCADE);
        assertEquals(ForeignKey.ReferentialAction.CASCADE, foreignKey.getDeleteRule());
    }

    @Test
    public void testGetUpdateRule() throws Exception {
        foreignKey.setUpdateRule(ForeignKey.ReferentialAction.CASCADE);
        assertEquals(ForeignKey.ReferentialAction.CASCADE, foreignKey.getUpdateRule());
    }
}
