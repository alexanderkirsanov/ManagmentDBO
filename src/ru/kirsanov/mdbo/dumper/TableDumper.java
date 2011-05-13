package ru.kirsanov.mdbo.dumper;

import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;

import java.util.ArrayList;
import java.util.List;

public class TableDumper implements ITableDumper {
    private String tableName;
    private List<String> columns = new ArrayList<String>();

    public TableDumper(String tableName) {
        this.tableName = tableName;
    }


    @Override
    public void addColumn(String columnName) {
        columns.add(columnName);
    }

    @Override
    public String getSql() throws NoColumnForDumpException {
        if (columns.size() > 0 ){
        StringBuilder selectStringBuilder = new StringBuilder("SELECT ");
        for (String columnName : columns) {
            selectStringBuilder.append(columnName);
            if (columns.lastIndexOf(columnName) + 1 != columns.size()) {
                selectStringBuilder.append(" ,");
            }
        }

        selectStringBuilder.append(" FROM ");
        selectStringBuilder.append(tableName);
        selectStringBuilder.append(";");
        return selectStringBuilder.toString();
    } else  {
            throw new NoColumnForDumpException();
        }
    }
}
