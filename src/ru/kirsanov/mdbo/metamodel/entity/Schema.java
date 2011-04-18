package ru.kirsanov.mdbo.metamodel.entity;

import java.util.LinkedList;
import java.util.List;

public class Schema extends MetaObject implements Container {
    private List<Table> tables;
    private Container container;

    public Schema(final String name) {
        super(name);
        tables = new LinkedList<Table>();
    }

    public void addTable(Table table) {
        this.tables.add(table);
    }

    public void removeTable(Table table) {
        this.tables.remove(table);
    }

    public List<Table> getTables() {
        return this.tables;
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
