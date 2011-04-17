package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.exception.ElementNotFoundException;

import java.util.ArrayList;

public class Column extends MetaObject implements Container {

    private Container container;
    private DataType dataType;
    private ArrayList<String> variableList;
    private boolean nullable;
    private boolean unique;
    private String defaultValue;
    private Table table;

    public Column(final Table table, final String name, final DataType dataType) {
        super(name);
        setContainer(table);
        this.table = table;
        this.dataType = dataType;
        variableList = new ArrayList<String>();
    }

    public Container getParent() {
        return this.container;
    }

    public Table getTable() {
        return this.table;
    }

    public void setContainer(final Container container) {
        this.container = container;
    }

    public void setDataType(final DataType dataType) {
        this.dataType = dataType;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public void addVariable(String variable) {
        variableList.add(variable);
    }

    public void removeVariable(int id) throws ElementNotFoundException {
        if (id < variableList.size()) {
            variableList.remove(id);
        } else {
            throw new ElementNotFoundException();
        }
    }

    public String getVariable(int id) throws ElementNotFoundException {
        if (id < variableList.size()) {
            return variableList.get(id);
        } else {
            throw new ElementNotFoundException();
        }
    }

    public void changeVariable(String variable, int id) throws ElementNotFoundException {
        if (id < variableList.size()) {
            variableList.set(id, variable);
        } else {
            throw new ElementNotFoundException();
        }
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (nullable != column.nullable) return false;
        if (unique != column.unique) return false;
        if (table != null ? !table.getName().equals(column.table.getName()) : column.table != null) return false;
        if (variableList != null ? !variableList.equals(column.variableList) : column.variableList != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = container != null ? container.hashCode() : 0;
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (variableList != null ? variableList.hashCode() : 0);
        result = 31 * result + (nullable ? 1 : 0);
        result = 31 * result + (unique ? 1 : 0);
        result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
        result = 31 * result + (table != null ? table.hashCode() : 0);
        return result;
    }
}
