package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.constraint.ForeignKey;
import ru.kirsanov.mdbo.metamodel.exception.ForeignKeyNotFound;
import ru.kirsanov.mdbo.metamodel.exception.IndexNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.TableNotFound;
import ru.kirsanov.mdbo.metamodel.exception.ViewNotFoundException;

import java.util.List;


public interface ISchema extends Container {
    void addTable(ITable table);

    void removeTable(ITable table);

    List<ITable> getTables();

    ITable getTable(String tableName) throws TableNotFound;

    ForeignKey createForeignKey(String name, ITable sourceTable, ITable targetTable);

    List<ForeignKey> getForeignKeys();

    ForeignKey getForeignKey(String name) throws ForeignKeyNotFound;

    IView createView(String name, String defenition);

    IView getView(String name) throws ViewNotFoundException;

    List<IView> getViews();

    IIndex createIndex(String name, IColumn column);

    IIndex getIndex(String name) throws IndexNotFoundException;

    List<IIndex> getIndexes();

    void rename(String newName);
}
