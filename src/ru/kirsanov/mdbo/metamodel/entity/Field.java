package ru.kirsanov.mdbo.metamodel.entity;

import ru.kirsanov.mdbo.metamodel.datatype.Datatype;
import ru.kirsanov.mdbo.metamodel.exception.ElementNotFoundException;
import ru.kirsanov.mdbo.metamodel.exception.IncorrectVariableTypeException;

import java.util.ArrayList;

public class Field extends MetaObject implements Container {

    private Container container;
    private Datatype dataType;
    private ArrayList<Object> variableList;

    public Field(final String name,final Datatype dataType) {
        super(name);
        this.dataType = dataType;
        variableList = new ArrayList<Object>();
    }

    public Container getParent() {
        return this.container;
    }

    public void setContainer(final Container container) {
        this.container = container;
    }

    public void setDataType(final Datatype dataType) {
        this.dataType = dataType;
    }

    public Datatype getDataType() {
        return this.dataType;
    }

    public void addVariable(Object variable) throws IncorrectVariableTypeException {
        dataType.checkCorrect(variable);
        variableList.add(variable);
    }

    public void removeVariable(int id) throws ElementNotFoundException {
        if (id < variableList.size()) {
            variableList.remove(id);
        } else {
            throw new ElementNotFoundException();
        }
    }

    public Object getVariable(int id) throws ElementNotFoundException {
        if (id < variableList.size()) {
            return variableList.get(id);
        } else {
            throw new ElementNotFoundException();
        }
    }

    public void changeVariable(Object variable, int id) throws ElementNotFoundException, IncorrectVariableTypeException {
        if (id < variableList.size()) {
            dataType.checkCorrect(variable);
            variableList.set(id, variable);
        } else {
            throw new ElementNotFoundException();
        }
    }

}
