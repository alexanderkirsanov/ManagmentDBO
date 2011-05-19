package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.dumper.parser.Dumpable;

import java.util.List;

public abstract class Model extends MetaObject implements Dumpable {

    public Model(final String name) {
        super(name);
    }


    public abstract ISchema createSchema(String schema);

    public abstract List<ISchema> getSchemas();
}
