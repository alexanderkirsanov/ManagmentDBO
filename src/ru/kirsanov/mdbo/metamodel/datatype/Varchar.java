package ru.kirsanov.mdbo.metamodel.datatype;

import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

public class Varchar extends Datatype {
    private int size = 0;

    public Varchar(int size) {
        this.size = size;
    }

    @Override
    public String getSqlString() {
        return new StringBuilder("Varchar (").append(size).append(")").toString();
    }

    @Override
    public void checkCorrect(Object o) throws IncorrectVariableTypeException {
        if (checkEqualsType(o)) {
            int realSize = ((Varchar) o).length();
            if (realSize <= size) {
                return;
            }
        }
        throw new IncorrectVariableTypeException();
    }

    public  int length(){
        return size;
    }

}
