package ru.kirsanov.mdbo.metamodel.entity;

public class View extends MetaObject implements IView {

    private String definition;
    private String checkOption = "none";
    private boolean updatable;
    private Container parent;

    public View(final String name, final String definition) {
        super(name);
        this.definition = definition;
    }


    @Override
    public String getDefinition() {
        return this.definition;
    }

    @Override
    public String getCheckOption() {
        return this.checkOption;
    }

    @Override
    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    @Override
    public boolean isUpdatable() {
        return this.updatable;
    }

    @Override
    public void setUpdatable(boolean value) {
        this.updatable = value;
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public void setContainer(Container container) {
        this.parent = container;
    }
}
