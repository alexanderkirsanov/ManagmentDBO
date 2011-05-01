package ru.kirsanov.mdbo.synchronize.synchronizers.mysql;

import ru.kirsanov.mdbo.metamodel.entity.*;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLIndexSynchronizer implements IEntitySynchronizer {

    private static final String INDEX_NAME = "INDEX_NAME";
    private static final String NON_UNIQUE = "NON_UNIQUE";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String TABLE_NAME = "TABLE_NAME";
    private Connection connection;

    public MySQLIndexSynchronizer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Model execute(Model model) throws Throwable {
        if (!(model instanceof MysqlModel)) throw new ModelSynchronizerNotFound();
        PreparedStatement selectInformationFromSysTable = connection
                .prepareStatement("SELECT * FROM statistics WHERE index_schema = ? AND INDEX_NAME <> 'PRIMARY'");
        selectInformationFromSysTable.setString(1, model.getName());
        connection.setAutoCommit(false);
        ResultSet resultSetOfConstraintTable = selectInformationFromSysTable.executeQuery();
        while (resultSetOfConstraintTable.next()) {
            String indexName = resultSetOfConstraintTable.getString(INDEX_NAME).toLowerCase();
            String nonUnique = resultSetOfConstraintTable.getString(NON_UNIQUE).toLowerCase();
            String columnName = resultSetOfConstraintTable.getString(COLUMN_NAME).toLowerCase();
            String tableName = resultSetOfConstraintTable.getString(TABLE_NAME).toLowerCase();
            ISchema mainSchema = model.getSchemas().get(0);
            ITable table = mainSchema.getTable(tableName);
            IColumn column = table.getColumn(columnName);
            IIndex index = mainSchema.createIndex(indexName, column);
            if (nonUnique.equals("0")) {
                index.setType(IIndex.UNIQUE);
            }
        }
        connection.setAutoCommit(true);
        return model;
    }
}
