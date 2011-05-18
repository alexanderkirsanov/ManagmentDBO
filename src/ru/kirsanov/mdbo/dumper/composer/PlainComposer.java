package ru.kirsanov.mdbo.dumper.composer;

import ru.kirsanov.mdbo.dumper.validator.Validator;

import java.util.List;

public class PlainComposer implements IComposer {
    private Character delimiter;
    private StringBuilder stringBuilder;

    public PlainComposer(Character delimiter) {
        this.delimiter = delimiter;
        stringBuilder = new StringBuilder();
    }

    @Override
    public void addHeader(String tableName, List<String> columns) {
        stringBuilder.append("# ").append(tableName).append(":");
        int i = 0;
        for (String column : columns) {
            stringBuilder.append(column);
            i++;
            if (i < columns.size()) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("\n");
    }

    @Override
    public void addBody(String[] line) {
        int i = 0;
        for (String currentLine : line) {
            stringBuilder.append(Validator.escape(currentLine, delimiter));
            i++;
            if (i < line.length) {
                stringBuilder.append(delimiter);
            }
        }
    }

    @Override
    public void addEndLine() {
        stringBuilder.append('\n');
    }

    @Override
    public void addEnd() {
        stringBuilder.append("\n#end");
    }

    @Override
    public String getResults() {
        return stringBuilder.toString();
    }
}
