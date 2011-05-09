package ru.kirsanov.mdbo.synchronize.synchronizers.postgres;

import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.*;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;


public class PostgresTableSynchronizer implements IEntitySynchronizer {
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String IS_NULLABLE = "IS_NULLABLE";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String COLUMN_DEFAULT = "COLUMN_DEFAULT";
    private static final String CHARACTER_MAXIMUM_LENGTH = "character_maximum_length";
    private static final String NUMERIC_PRECISION = "NUMERIC_PRECISION";
    private static final String NUMERIC_SCALE = "NUMERIC_SCALE";
    private static final String NULL = "NULL";
    private Connection connection;
    private static final String TABLE_SCHEMA = "TABLE_SCHEMA";
    private Map<String, ISchema> schemas = new HashMap<String, ISchema>();

    public PostgresTableSynchronizer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Model execute(Model model) throws Throwable {
        if (!(model instanceof PostgresModel)) throw new ModelSynchronizerNotFound();
        PreparedStatement selectInformationFromSysTable = connection
                .prepareStatement(
                        "SELECT * FROM information_schema.columns \n" +
                                "WHERE Table_Schema <> 'information_schema'\n" +
                                "AND table_Schema<>'pg_catalog'\n" +
                                "AND table_name NOT IN \n" +
                                "(SELECT table_name \n" +
                                "FROM information_schema.views \n" +
                                "WHERE Table_Schema <> 'information_schema'\n" +
                                "AND table_Schema<>'pg_catalog')");

        connection.setAutoCommit(false);
        Map<String, ISchema> schemas = new HashMap<String, ISchema>();
        ResultSet resultSetOfSchema = selectInformationFromSysTable.executeQuery();
        while (resultSetOfSchema.next()) {
            String schemaName = resultSetOfSchema.getString(TABLE_SCHEMA);
            ISchema schema = null;
            if (schemas.containsKey(schemaName)) {
                schema = schemas.get(schemaName);
            } else {
                schema = model.createSchema(schemaName);

                schemas.put(schemaName, schema);
            }
            String tableName = resultSetOfSchema.getString(TABLE_NAME);
            ITable table = null;
            try {
                table = schema.getTable(tableName);
            } catch (TableNotFound e) {
                table = new Table(tableName);
                schema.addTable(table);
            }
            String characterMaximumLength = lower(resultSetOfSchema.getString(CHARACTER_MAXIMUM_LENGTH));
            String numericPrecision = lower(resultSetOfSchema.getString(NUMERIC_PRECISION));
            String numericScale = lower(resultSetOfSchema.getString(NUMERIC_SCALE));
            String dataTypeName = lower(resultSetOfSchema.getString(DATA_TYPE));
            String columnName = lower(resultSetOfSchema.getString(COLUMN_NAME));

            DataType dataType = null;
            if (characterMaximumLength.equals(NULL)) {
                if (numericPrecision.equals(NULL)) {
                    dataType = new SimpleDatatype(dataTypeName);
                } else {
                    if (numericScale.equals(NULL)) {
                        dataType = new SimpleDatatype(dataTypeName, Integer.parseInt(numericPrecision));
                    } else {
                        dataType = new SimpleDatatype(dataTypeName, Integer.parseInt(numericPrecision), Integer.parseInt(numericScale));
                    }
                }
            } else {
                dataType = new SimpleDatatype(dataTypeName, Integer.parseInt(characterMaximumLength));
            }
            IColumn column = table.createColumn(columnName, dataType);
            column.setNullable(lower(resultSetOfSchema.getString(IS_NULLABLE)).equals("yes"));
            String defaultValue = lower(resultSetOfSchema.getString(COLUMN_DEFAULT));
            if (!defaultValue.equals(NULL)) {
                column.setDefaultValue(defaultValue);
            }
        }
        connection.setAutoCommit(true);
        return model;
    }

    public static String lower(String string) {
        if (string != null) {
            return string.toLowerCase();
        } else {
            return NULL;
        }
    }
}