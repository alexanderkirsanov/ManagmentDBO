package ru.kirsanov.mdbo.dumper.writer;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 14.05.11
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public interface IWriter {
    void write(String[] line);

    void close();

    void setDelimiter(char delimiter);
}
