package ru.kirsanov.mdbo.metamodel.entity;

public class MetaObject {
    private String name;

    public MetaObject(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void rename(String newName){
        this.name = newName;
    }
}
