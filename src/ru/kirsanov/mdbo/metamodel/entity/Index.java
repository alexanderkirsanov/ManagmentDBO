package ru.kirsanov.mdbo.metamodel.entity;

public class Index extends MetaObject {
    private Column column;
    private int count;
    public final int UNIQUE = 1;
    public final int FULL_TEXT_SEARCH = 2;
    private int type = 0;

    public Index(final String name, Column column, int count ) {
        super(name);
        this.column = column;
        this.count = count;
    }

    public Index(final String name, Column column, int count, int type ) {
        super(name);
        this.column = column;
        this.count = count;
        this.type = type;
    }
}
