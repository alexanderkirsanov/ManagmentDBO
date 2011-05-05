package ru.kirsanov.mdbo.synchronize.synchronizers.postgres;

import ru.kirsanov.mdbo.metamodel.entity.ISchema;
import ru.kirsanov.mdbo.metamodel.entity.IView;
import ru.kirsanov.mdbo.metamodel.entity.Model;
import ru.kirsanov.mdbo.metamodel.entity.PostgresModel;
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
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String VIEW_DEFINITION = "VIEW_DEFINITION";

    public PostgresViewSynchronizer(Connection connection) {
        this.connection = connection;
    }

    public Model execute(Model model) throws SQLException, ModelSynchronizerNotFound {
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
        connection.setAutoCommit(true);
        return model;
    }
}
