package ru.kirsanov.mdbo.dumper.query;

import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;

import java.util.ArrayList;
import java.util.List;

public class TableDumpQuery implements ITableDumpQuery {
    private String entityName;
    private List<String> columns = new ArrayList<String>();

    public TableDumpQuery(String entityName) {
        this.entityName = entityName;
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
        selectStringBuilder.append(entityName);
        selectStringBuilder.append(";");
        return selectStringBuilder.toString();
    } else  {
            throw new NoColumnForDumpException();
        }
    }

    @Override
    public String getEntityName() {
        return this.entityName;
    }
}
