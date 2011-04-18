package ru.kirsanov.mdbo.metamodel.entity;

import java.util.ArrayList;
import java.util.List;

public class View extends MetaObject {
    private List<Column> columns;

    public View(final String name) {
        super(name);
        columns = new ArrayList<Column>();
    }

    public void addColumn(Column column){
       this.columns.add(column);

    }

}
