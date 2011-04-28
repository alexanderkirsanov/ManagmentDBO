package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.ForeignKey;
import ru.kirsanov.mdbo.synchronize.exception.ForeignKeyNotFound;
import ru.kirsanov.mdbo.synchronize.exception.TableNotFound;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Schema extends MetaObject implements ISchema {
    private List<ITable> tables;
    private Container container;
    private ArrayList<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();

    public Schema(final String name) {
        super(name);
        tables = new LinkedList<ITable>();
    }

    @Override
    public void addTable(ITable table) {
        this.tables.add(table);
    }

    @Override
    public void removeTable(ITable table) {
        this.tables.remove(table);
    }

    @Override
    public List<ITable> getTables() {
        return this.tables;
    }

    @Override
    public ITable getTable(String str) throws TableNotFound {
        for (ITable table : tables) {
            if (table.getName().equals(str)) {
                return table;
            }
        }
        throw new TableNotFound();
    }

    @Override
    public ForeignKey createForeignKey(String name, ITable sourceTable, ITable targetTable) {
        ForeignKey foreignKey = new ForeignKey(sourceTable, targetTable, name);
        foreignKeys.add(foreignKey);
        return foreignKey;
    }

    @Override
    public List<ForeignKey> getForeignKeys() {
        return this.foreignKeys;
    }

    @Override
    public ForeignKey getForeignKey(String name) throws ForeignKeyNotFound {
        for (ForeignKey foreignKey : foreignKeys) {
            if (foreignKey.getName().equals(name)) {
                return foreignKey;
            }
        }
        throw new ForeignKeyNotFound();
    }

    public Container getParent() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schema schema = (Schema) o;

        if (container != null ? !container.equals(schema.container) : schema.container != null) return false;
        if (tables != null ? !tables.equals(schema.tables) : schema.tables != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tables != null ? tables.hashCode() : 0;
        result = 31 * result + (container != null ? container.hashCode() : 0);
        return result;
    }
}
