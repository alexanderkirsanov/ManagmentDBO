package ru.kirsanov.mdbo.dumper.query;

import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 13.05.11
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
public interface ITableDumpQuery {
    void addColumn(String id);

    String getSql() throws NoColumnForDumpException;

    String getEntityName();
}
