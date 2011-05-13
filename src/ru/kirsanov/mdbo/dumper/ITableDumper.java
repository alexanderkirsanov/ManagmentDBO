package ru.kirsanov.mdbo.dumper;

import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 13.05.11
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
public interface ITableDumper {
    void addColumn(String id);

    String getSql() throws NoColumnForDumpException;
}
