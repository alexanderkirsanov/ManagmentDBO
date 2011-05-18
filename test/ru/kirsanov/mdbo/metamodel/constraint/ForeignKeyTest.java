package ru.kirsanov.mdbo.metamodel.constraint;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.Column;
import ru.kirsanov.mdbo.metamodel.entity.IColumn;
import ru.kirsanov.mdbo.metamodel.entity.ITable;
import ru.kirsanov.mdbo.metamodel.entity.Table;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

import static org.junit.Assert.assertEquals;

public class ForeignKeyTest {
    private ITable sourceTable;
    private ITable targetTable;
    private IColumn sourceColumn;
    private IColumn targetColumn;
    private ForeignKey foreignKey;

    @Before
    public void setUp() throws ColumnAlreadyExistsException {
        sourceTable = new Table("source");
        sourceColumn = sourceTable.createColumn("sourceColumn", new SimpleDatatype("Smallint"));
        targetTable = new Table("target");
        targetColumn = targetTable.createColumn("targetColumn", new SimpleDatatype("Smallint"));
        foreignKey = new ForeignKey(sourceTable, targetTable);
    }

    @Test
    public void getSourceTableTest() {
        assertEquals(sourceTable, foreignKey.getSourceTable());
    }

    @Test
    public void getTargetTableTest() {
        assertEquals(targetTable, foreignKey.getTargetTable());
    }


    @Test
    public void getSourceColumnsTest() throws ColumnNotFoundException {
        foreignKey.addColumnMapping(sourceColumn, targetColumn);
        assertEquals(sourceColumn, foreignKey.getSourceColumns().get(0));
    }

    @Test
    public void getTargetColumnsTest() throws ColumnNotFoundException {
        foreignKey.addColumnMapping(sourceColumn, targetColumn);
        assertEquals(targetColumn, foreignKey.getTargetColumns().get(0));
    }

    @Test(expected = ColumnNotFoundException.class)
    public void getNotExistsTargetColumnsShouldBeThrowsExceptionTest() throws ColumnNotFoundException {
        sourceColumn = new Column(new Table("test"), "sourceColumn", new SimpleDatatype("Smallint"));
        foreignKey.addColumnMapping(sourceColumn, targetColumn);
        foreignKey.getTargetColumns().get(0);
    }

    @Test
    public void getDeleteRuleTest() {
        foreignKey.setDeleteRule(ForeignKey.ReferentialAction.CASCADE);
        assertEquals(ForeignKey.ReferentialAction.CASCADE, foreignKey.getDeleteRule());
    }

    @Test
    public void testGetUpdateRule() {
        foreignKey.setUpdateRule(ForeignKey.ReferentialAction.CASCADE);
        assertEquals(ForeignKey.ReferentialAction.CASCADE, foreignKey.getUpdateRule());
    }
}
