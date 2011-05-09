package ru.kirsanov.mdbo.synchronize.synchronizers.postgres;

import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.*;
import ru.kirsanov.mdbo.metamodel.exception.ViewNotFound;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostgresViewSynchronizer {
    private static final String TABLE_SCHEMA = "TABLE_SCHEMA";
    private Connection connection;
    private static final String CHECK_OPTION = "CHECK_OPTION";
    private static final String IS_UPDATABLE = "IS_UPDATABLE";
    private static final String VIEW_DEFINITION = "VIEW_DEFINITION";
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String CHARACTER_MAXIMUM_LENGTH = "character_maximum_length";
    private static final String NUMERIC_PRECISION = "NUMERIC_PRECISION";
    private static final String NUMERIC_SCALE = "NUMERIC_SCALE";
    private static final String NULL = "NULL";

    public PostgresViewSynchronizer(Connection connection) {
        this.connection = connection;
    }

    public Model execute(Model model) throws SQLException, ModelSynchronizerNotFound, ViewNotFound {
        if (!(model instanceof PostgresModel)) throw new ModelSynchronizerNotFound();
        PreparedStatement selectInformationFromSysTable = connection
                .prepareStatement("SELECT * FROM information_schema.views WHERE table_schema not in('pg_catalog', 'information_schema')");
        connection.setAutoCommit(false);
        List<ISchema> schemas = model.getSchemas();
        ResultSet resultSetOfConstraintTable = selectInformationFromSysTable.executeQuery();
        while (resultSetOfConstraintTable.next()) {
            String tableSchema = resultSetOfConstraintTable.getString(TABLE_SCHEMA).toLowerCase();
            String tableName = resultSetOfConstraintTable.getString(TABLE_NAME).toLowerCase();
            String viewDefinition = resultSetOfConstraintTable.getString(VIEW_DEFINITION).toLowerCase();
            String checkOption = resultSetOfConstraintTable.getString(CHECK_OPTION).toLowerCase();
            String isUpdatable = resultSetOfConstraintTable.getString(IS_UPDATABLE).toLowerCase();
            for (ISchema schema : schemas) {
                if (schema.getName().equals(tableSchema)) {
                    IView view = schema.createView(tableName, viewDefinition);
                    view.setCheckOption(checkOption);
                    view.setUpdatable(isUpdatable.equals("yes"));
                    break;
                }
            }
        }
        PreparedStatement selectColumnFromSysTable = connection
                .prepareStatement(
                        "SELECT * FROM information_schema.columns \n" +
                                "WHERE Table_Schema <> 'information_schema'\n" +
                                "AND table_Schema<>'pg_catalog'\n" +
                                "AND table_name IN \n" +
                                "(SELECT table_name \n" +
                                "FROM information_schema.views \n" +
                                "WHERE Table_Schema <> 'information_schema'\n" +
                                "AND table_Schema<>'pg_catalog')");

        ResultSet resultSetOfColumn = selectColumnFromSysTable.executeQuery();
        while (resultSetOfColumn.next()) {
            String schemaName = resultSetOfColumn.getString(TABLE_SCHEMA);
            for (ISchema schema : schemas) {
                if (schema.getName().equals(schemaName)) {
                    String viewName = resultSetOfColumn.getString(TABLE_NAME);
                    IView view = schema.getView(viewName);
                    String characterMaximumLength = PostgresTableSynchronizer.lower(resultSetOfColumn.getString(CHARACTER_MAXIMUM_LENGTH));
                    String numericPrecision = PostgresTableSynchronizer.lower(resultSetOfColumn.getString(NUMERIC_PRECISION));
                    String numericScale = PostgresTableSynchronizer.lower(resultSetOfColumn.getString(NUMERIC_SCALE));
                    String dataTypeName = PostgresTableSynchronizer.lower(resultSetOfColumn.getString(DATA_TYPE));
                    String columnName = PostgresTableSynchronizer.lower(resultSetOfColumn.getString(COLUMN_NAME));

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
                    Column column = new Column(view, columnName, dataType);
                    view.addColumn(column);
                }
            }
        }
        connection.setAutoCommit(true);
        return model;
    }
}
