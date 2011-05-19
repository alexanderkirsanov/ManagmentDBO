package ru.kirsanov.mdbo.dumper.parser;

import ru.kirsanov.mdbo.dumper.exception.IncorrectDumper;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface IDumper {
    void execute(Dumpable dumpable) throws NoColumnForDumpException, FileNotFoundException, SQLException, UnsupportedEncodingException, IncorrectDumper;
}
