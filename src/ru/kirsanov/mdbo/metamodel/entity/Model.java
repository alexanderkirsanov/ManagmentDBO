package ru.kirsanov.mdbo.metamodel.entity;

import java.util.List;

public abstract class Model extends MetaObject {

    public Model(final String name) {
        super(name);
    }


    public abstract ISchema createSchema(String schema);

    public abstract List<ISchema> getSchemas();
}
