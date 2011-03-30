package ru.kirsanov.mdbo.metamodel.datatype;

import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

public class IntegerType extends Datatype {
    private String name = "Integer";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSqlString() {
        return "integer";
    }

    @Override
    public void checkCorrect(Object o) throws IncorrectVariableTypeException {
        if (!checkEqualsType(o)) {
            throw new IncorrectVariableTypeException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntegerType that = (IntegerType) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
