package ru.kirsanov.mdbo.metamodel.entity;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 21.04.11
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public interface IView extends Container{
    String getDefinition();

    String getCheckOption();
    void setCheckOption(String str);

    boolean isUpdatable();
    void setUpdatable(boolean value);

}
