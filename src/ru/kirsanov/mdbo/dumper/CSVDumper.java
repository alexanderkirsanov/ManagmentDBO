package ru.kirsanov.mdbo.dumper;

import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.query.ITableDumpQuery;
import ru.kirsanov.mdbo.dumper.writer.IWriter;

import java.sql.*;

public class CSVDumper {
    private Connection connection;
    private ITableDumpQuery query;
    private Character delimiter;
    private IWriter writer;

    public CSVDumper(Connection connection, IWriter writer) {
        this.connection = connection;
        this.writer = writer;
    }

    public void setDelimiter(Character delimiter) {
        writer.setDelimiter(delimiter);
    }

    public void execute(ITableDumpQuery tableDumpQuery) throws SQLException, NoColumnForDumpException {
        PreparedStatement selectDataFromTable = connection
                .prepareStatement(tableDumpQuery.getSql());
        connection.setAutoCommit(false);
        ResultSet resultSetOfData = selectDataFromTable.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSetOfData.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        String[] line = new String[columnCount];
        while (resultSetOfData.next()) {
            for (int i = 1; i <= columnCount; i++) {

                line[i-1] = resultSetOfData.getString(i);

            }
            writer.write(line);
        }
        connection.setAutoCommit(true);
    }


}
