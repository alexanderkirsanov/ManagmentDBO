package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.datatype.Datatype;

import java.util.LinkedList;
import java.util.List;

public class Table extends MetaObject implements Container {

    private List<Field> fields;
    private Container container;

    public Table(final String name) {
        super(name);
        fields = new LinkedList<Field>();
    }

    public Field createField(String name, Datatype datatype) {
        Field field = new Field(name, datatype);
        this.fields.add(field);
        return field;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public Container getParent() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
