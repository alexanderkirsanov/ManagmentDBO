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

}
