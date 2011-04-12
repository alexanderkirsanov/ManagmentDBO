package ru.kirsanov.mdbo.metamodel.datatype;

public class Integer extends  Datatype{

    @Override
    public String getSqlString() {
        return "integer";
    }

}
