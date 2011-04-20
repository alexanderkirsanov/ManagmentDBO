package ru.kirsanov.mdbo.metamodel.constraint;

import org.junit.Test;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.Column;
import ru.kirsanov.mdbo.metamodel.entity.Table;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;

import static org.junit.Assert.assertEquals;

public class EntityConstraintTest {
    @Test
    public void testGetConstraintString() throws Exception, ColumnAlreadyExistsException {
        Table table = new Table("test");
        Column column = table.createColumn("testColumn", new SimpleDatatype("integer"));

        EntityConstraint constraint = new EntityConstraint(column, ">0");
        assertEquals(">0", constraint.getConstraintString());
    }
}
