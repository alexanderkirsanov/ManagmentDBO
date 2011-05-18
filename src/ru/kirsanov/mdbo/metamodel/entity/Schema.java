package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.ForeignKey;
import ru.kirsanov.mdbo.metamodel.exception.ForeignKeyNotFound;
import ru.kirsanov.mdbo.metamodel.exception.IndexNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.metamodel.exception.ViewNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Schema extends MetaObject implements ISchema {
    private List<ITable> tables;
    private Container container;
    private ArrayList<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();
    private ArrayList<IView> views = new ArrayList<IView>();
    private List<IIndex> indexes = new ArrayList<IIndex>();

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

    @Override
    public IView createView(String name, String defenition) {
        IView view = new View(name, defenition);
        views.add(view);
        return view;
    }

    @Override
    public IView getView(String name) throws ViewNotFoundException {
        for (IView view : views) {
            if (view.getName().equals(name)) {
                return view;
            }
        }
        throw new ViewNotFoundException();
    }

    @Override
    public List<IView> getViews() {
        return views;
    }

    @Override
    public IIndex createIndex(String name, IColumn column) {
        IIndex index = new Index(name, column);
        indexes.add(index);
        return index;
    }

    @Override
    public IIndex getIndex(String name) throws IndexNotFoundException {
        for (IIndex index : indexes) {
            if (index.getName().equals(name)) {
                return index;
            }
        }
        throw new IndexNotFoundException();
    }

    @Override
    public List<IIndex> getIndexes() {
        return indexes;
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

        if (container != null ? !container.getName().equals(schema.container.getName()) : schema.container != null)
            return false;
        if (foreignKeys != null ? !foreignKeys.equals(schema.foreignKeys) : schema.foreignKeys != null) return false;
        if (indexes != null ? !indexes.equals(schema.indexes) : schema.indexes != null) return false;
        if (tables != null ? !tables.equals(schema.tables) : schema.tables != null) return false;
        if (views != null ? !views.equals(schema.views) : schema.views != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tables != null ? tables.hashCode() : 0;
        result = 31 * result + (container != null ? container.hashCode() : 0);
        result = 31 * result + (foreignKeys != null ? foreignKeys.hashCode() : 0);
        result = 31 * result + (views != null ? views.hashCode() : 0);
        result = 31 * result + (indexes != null ? indexes.hashCode() : 0);
        return result;
    }
}
