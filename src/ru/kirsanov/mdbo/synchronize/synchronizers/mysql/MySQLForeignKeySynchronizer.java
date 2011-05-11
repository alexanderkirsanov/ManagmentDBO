package ru.kirsanov.mdbo.synchronize.synchronizers.mysql;

import ru.kirsanov.mdbo.metamodel.constraint.ForeignKey;
import ru.kirsanov.mdbo.metamodel.entity.*;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLForeignKeySynchronizer implements IEntitySynchronizer {

    private Connection connection;
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
    private static final String REFERENCED__TABLE__NAME = "REFERENCED_TABLE_NAME";
    private static final String REFERENCED_COLUMN_NAME = "REFERENCED_COLUMN_NAME";

    public MySQLForeignKeySynchronizer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Model execute(Model model) throws ModelSynchronizerNotFound, SQLException, TableNotFound, ColumnNotFoundException {
        if (!(model instanceof MysqlModel)) throw new ModelSynchronizerNotFound();
        PreparedStatement selectInformationFromSysTable = connection
                .prepareStatement("SELECT * FROM key_column_usage WHERE constraint_schema = ? AND  REFERENCED_TABLE_NAME IS NOT NULL");
        selectInformationFromSysTable.setString(1, model.getName());
        connection.setAutoCommit(false);
        ResultSet resultSetOfConstraintTable = selectInformationFromSysTable.executeQuery();
        while (resultSetOfConstraintTable.next()) {
            String tableName = resultSetOfConstraintTable.getString(TABLE_NAME).toLowerCase();
            String columnName = resultSetOfConstraintTable.getString(COLUMN_NAME).toLowerCase();
            String constraintName = resultSetOfConstraintTable.getString(CONSTRAINT_NAME).toLowerCase();
            String referencedTableName = resultSetOfConstraintTable.getString(REFERENCED__TABLE__NAME).toLowerCase();
            String referencedColumnName = resultSetOfConstraintTable.getString(REFERENCED_COLUMN_NAME).toLowerCase();
            ISchema mainSchema = model.getSchemas().get(0);
            ITable sourceTable = mainSchema.getTable(tableName);
            ITable referencedTable = mainSchema.getTable(referencedTableName);
            IColumn sourceColumn = sourceTable.getColumn(columnName);
            IColumn referencedColumn = referencedTable.getColumn(referencedColumnName);
            ForeignKey fk = mainSchema.createForeignKey(constraintName, sourceTable, referencedTable);
            fk.addColumnMapping(sourceColumn, referencedColumn);
        }
        connection.setAutoCommit(true);
        return model;
    }
}
