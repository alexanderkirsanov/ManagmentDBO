package ru.kirsanov.mdbo.metamodel.entity;

import java.util.ArrayList;
import java.util.List;

public class PostgresDatabase extends Database {
    private List<Schema> schemas = new ArrayList<Schema>();

    public PostgresDatabase(final String name) {
        super(name);
    }

    public Schema createSchema(String name) {
        Schema schema = new Schema(name);
        schemas.add(schema);
        return schema;
    }

}
