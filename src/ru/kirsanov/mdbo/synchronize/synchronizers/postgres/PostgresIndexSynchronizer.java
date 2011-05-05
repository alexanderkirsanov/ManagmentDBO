package ru.kirsanov.mdbo.synchronize.synchronizers.postgres;

import ru.kirsanov.mdbo.metamodel.entity.*;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PostgresIndexSynchronizer implements IEntitySynchronizer {

    private static final String INDEX_NAME = "INDEX_NAME";
    private static final String NON_UNIQUE = "NON_UNIQUE";
    private static final String COLUMN_NAME = "attname";
    private static final String TABLE_NAME = "rel_name";
    private static final String INDISUNIQUE = "indisunique";
    private static final String SCHEMA_NAME = "nsp";
    private Connection connection;

    public PostgresIndexSynchronizer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Model execute(Model model) throws Throwable {
        if (!(model instanceof PostgresModel)) throw new ModelSynchronizerNotFound();
        PreparedStatement selectInformationFromSysTable = connection
                .prepareStatement("\n" +
                        "  SELECT a.index_name, \n" +
                        "         b.attname,\n" +
                        "         a.indisunique,\n" +
                        "         a.indisprimary,\n" +
                        "         a.rel_name,\n" +
                        "         a.nsp\n" +
                        "    FROM ( SELECT a.indrelid,\n" +
                        "                  a.indisunique,\n" +
                        "                  a.indisprimary, \n" +
                        "                  c.relname index_name, \n" +
                        "                  b.relname rel_name,\n" +
                        "                  d.nspname nsp,\n" +
                        "                  unnest(a.indkey) index_num \n" +
                        "             FROM pg_index a, \n" +
                        "                  pg_class b, \n" +
                        "                  pg_class c,\n" +
                        "                  pg_namespace d \n" +
                        "            WHERE b.oid=a.indrelid \n" +
                        "              AND a.indexrelid=c.oid AND  d.oid = b.relnamespace AND d.nspname NOT IN('pg_catalog','information_schema', 'pg_toast')) a, \n" +
                        "         pg_attribute b \n" +
                        "   WHERE a.indrelid = b.attrelid \n" +
                        "     AND a.index_num = b.attnum \n" +
                        "ORDER BY a.index_name, \n" +
                        "         a.index_num");
        connection.setAutoCommit(false);
        List<ISchema> schemas = model.getSchemas();
        ResultSet resultSetOfConstraintTable = selectInformationFromSysTable.executeQuery();
        while (resultSetOfConstraintTable.next()) {
            String indexName = resultSetOfConstraintTable.getString(INDEX_NAME).toLowerCase();
            Boolean isUnique = resultSetOfConstraintTable.getBoolean(INDISUNIQUE);
            String columnName = resultSetOfConstraintTable.getString(COLUMN_NAME).toLowerCase();
            String tableName = resultSetOfConstraintTable.getString(TABLE_NAME).toLowerCase();
            String schemaName = resultSetOfConstraintTable.getString(SCHEMA_NAME).toLowerCase();
            for (ISchema schema : schemas) {
                if (schema.getName().equals(schemaName)) {
                    ITable table = schema.getTable(tableName);
                    IColumn column = table.getColumn(columnName);
                    IIndex index = schema.createIndex(indexName, column);
                    if (isUnique) {
                        index.setType(IIndex.UNIQUE);
                    }
                }
            }

        }
        connection.setAutoCommit(true);
        return model;
    }
}
