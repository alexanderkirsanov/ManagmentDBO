package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

import java.util.ArrayList;
import java.util.List;

public abstract class ColumnContainer extends MetaObject implements Container {
    protected List<IColumn> columns = new ArrayList<IColumn>();
    protected Container container;

    public ColumnContainer(final String name) {
        super(name);
    }

    public IColumn createColumn(final String name, final DataType dataType) throws ColumnAlreadyExistsException {
        try {
            getColumn(name);
            throw new ColumnAlreadyExistsException();
        } catch (ColumnNotFoundException e) {
            Column column = new Column(this, name, dataType);
            this.columns.add(column);
            return column;
        }
    }

    public IColumn getColumn(String name) throws ColumnNotFoundException {
        for (IColumn column : columns) {
            if (column.getName().equals(name)) {
                return column;
            }
        }
        throw new ColumnNotFoundException();
    }

    public void removeColumn(final IColumn column) throws ColumnNotFoundException {
        if (getColumn(column.getName()) == null) throw new ColumnNotFoundException();
        this.columns.remove(column);
    }

    public Container getParent() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public List<IColumn> getColumns() {
        return this.columns;
    }
}
