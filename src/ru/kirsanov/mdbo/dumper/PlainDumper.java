package ru.kirsanov.mdbo.dumper;

import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.query.ITableDumpQuery;
import ru.kirsanov.mdbo.dumper.writer.Encoding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;

public class PlainDumper {
    private Connection connection;
    private String encoding = Encoding.UTF8;
    private String path = "";

    public PlainDumper(Connection connection) {
        this.connection = connection;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setPath(String path) throws IOException {
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                if (file.canWrite()) {
                    this.path = path;
                } else {
                    throw new IOException("File can not be write");
                }
            } else {
                throw new IOException("It is not folder");
            }
        } catch (NullPointerException e) {
            throw new IOException("Path not found");
        }
    }

    public void execute(ITableDumpQuery tableDumpQuery) throws SQLException, NoColumnForDumpException, FileNotFoundException, UnsupportedEncodingException {
//        IPlainWriter writer = new PlainWriter(path + tableDumpQuery.getEntityName() + ".txt", encoding);
//        PreparedStatement selectDataFromTable = connection
//                .prepareStatement(tableDumpQuery.getSql());
//        connection.setAutoCommit(false);
//        ResultSet resultSetOfData = selectDataFromTable.executeQuery();
//        ResultSetMetaData resultSetMetaData = resultSetOfData.getMetaData();
//        int columnCount = resultSetMetaData.getColumnCount();
//        String[] inf = new String[columnCount];
//        for (int i = 1; i <= columnCount; i++) {
//            inf[i - 1] = resultSetMetaData.getColumnName(i);
//        }
//        writer.write(inf);
//        String[] line = new String[columnCount];
//        while (resultSetOfData.next()) {
//            for (int i = 1; i <= columnCount; i++) {
//                line[i - 1] = resultSetOfData.getString(i);
//            }
//            writer.write(line);
//        }
//        writer.close();
//        connection.setAutoCommit(true);
    }
}
