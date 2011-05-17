package ru.kirsanov.mdbo.dumper.writer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class SingleSQLWriter extends SQLWriter {
    private StringBuilder stringBuilder = new StringBuilder();
    private boolean isNotEnd = true;

    public SingleSQLWriter(PrintWriter printWriter, String tableName, List<String> columns) throws FileNotFoundException, UnsupportedEncodingException {
        super(printWriter, tableName, columns);
        printWriter.write(this.createHeader());
        printWriter.write(" VALUES");
    }

    public SingleSQLWriter(String fileName, String encoding, String tableName, List<String> columns) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, encoding, tableName, columns);
    }

    @Override
    public void write(String[] line) {
        stringBuilder.append("(");
        stringBuilder.append(prepareData(line));
        stringBuilder.append(")");
        if (isNotEnd) {
            stringBuilder.append(",");
        } else {
            stringBuilder.append(";");
            isNotEnd = true;
        }
        writeLine(stringBuilder.toString());
        stringBuilder.delete(0, Integer.MAX_VALUE);
    }

    public void setEnd() {
        this.isNotEnd = false;
    }

}
