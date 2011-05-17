package ru.kirsanov.mdbo.dumper.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public abstract class SQLWriter implements IWriter {
    private PrintWriter printWriter;
    private String table;
    private List<String> columns;

    public SQLWriter(PrintWriter printWriter, String tableName, List<String> columns) throws FileNotFoundException, UnsupportedEncodingException {
        this.printWriter = printWriter;
        this.table = tableName;
        this.columns = columns;
    }

    public SQLWriter(String fileName, String encoding, String tableName, List<String> columns) throws FileNotFoundException, UnsupportedEncodingException {
        this(new PrintWriter(new File(fileName), encoding), tableName, columns);
    }

    protected String createHeader() {
        StringBuilder headerString = new StringBuilder("INSERT INTO ").append(table).append("(");
        int i = 0;
        for (String column : columns) {
            headerString.append(column);
            i++;
            if (i < columns.size()) {
                headerString.append(", ");
            }
        }
        headerString.append(") ");
        return headerString.toString();
    }

    protected void writeLine(String line) {
        printWriter.write(line);
    }

    protected StringBuilder prepareData(String[] line) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (String currentLine : line) {
            stringBuilder.append("'");
            stringBuilder.append(currentLine.replace("\"", "\\\""));
            stringBuilder.append("'");
            i++;
            if (i < line.length) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder;
    }

    @Override
    public void close() {
        printWriter.flush();
        printWriter.close();
    }
}
