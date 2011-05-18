package ru.kirsanov.mdbo.dumper.composer;

import java.util.List;

public interface IComposer {
    public void addHeader(String tableName, List<String> columns);

    public void addBody(String[] line);

    public void addEndLine();

    public void addEnd();

    public String getResults();
}
