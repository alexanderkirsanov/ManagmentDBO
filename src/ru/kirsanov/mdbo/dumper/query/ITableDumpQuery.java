package ru.kirsanov.mdbo.dumper.query;

import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.parser.Dumpable;

import java.util.List;

public interface ITableDumpQuery extends Dumpable {
    void addColumn(String id);

    String getSql() throws NoColumnForDumpException;

    String getEntityName();

    List<String> getColumnsList();
}
