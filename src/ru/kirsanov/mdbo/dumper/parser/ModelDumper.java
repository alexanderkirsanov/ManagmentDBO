package ru.kirsanov.mdbo.dumper.parser;

import ru.kirsanov.mdbo.dumper.composer.IComposer;
import ru.kirsanov.mdbo.dumper.exception.IncorrectDumper;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.query.ITableDumpQuery;
import ru.kirsanov.mdbo.dumper.query.TableDumpQuery;
import ru.kirsanov.mdbo.metamodel.entity.IColumn;
import ru.kirsanov.mdbo.metamodel.entity.ISchema;
import ru.kirsanov.mdbo.metamodel.entity.ITable;
import ru.kirsanov.mdbo.metamodel.entity.Model;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

public class ModelDumper implements IDumper {

    private QueryDumper queryDumper;

    public ModelDumper(Connection connection, String path, String encoding, IComposer composer) {
        this.queryDumper = new QueryDumper(connection, path, encoding, composer);
    }

    public void execute(Dumpable dumpModel) throws NoColumnForDumpException, FileNotFoundException, SQLException, UnsupportedEncodingException, IncorrectDumper {
        if (dumpModel instanceof Model) {
            Model model = (Model) dumpModel;
            for (ISchema schema : model.getSchemas()) {
                for (ITable table : schema.getTables()) {
                    ITableDumpQuery tableDumpQuery = new TableDumpQuery(table.getName());
                    if (table.getColumns().size() != 0) {
                        for (IColumn column : table.getColumns()) {
                            tableDumpQuery.addColumn(column.getName());
                        }
                        queryDumper.execute(tableDumpQuery);
                    }

                }
            }
        } else {
            throw new IncorrectDumper();
        }
    }
}
