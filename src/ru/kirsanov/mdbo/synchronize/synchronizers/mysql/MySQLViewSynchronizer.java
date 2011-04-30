package ru.kirsanov.mdbo.synchronize.synchronizers.mysql;

import ru.kirsanov.mdbo.metamodel.entity.ISchema;
import ru.kirsanov.mdbo.metamodel.entity.IView;
import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.entity.MysqlModel;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.IEntitySynchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLViewSynchronizer implements IEntitySynchronizer {
    private static final String CHECK__OPTION = "CHECK_OPTION";
    private static final String IS_UPDATABLE = "IS_UPDATABLE";

    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String VIEW_DEFINITION = "VIEW_DEFINITION";
    private Connection connection;

    public MySQLViewSynchronizer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Model execute(Model model) throws Throwable {
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
        connection.setAutoCommit(true);
        return model;
    }
}
