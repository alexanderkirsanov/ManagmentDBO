package ru.kirsanov.mdbo.metamodel.datatype;

public class SmallInt extends Datatype {

    @Override
    public String getSqlString() {
        return "smallint";
    }

}
