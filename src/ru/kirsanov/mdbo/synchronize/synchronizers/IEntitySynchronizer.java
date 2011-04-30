package ru.kirsanov.mdbo.synchronize.synchronizers;

import ru.kirsanov.mdbo.metamodel.entity.Model;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 23.04.11
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface IEntitySynchronizer {
    Model execute(Model model) throws Throwable;
}
