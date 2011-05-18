package ru.kirsanov.mdbo.synchronize.synchronizers.postgres;

import ru.kirsanov.mdbo.metamodel.constraint.ForeignKey;
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

public class PostgresForeignKeySynchronizer implements IEntitySynchronizer {
    private static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
    private static final String CONSTRAINT__SCHEMA = "CONSTRAINT_SCHEMA";
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String FOREIGN__TABLE__NAME = "FOREIGN_TABLE_NAME";
    private static final String FOREIGN__COLUMN__NAME = "FOREIGN_COLUMN_NAME";
    private Connection connection;

    public PostgresForeignKeySynchronizer(Connection connection) {
        this.connection = connection;
    }

    public Model execute(Model model) throws ModelSynchronizerNotFound, SQLException, TableNotFound, ColumnNotFoundException {
        if (!(model instanceof PostgresModel)) throw new ModelSynchronizerNotFound();
        PreparedStatement selectInformationFromSysTable = connection
                .prepareStatement("SELECT\n" +
                        "    tc.constraint_name, tc.constraint_schema, tc.table_name, kcu.column_name, \n" +
                        "    ccu.table_name AS foreign_table_name,\n" +
                        "    ccu.column_name AS foreign_column_name \n" +
                        "FROM \n" +
                        "    information_schema.table_constraints AS tc \n" +
                        "    JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name\n" +
                        "    JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name\n" +
                        "WHERE constraint_type = 'FOREIGN KEY';");

        List<ISchema> schemas = model.getSchemas();
        ResultSet resultSetOfSchema = selectInformationFromSysTable.executeQuery();
        while (resultSetOfSchema.next()) {
            String constraintName = resultSetOfSchema.getString(CONSTRAINT_NAME).toLowerCase();
            String schemaName = resultSetOfSchema.getString(CONSTRAINT__SCHEMA).toLowerCase();
            String sourceTableName = resultSetOfSchema.getString(TABLE_NAME).toLowerCase();
            String sourceColumnName = resultSetOfSchema.getString(COLUMN_NAME).toLowerCase();
            String targetTableName = resultSetOfSchema.getString(FOREIGN__TABLE__NAME).toLowerCase();
            String targetColumnName = resultSetOfSchema.getString(FOREIGN__COLUMN__NAME).toLowerCase();
            for (ISchema schema : schemas) {
                if (schema.getName().equals(schemaName)) {
                    ITable sourceTable = schema.getTable(sourceTableName);
                    ITable targetTable = schema.getTable(targetTableName);
                    ForeignKey foreignKey = schema.createForeignKey(constraintName, sourceTable, targetTable);
                    foreignKey.addColumnMapping(sourceTable.getColumn(sourceColumnName), targetTable.getColumn(targetColumnName));
                    break;
                }
            }
        }
        connection.setAutoCommit(false);
        return model;
    }
}
