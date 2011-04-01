package ru.kirsanov.mdbo.metamodel.constraint;


import ru.kirsanov.mdbo.metamodel.entity.Column;
import ru.kirsanov.mdbo.metamodel.entity.Table;

import java.util.ArrayList;
import java.util.List;
//todo: доработать
public class ForeignKey extends AbstractConstraint implements Constraint {

	private final Table targetTable;
	private List<Column> targetColumns;
	private ReferentialAction deleteRule = ReferentialAction.NO_ACTION;
	public ReferentialAction updateRule = ReferentialAction.NO_ACTION;

	public ForeignKey(Table sourceTable, Table targetTable, String name) {
		super( sourceTable, name );
		this.targetTable = targetTable;
	}

	protected ForeignKey(Table sourceTable, Table targetTable) {
		this( sourceTable, targetTable, null );
	}

	public Table getSourceTable() {
		return getTable();
	}

	public Table getTargetTable() {
		return targetTable;
	}

	public Iterable<Column> getSourceColumns() {
		return getColumns();
	}

	public Iterable<Column> getTargetColumns() {
		return targetColumns == null
				? getTargetTable().getPrimaryKey().getColumns()
				: targetColumns;
	}

	@Override
	public void addColumn(Column column) {
		addColumnMapping( column, null );
	}

	public void addColumnMapping(Column sourceColumn, Column targetColumn) {
		if ( targetColumn == null ) {
			if ( targetColumns != null ) {
                ///todo:add exception
			}
		}
		else {
			if ( targetColumns == null ) {
				targetColumns = new ArrayList<Column>();
			}
			targetColumns.add( targetColumn );
		}
	}


	public ReferentialAction getDeleteRule() {
		return deleteRule;
	}

	public void setDeleteRule(ReferentialAction deleteRule) {
		this.deleteRule = deleteRule;
	}

	public ReferentialAction getUpdateRule() {
		return updateRule;
	}

	public void setUpdateRule(ReferentialAction updateRule) {
		this.updateRule = updateRule;
	}

	public static enum ReferentialAction {
		NO_ACTION,
		CASCADE,
		SET_NULL,
		SET_DEFAULT,
		RESTRICT
	}
}
