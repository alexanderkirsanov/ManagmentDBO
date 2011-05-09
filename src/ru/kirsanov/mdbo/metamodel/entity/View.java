package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class View extends MetaObject implements IView {

    private String definition;
    private String checkOption = "none";
    private boolean updatable;
    private Container parent;
    private List<IColumn> columns = new ArrayList<IColumn>();

    public View(final String name, final String definition) {
        super(name);
        this.definition = definition;
    }


    @Override
    public String getDefinition() {
        return this.definition;
    }

    @Override
    public String getCheckOption() {
        return this.checkOption;
    }

    @Override
    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    @Override
    public boolean isUpdatable() {
        return this.updatable;
    }

    @Override
    public void setUpdatable(boolean value) {
        this.updatable = value;
    }

    @Override
    public void addColumn(IColumn column) {
        this.columns.add(column);
    }

    @Override
    public List<IColumn> getColumns() {
        return columns;
    }

    @Override
    public void removeColumn(IColumn column) {
        columns.remove(column);
    }

    @Override
    public IColumn getColumnByName(String name) throws ColumnNotFoundException {
        for (IColumn column : columns) {
            if (column.getName().equals(name)) {
                return column;
            }
        }
        throw new ColumnNotFoundException();
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public void setContainer(Container container) {
        this.parent = container;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        View view = (View) o;

        if (updatable != view.updatable) return false;
        if (checkOption != null ? !checkOption.equals(view.checkOption) : view.checkOption != null) return false;
        if (columns != null ? !columns.equals(view.columns) : view.columns != null) return false;
        if (definition != null ? !definition.equals(view.definition) : view.definition != null) return false;
        if (parent != null ? !parent.getName().equals(view.parent.getName()) : view.parent != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = definition != null ? definition.hashCode() : 0;
        result = 31 * result + (checkOption != null ? checkOption.hashCode() : 0);
        result = 31 * result + (updatable ? 1 : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (columns != null ? columns.hashCode() : 0);
        return result;
    }
}
