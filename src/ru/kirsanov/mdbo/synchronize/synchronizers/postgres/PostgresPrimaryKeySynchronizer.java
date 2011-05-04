package ru.kirsanov.mdbo.synchronize.synchronizers.postgres;

import ru.kirsanov.mdbo.metamodel.entity.ISchema;
import ru.kirsanov.mdbo.metamodel.entity.ITable;
import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.entity.PostgresModel;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostgresPrimaryKeySynchronizer implements IEntitySynchronizer {
    private Connection connection;

    public PostgresPrimaryKeySynchronizer(Connection connection) {
        this.connection = connection;
    }

    public Model execute(Model model) throws ModelSynchronizerNotFound, SQLException, TableNotFound, ColumnNotFoundException {
        if (!(model instanceof PostgresModel)) throw new ModelSynchronizerNotFound();
        PreparedStatement selectInformationFromSysTable = connection
                .prepareStatement("SELECT\n" +
                        " tc.constraint_schema, tc.table_name, kcu.column_name\n" +
                        "FROM\n" +
                        "    information_schema.table_constraints AS tc\n" +
                        "    JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name\n" +
                        "\n" +
                        "WHERE constraint_type = 'PRIMARY KEY';");

        List<ISchema> schemas = model.getSchemas();
        ResultSet resultSetOfSchema = selectInformationFromSysTable.executeQuery();
        while (resultSetOfSchema.next()) {
            String schemaName = resultSetOfSchema.getString("CONSTRAINT_SCHEMA").toLowerCase();
            String tableName = resultSetOfSchema.getString("TABLE_NAME").toLowerCase();
            String columnName = resultSetOfSchema.getString("COLUMN_NAME").toLowerCase();
            for (ISchema schema : schemas) {
                if (schema.getName().equals(schemaName)) {
                    ITable table = schema.getTable(tableName);
                    table.createPrimaryKey(table.getColumn(columnName));
                    break;
                }
            }
        }
        connection.setAutoCommit(false);
        return model;
    }
}
