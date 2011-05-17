package ru.kirsanov.mdbo.dumper.writer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class MultipleSQLWriter extends SQLWriter {
    private StringBuilder stringBuilder = new StringBuilder();

    public MultipleSQLWriter(PrintWriter printWriter, String tableName, List<String> columns) throws FileNotFoundException, UnsupportedEncodingException {
        super(printWriter, tableName, columns);
    }

    public MultipleSQLWriter(String fileName, String encoding, String tableName, List<String> columns) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, encoding, tableName, columns);
    }

    @Override
    public void write(String[] line) {
        stringBuilder.append(createHeader());
        stringBuilder.append(" VALUES(");
        stringBuilder.append(prepareData(line));
        stringBuilder.append(");");
        writeLine(stringBuilder.toString());
        stringBuilder.delete(0, Integer.MAX_VALUE);
    }
}
