package ru.kirsanov.mdbo.dumper.writer;

import ru.kirsanov.mdbo.dumper.validator.Validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class PlainWriter implements IWriter {
    private char delimiter = ',';
    private PrintWriter printWriter;
    private StringBuilder stringBuilder = new StringBuilder();

    public PlainWriter(PrintWriter printWriter) throws FileNotFoundException, UnsupportedEncodingException {
        this.printWriter = printWriter;
    }

    public PlainWriter(String s, String encoding) throws FileNotFoundException, UnsupportedEncodingException {
        this(new PrintWriter(new File(s), encoding));
    }

    @Override
    public void write(String[] line) {
        int i = 0;
        for (String currentLine : line) {
            stringBuilder.append(Validator.escape(currentLine, delimiter));
            i++;
            if (i < line.length) {
                stringBuilder.append(delimiter);
            }
        }
        printWriter.write(stringBuilder.toString());
        stringBuilder.delete(0, Integer.MAX_VALUE);
    }

    @Override
    public void close() {
        printWriter.flush();
        printWriter.close();
    }

    @Override
    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }
}
