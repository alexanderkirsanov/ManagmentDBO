package ru.kirsanov.mdbo.dumper;

import ru.kirsanov.mdbo.dumper.composer.IComposer;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.query.ITableDumpQuery;
import ru.kirsanov.mdbo.dumper.writer.Encoding;

import java.io.*;
import java.sql.*;

public class Dumper {
    private Connection connection;
    private String encoding = Encoding.UTF8;
    private String path = "";
    private IComposer composer;

    public Dumper(Connection connection, IComposer composer) {
        this.connection = connection;
        this.composer = composer;
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
        PrintWriter writer = new PrintWriter(path + tableDumpQuery.getEntityName() + ".txt", encoding);
        PreparedStatement selectDataFromTable = connection
                .prepareStatement(tableDumpQuery.getSql());
        connection.setAutoCommit(false);
        ResultSet resultSetOfData = selectDataFromTable.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSetOfData.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        composer.addHeader(tableDumpQuery.getEntityName(), tableDumpQuery.getColumnsList());
        String[] line = new String[columnCount];
        int j = 0;
        while (resultSetOfData.next()) {
            j++;
            for (int i = 1; i <= columnCount; i++) {
                line[i - 1] = resultSetOfData.getString(i);
            }
            composer.addBody(line);
            if (resultSetOfData.isLast()) {
                composer.addEnd();
            } else {
                composer.addEndLine();
            }
        }
        writer.write(composer.getResults());
        writer.close();
        connection.setAutoCommit(true);
    }
}
