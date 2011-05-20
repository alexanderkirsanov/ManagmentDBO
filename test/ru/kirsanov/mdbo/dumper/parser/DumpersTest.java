package ru.kirsanov.mdbo.dumper.parser;

import org.junit.Test;
import ru.kirsanov.mdbo.dumper.composer.Encoding;
import ru.kirsanov.mdbo.dumper.composer.PlainComposer;
import ru.kirsanov.mdbo.dumper.exception.DumperNotFound;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionData;
import ru.kirsanov.mdbo.synchronize.utility.ConnectionManger;

public class DumpersTest {
    private ConnectionManger cm = new ConnectionManger(new ConnectionData(ConnectionData.getBaseName(), "postgresql"));

    @Test(expected = DumperNotFound.class)
    public void findNotExistsDumper() throws Exception, DumperNotFound {
        Dumpers dumpers = new Dumpers(cm.getConnection(), "/home/lqip32222/", Encoding.UTF8, new PlainComposer(','));
        dumpers.find(new Dumpable() {

        });
    }
}
