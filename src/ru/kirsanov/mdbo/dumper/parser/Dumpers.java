package ru.kirsanov.mdbo.dumper.parser;

import ru.kirsanov.mdbo.dumper.composer.IComposer;
import ru.kirsanov.mdbo.dumper.exception.DumperNotFound;
import ru.kirsanov.mdbo.dumper.query.TableDumpQuery;
import ru.kirsanov.mdbo.metamodel.entity.Model;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Dumpers {

    private Map<String, IDumper> list;

    public Dumpers(final Connection connection, final String path, final String encoding, final IComposer composer) {
        list = new HashMap<String, IDumper>() {{
            put(Model.class.getSimpleName(), new ModelDumper(connection, path, encoding, composer));
            put(TableDumpQuery.class.getSimpleName(), new QueryDumper(connection, path, encoding, composer));
        }};
    }

    public IDumper find(Dumpable dumpable) throws DumperNotFound {
        IDumper dumper = (list.get(dumpable.getClass().getSimpleName()) == null) ? list.get(dumpable.getClass().getSuperclass().getSimpleName()) : list.get(dumpable.getClass().getSimpleName());
        if (dumper == null) {
            throw new DumperNotFound();
        }
        return dumper;
    }
}
