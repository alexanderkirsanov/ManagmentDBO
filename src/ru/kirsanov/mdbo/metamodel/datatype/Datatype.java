package ru.kirsanov.mdbo.metamodel.datatype;

import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

public abstract class Datatype {

    public abstract String getName();

    public abstract String getSqlString();

    public abstract void checkCorrect(Object o) throws IncorrectVariableTypeException;

    public boolean checkEqualsType(Object object) {
        if (object.getClass().getSimpleName().equals(getName())) {
            return true;
        }
        return false;
    }
}
