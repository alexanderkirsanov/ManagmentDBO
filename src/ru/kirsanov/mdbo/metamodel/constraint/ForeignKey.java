package ru.kirsanov.mdbo.metamodel.constraint;


import ru.kirsanov.mdbo.metamodel.entity.IColumn;
import ru.kirsanov.mdbo.metamodel.entity.ITable;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ForeignKey extends AbstractConstraint implements Constraint {

    private final ITable targetTable;
    private List<IColumn> targetColumns;
    private ReferentialAction deleteRule = ReferentialAction.NO_ACTION;
    private ReferentialAction updateRule = ReferentialAction.NO_ACTION;

    public ForeignKey(ITable sourceTable, ITable targetTable, String name) {
        super(sourceTable, name);
        this.targetTable = targetTable;
    }

    protected ForeignKey(ITable sourceTable, ITable targetTable) {
        this(sourceTable, targetTable, null);
    }

    public ITable getSourceTable() {
        return getTable();
    }

    public ITable getTargetTable() {
        return targetTable;
    }

    public List<IColumn> getSourceColumns() {
        return getColumns();
    }

    public List<IColumn> getTargetColumns() {
        return targetColumns == null
                ? getTargetTable().getPrimaryKey().getColumns()
                : targetColumns;
    }

    @Override
    @Deprecated
    public void addColumn(IColumn column) throws ColumnNotFoundException {
        addColumnMapping(column, null);
    }

    public void addColumnMapping(IColumn sourceColumn, IColumn targetColumn) throws ColumnNotFoundException {
        if (getSourceTable().getColumns().contains(sourceColumn) && getTargetTable().getColumns().contains(targetColumn)) {
            if (targetColumns == null) {
                targetColumns = new ArrayList<IColumn>();
            }
            targetColumns.add(targetColumn);
            super.addColumn(sourceColumn);
        } else {
            throw new ColumnNotFoundException();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForeignKey that = (ForeignKey) o;

        if (deleteRule != that.deleteRule) return false;
        if (targetColumns != null ? !targetColumns.equals(that.targetColumns) : that.targetColumns != null)
            return false;
        if (targetTable != null ? !targetTable.equals(that.targetTable) : that.targetTable != null) return false;
        if (updateRule != that.updateRule) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = targetTable != null ? targetTable.hashCode() : 0;
        result = 31 * result + (targetColumns != null ? targetColumns.hashCode() : 0);
        result = 31 * result + (deleteRule != null ? deleteRule.hashCode() : 0);
        result = 31 * result + (updateRule != null ? updateRule.hashCode() : 0);
        return result;
    }

    public static enum ReferentialAction {
        NO_ACTION,
        CASCADE,
        SET_NULL,
        SET_DEFAULT,
        RESTRICT
    }
}
