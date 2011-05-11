package ru.kirsanov.mdbo.synchronize.synchronizers.mysql;

import ru.kirsanov.mdbo.metamodel.entity.IColumn;
import ru.kirsanov.mdbo.metamodel.entity.ITable;
import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.entity.MysqlModel;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLPrimaryKeySynchronizer implements IEntitySynchronizer {
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";

    private Connection connection;

    public MySQLPrimaryKeySynchronizer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Model execute(Model model) throws ModelSynchronizerNotFound, SQLException, TableNotFound, ColumnNotFoundException {
        if (!(model instanceof MysqlModel)) throw new ModelSynchronizerNotFound();
        PreparedStatement selectInformationFromSysTable = connection
                .prepareStatement("SELECT * FROM key_column_usage WHERE constraint_schema = ?");
        selectInformationFromSysTable.setString(1, model.getName());
        connection.setAutoCommit(false);
        ResultSet resultSetOfConstraintTable = selectInformationFromSysTable.executeQuery();
        while (resultSetOfConstraintTable.next()) {
            String tableName = resultSetOfConstraintTable.getString(TABLE_NAME).toLowerCase();
            String columnName = resultSetOfConstraintTable.getString(COLUMN_NAME).toLowerCase();
            String constraintName = resultSetOfConstraintTable.getString(CONSTRAINT_NAME).toLowerCase();
            ITable table = model.getSchemas().get(0).getTable(tableName);
            IColumn column = table.getColumn(columnName);
            if (constraintName.toLowerCase().equals("primary")) {
                table.createPrimaryKey(column);
            }
        }
        connection.setAutoCommit(true);
        return model;
    }
}
