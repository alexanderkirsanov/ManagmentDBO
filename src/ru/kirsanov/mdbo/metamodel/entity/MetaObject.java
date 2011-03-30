package ru.kirsanov.mdbo.metamodel.entity;

public class MetaObject {
    private final String name;

    public MetaObject(final String name){
        this.name = name;
    }

    public String getName(){
        return  this.name;
    }
}
