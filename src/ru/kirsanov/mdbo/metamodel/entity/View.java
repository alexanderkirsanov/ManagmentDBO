package ru.kirsanov.mdbo.metamodel.entity;

public class View extends ColumnContainer implements IView {

    private String definition;
    private String checkOption = "none";
    private boolean updatable;

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
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        View view = (View) o;

        if (updatable != view.updatable) return false;
        if (checkOption != null ? !checkOption.equals(view.checkOption) : view.checkOption != null) return false;
        if (columns != null ? !columns.equals(view.columns) : view.columns != null) return false;
        if (definition != null ? !definition.equals(view.definition) : view.definition != null) return false;
        if (container != null ? !container.getName().equals(view.container.getName()) : view.container != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = definition != null ? definition.hashCode() : 0;
        result = 31 * result + (checkOption != null ? checkOption.hashCode() : 0);
        result = 31 * result + (updatable ? 1 : 0);
        result = 31 * result + (container != null ? container.hashCode() : 0);
        result = 31 * result + (columns != null ? columns.hashCode() : 0);
        return result;
    }
}
