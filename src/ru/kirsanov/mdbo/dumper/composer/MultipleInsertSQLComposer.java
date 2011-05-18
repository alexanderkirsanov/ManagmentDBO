package ru.kirsanov.mdbo.dumper.composer;

import ru.kirsanov.mdbo.dumper.validator.Validator;

import java.util.List;

public class MultipleInsertSQLComposer implements IComposer {
    private String tableName;
    private List<String> columns;
    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void addHeader(String tableName, List<String> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    @Override
    public void addBody(String[] line) {
        stringBuilder.append("INSERT INTO ").append(tableName).append("(");
        int i = 0;
        for (String column : columns) {
            stringBuilder.append(column);
            i++;
            if (i < columns.size()) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(") VALUES(");
        stringBuilder.append(Validator.prepareData(line));
        stringBuilder.append(")");
    }

    @Override
    public void addEnd() {
        stringBuilder.append(";");
    }

    @Override
    public void addEndLine() {
        stringBuilder.append('\n');
    }

    @Override
    public String getResults() {
        return stringBuilder.toString();
    }
}
