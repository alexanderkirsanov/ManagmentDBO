package ru.kirsanov.mdbo.metamodel.datatype;

import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

public abstract class Datatype {

    public String getName() {
        return getClass().getSimpleName();
    }

    public abstract String getSqlString();


    public boolean checkEqualsType(Object object) {
        if (object.getClass().getSimpleName().equals(getName())) {
            return true;
        }
        return false;
    }

    public void checkCorrect(Object o) throws IncorrectVariableTypeException {
        if (!checkEqualsType(o)) {
            throw new IncorrectVariableTypeException();
        }
    }
}
