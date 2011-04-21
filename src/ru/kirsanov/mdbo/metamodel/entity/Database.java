package ru.kirsanov.mdbo.metamodel.entity;

public abstract class Database extends MetaObject {

    public Database(final String name) {
        super(name);
    }

    public abstract ISchema createSchema(String schema);
}
