package ru.kirsanov.mdbo.metamodel.entity;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * date: 24.03.11
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public interface Container {
    Container getParent();

    void setContainer(Container container);

    public String getName();
}
