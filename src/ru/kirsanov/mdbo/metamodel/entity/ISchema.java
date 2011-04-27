package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.synchronize.exception.TableNotFound;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 21.04.11
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public interface ISchema extends Container {
    void addTable(ITable table);

    void removeTable(ITable table);

    List<ITable> getTables();

    ITable getTable(String str) throws TableNotFound;
}
