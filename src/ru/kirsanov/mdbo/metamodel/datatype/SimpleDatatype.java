package ru.kirsanov.mdbo.metamodel.datatype;

public class SimpleDatatype implements DataType {

    private String name = null;
    private int precision;
    private int scale;

    public String getName() {
        return this.name;
    }

    public String getSqlString() {
        StringBuilder result = new StringBuilder(name);
        if (precision != 0) {
            result.append(" (").append(precision);

            if (scale != 0) {
                result.append(", ").append(scale);
            }
            result.append(")");
        }
        return result.toString();
    }

    public SimpleDatatype(String name) {
        this.name = name;
    }

    public SimpleDatatype(String name, int precision) {
        this(name);
        this.precision = precision;
    }

    public SimpleDatatype(String name, int precision, int scale) {
        this(name, precision);
        this.scale = scale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleDatatype that = (SimpleDatatype) o;

        if (precision != that.precision) return false;
        if (scale != that.scale) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + precision;
        result = 31 * result + scale;
        return result;
    }
}
