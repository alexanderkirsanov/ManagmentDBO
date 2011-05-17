package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.IColumn;

public class EntityConstraint extends AbstractConstraint {
    private String constraintString;

    public EntityConstraint(IColumn column, String constraintString) {
        super(column.getTable(), null);
        this.constraintString = constraintString;
    }

    public String getConstraintString() {
        return this.constraintString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityConstraint that = (EntityConstraint) o;

        if (constraintString != null ? !constraintString.equals(that.constraintString) : that.constraintString != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return constraintString != null ? constraintString.hashCode() : 0;
    }
}
