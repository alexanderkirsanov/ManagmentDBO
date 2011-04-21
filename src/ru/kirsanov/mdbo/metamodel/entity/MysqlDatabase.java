package ru.kirsanov.mdbo.metamodel.entity;

import java.util.List;

public class MysqlDatabase extends Database {
    private List<ITable> tables;
    private Container container;
    private Schema schema;

    public MysqlDatabase(final String name) {
        super(name);
        schema = new Schema("default");
    }

    public ISchema createSchema(String name){
        return schema;
    }

}
