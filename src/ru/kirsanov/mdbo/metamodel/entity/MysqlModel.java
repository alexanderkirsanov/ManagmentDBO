package ru.kirsanov.mdbo.metamodel.entity;

import java.util.ArrayList;
import java.util.List;

public class MysqlModel extends Model {
    private List<ISchema> schemas = new ArrayList<ISchema>();

    public MysqlModel(final String dataBaseName) {
        super(dataBaseName);
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

        MysqlModel that = (MysqlModel) o;

        if (schemas != null ? !schemas.equals(that.schemas) : that.schemas != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return schemas != null ? schemas.hashCode() : 0;
    }
}
