package ru.kirsanov.mdbo.metamodel.entity;

import java.util.ArrayList;
import java.util.List;

public class PostgresModel extends Model {
    private List<ISchema> schemas = new ArrayList<ISchema>();

    public PostgresModel(final String name) {
        super(name);
    }

    public ISchema createSchema(String name) {
        Schema schema = new Schema(name);
        schemas.add(schema);
        return schema;
    }

    @Override
    public List<ISchema> getSchemas() {
        return schemas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostgresModel that = (PostgresModel) o;

        if (schemas != null ? !schemas.equals(that.schemas) : that.schemas != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return schemas != null ? schemas.hashCode() : 0;
    }
}
