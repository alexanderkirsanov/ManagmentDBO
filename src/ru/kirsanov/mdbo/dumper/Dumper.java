package ru.kirsanov.mdbo.dumper;

import ru.kirsanov.mdbo.dumper.composer.Encoding;
import ru.kirsanov.mdbo.dumper.composer.IComposer;
import ru.kirsanov.mdbo.dumper.exception.DumperNotFound;
import ru.kirsanov.mdbo.dumper.exception.IncorrectDumper;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;
import ru.kirsanov.mdbo.dumper.parser.Dumpable;
import ru.kirsanov.mdbo.dumper.parser.Dumpers;
import ru.kirsanov.mdbo.dumper.parser.IDumper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

public class Dumper {

    private Dumpers dumpers;

    public Dumper(Connection connection, IComposer composer, String encoding, String path) throws IOException {
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                if (file.canWrite()) {
                    dumpers = new Dumpers(connection, path, encoding, composer);
                } else {
                    throw new IOException("File can not be write");
                }
            } else {
                throw new IOException("It is not folder");
            }
        } catch (NullPointerException e) {
            throw new IOException("Path not found");
        }
    }

    public Dumper(Connection connection, IComposer composer) throws IOException {
        this(connection, composer, Encoding.UTF8, new File("").getCanonicalPath()+"/");
    }

    public void dump(Dumpable dumpableEntity) throws DumperNotFound, IncorrectDumper, NoColumnForDumpException, SQLException, FileNotFoundException, UnsupportedEncodingException {
        IDumper dumper = dumpers.find(dumpableEntity);
        dumper.execute(dumpableEntity);
    }


}
