package ru.kirsanov.mdbo.synchronize.synchronizers.mysql;

import ru.kirsanov.mdbo.metamodel.datatype.DataType;
import ru.kirsanov.mdbo.metamodel.datatype.SimpleDatatype;
import ru.kirsanov.mdbo.metamodel.entity.*;
import ru.kirsanov.mdbo.metamodel.exception.ColumnAlreadyExistsException;
import ru.kirsanov.mdbo.metamodel.exception.ColumnNotFoundException;
import ru.kirsanov.mdbo.synchronize.exception.ConnectionNotSet;
import ru.kirsanov.mdbo.synchronize.exception.IncorrectDataBaseType;
import ru.kirsanov.mdbo.synchronize.exception.ModelSynchronizerNotFound;
import ru.kirsanov.mdbo.synchronize.synchronizers.IModelSynchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MySQLModelSynchronizer implements IModelSynchronizer {
    private static final String TABLE__NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String IS_NULLABLE = "IS_NULLABLE";
    private static final String COLUMN_TYPE = "COLUMN_TYPE";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String COLUMN_DEFAULT = "COLUMN_DEFAULT";
    private static final String COLUMN_KEY = "COLUMN_KEY";
    private Connection connection;

    public MySQLModelSynchronizer(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Model execute(final Model model) throws IncorrectDataBaseType, ConnectionNotSet, SQLException, ModelSynchronizerNotFound, ColumnAlreadyExistsException, ColumnNotFoundException {
        if (!(model instanceof MysqlModel)) throw new ModelSynchronizerNotFound();
        PreparedStatement selectInformationFromSysTable = connection
                .prepareStatement("SELECT * FROM columns WHERE Table_Schema = ?");
        selectInformationFromSysTable.setString(1, model.getName());
        ISchema mySQLSchema = model.createSchema(model.getName());
        connection.setAutoCommit(false);
        ResultSet resultSetOfTable = selectInformationFromSysTable.executeQuery();
        Map<String, ITable> tables = new HashMap<String, ITable>();
        while (resultSetOfTable.next()) {
            String tableName = resultSetOfTable.getString(TABLE__NAME);
            ITable table = null;
            if (tables.containsKey(tableName)) {
                table = tables.get(tableName);
            } else {
                table = new Table(tableName);
                tables.put(tableName, table);
            }
            String columnName = resultSetOfTable.getString(COLUMN_NAME).toLowerCase();
            String isNullable = resultSetOfTable.getString(IS_NULLABLE).toLowerCase();
            String columnType = resultSetOfTable.getString(COLUMN_TYPE).toLowerCase();
            String dataTypeName = resultSetOfTable.getString(DATA_TYPE).toLowerCase();
            String columnDefault = resultSetOfTable.getString(COLUMN_DEFAULT);
            String isPrimari = resultSetOfTable.getString(COLUMN_KEY);
            DataType dataType = createDataType(columnType, dataTypeName);
            IColumn column = table.createColumn(columnName, dataType);
            if (isNullable.equals("no")) {
                column.setNullable(false);
            } else {
                column.setNullable(true);
            }
            if (columnDefault != null) {
                column.setDefaultValue(columnDefault);
            }
        }
        Set<? extends Map.Entry<String, ? extends ITable>> tablesSet = tables.entrySet();
        Iterator<? extends Map.Entry<String, ? extends ITable>> iterator = tablesSet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ? extends ITable> record = iterator.next();
            mySQLSchema.addTable(record.getValue());
        }
        connection.setAutoCommit(true);
        return model;
    }

    public DataType createDataType(String columnType, String dataTypeName) {
        DataType dataType;
        StringBuffer sb = new StringBuffer(columnType);
        if (sb.indexOf("(") != -1) {
            int precisionEnd = (sb.indexOf(",") != -1) ? (sb.indexOf(",") ) : (sb.indexOf(")"));
            int precision = Integer.valueOf(sb.substring(sb.indexOf("(") +1, precisionEnd));
            if (sb.indexOf(",") != -1) {
                int scale = Integer.valueOf(sb.substring(sb.indexOf(",") + 1, sb.indexOf(")") ));
                dataType = new SimpleDatatype(dataTypeName, precision, scale);
            } else {
                dataType = new SimpleDatatype(dataTypeName, precision);
            }
        } else {
            dataType = new SimpleDatatype(dataTypeName);
        }
        return dataType;
    }


    public Model synchronizePrimaryKey(Model model) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}