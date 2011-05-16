package ru.kirsanov.mdbo.synchronize.synchronizers.mysql;

import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.entity.ISchema;
import ru.kirsanov.mdbo.metamodel.entity.IView;
import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.entity.MysqlModel;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ViewNotFoundException;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLViewSynchronizer implements IEntitySynchronizer {
    private static final String CHECK__OPTION = "CHECK_OPTION";
    private static final String IS_UPDATABLE = "IS_UPDATABLE";
    private static final String COLUMN_TYPE = "COLUMN_TYPE";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String VIEW_DEFINITION = "VIEW_DEFINITION";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private Connection connection;

    public MySQLViewSynchronizer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Model execute(Model model) throws ModelSynchronizerNotFound, SQLException, ViewNotFoundException, ColumnAlreadyExistsException {
        if (!(model instanceof MysqlModel)) throw new ModelSynchronizerNotFound();
        PreparedStatement selectInformationFromSysTable = connection
                .prepareStatement("SELECT * FROM views WHERE table_schema = ?");
        selectInformationFromSysTable.setString(1, model.getName());
        connection.setAutoCommit(false);
        ResultSet resultSetOfConstraintTable = selectInformationFromSysTable.executeQuery();
        while (resultSetOfConstraintTable.next()) {
            String tableName = resultSetOfConstraintTable.getString(TABLE_NAME).toLowerCase();
            String viewDefinition = resultSetOfConstraintTable.getString(VIEW_DEFINITION).toLowerCase();
            String checkOption = resultSetOfConstraintTable.getString(CHECK__OPTION).toLowerCase();
            String isUpdatable = resultSetOfConstraintTable.getString(IS_UPDATABLE).toLowerCase();
            ISchema mainSchema = model.getSchemas().get(0);
            IView view = mainSchema.createView(tableName, viewDefinition);
            view.setCheckOption(checkOption);
            view.setUpdatable(isUpdatable.equals("yes"));
        }
        PreparedStatement columnInformationFromSysTable = connection
                .prepareStatement("SELECT * FROM columns WHERE Table_Schema = ? AND table_name IN (SELECT table_name from views WHERE Table_Schema = ?)");
        columnInformationFromSysTable.setString(1, model.getName());
        columnInformationFromSysTable.setString(2, model.getName());
        ResultSet resultSetOfColumnTable = columnInformationFromSysTable.executeQuery();
        while (resultSetOfColumnTable.next()) {
            String columnName = resultSetOfColumnTable.getString(COLUMN_NAME).toLowerCase();
            String tableName = resultSetOfColumnTable.getString(TABLE_NAME).toLowerCase();
            String columnType = resultSetOfColumnTable.getString(COLUMN_TYPE).toLowerCase();
            String dataTypeName = resultSetOfColumnTable.getString(DATA_TYPE).toLowerCase();
            DataType dataType = MySQLTableSynchronizer.createDataType(columnType, dataTypeName);
            IView view = model.getSchemas().get(0).getView(tableName);
            view.createColumn(columnName, dataType);
        }

        connection.setAutoCommit(true);
        return model;
    }
}
