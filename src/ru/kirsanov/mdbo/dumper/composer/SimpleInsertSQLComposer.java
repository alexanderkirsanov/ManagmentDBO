package ru.kirsanov.mdbo.dumper.composer;

import ru.kirsanov.mdbo.dumper.validator.Validator;

import java.util.List;

public class SimpleInsertSQLComposer implements IComposer {
    private StringBuilder stringBuilder;

    public SimpleInsertSQLComposer() {
        stringBuilder = new StringBuilder();
    }

    @Override
    public void addHeader(String tableName, List<String> columns) {
       stringBuilder.append("INSERT INTO ").append(tableName).append("(");
        int i = 0;
        for (String column : columns) {
            stringBuilder.append(column);
            i++;
            if (i < columns.size()) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(") VALUES");
    }

    @Override
    public void addBody(String[] line) {
        stringBuilder.append("(");
        stringBuilder.append(Validator.prepareData(line));
        stringBuilder.append(")");

    }

    @Override
    public void addEndLine() {
        stringBuilder.append(",");
    }

    @Override
    public void addEnd() {
        stringBuilder.append(";");
    }

    @Override
    public String getResults() {
        return stringBuilder.toString();
    }
}
