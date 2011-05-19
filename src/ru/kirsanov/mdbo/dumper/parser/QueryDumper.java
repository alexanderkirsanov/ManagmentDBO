package ru.kirsanov.mdbo.dumper.parser;

import ru.kirsanov.mdbo.dumper.composer.IComposer;
import ru.kirsanov.mdbo.dumper.exception.IncorrectDumper;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.query.ITableDumpQuery;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;

public class QueryDumper implements IDumper {
    private Connection connection;
    private String path;
    private String encoding;
    private IComposer composer;

    public QueryDumper(Connection connection, String path, String encoding, IComposer composer) {
        this.connection = connection;
        this.path = path;
        this.encoding = encoding;
        this.composer = composer;
    }

    public void execute(Dumpable dumpQuery) throws SQLException, NoColumnForDumpException, FileNotFoundException, UnsupportedEncodingException, IncorrectDumper {
        if (dumpQuery instanceof ITableDumpQuery) {
            ITableDumpQuery tableDumpQuery = (ITableDumpQuery) dumpQuery;
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
        } else {
            throw new IncorrectDumper();
        }
    }
}
