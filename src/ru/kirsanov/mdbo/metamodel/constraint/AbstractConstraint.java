package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.Column;
import ru.kirsanov.mdbo.metamodel.entity.Table;

import java.util.List;

public abstract  class AbstractConstraint implements Constraint{
    private Table table;
    private String name;
    private List<Column> columns;

    public AbstractConstraint(Table table, String name) {
		this.table = table;
		this.name = name;
	}

	public Table getTable() {
		return table;
	}

	public String getName() {
		return name;
	}

	public Iterable<Column> getColumns() {
		return columns;
	}

	public void addColumn(Column column) {
		if ( column.getTable() != getTable() ) {
			throw new IllegalArgumentException( "column not found" );
		}
		columns.add(column);
	}
}
