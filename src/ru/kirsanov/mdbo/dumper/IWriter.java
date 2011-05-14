package ru.kirsanov.mdbo.dumper;

/**
 * Created by IntelliJ IDEA.
 * User: lqip32
 * Date: 14.05.11
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public interface IWriter {
    String CP1251 = "Cp1251";
    String UTF8 = "UTF-8";

    void write(String[] line);

    void close();

    void setDelimiter(char delimiter);
}
