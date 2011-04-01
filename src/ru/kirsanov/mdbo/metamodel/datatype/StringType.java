package ru.kirsanov.mdbo.metamodel.datatype;

import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

public class StringType extends Datatype {
    private int size = 0;

    public StringType(int size) {
        this.size = size;
    }

    @Override
    public String getName() {
        return "String";
    }

    @Override
    public String getSqlString() {
        return new StringBuilder("VARCHAR (").append(size).append(")").toString();
    }

    @Override
    public void checkCorrect(Object o) throws IncorrectVariableTypeException {
        if (checkEqualsType(o)) {
            int realSize = ((String) o).length();
            if (realSize <= size) {
                return;
            }
        }
        throw new IncorrectVariableTypeException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringType that = (StringType) o;

        if (size != that.size) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return size;
    }
}
