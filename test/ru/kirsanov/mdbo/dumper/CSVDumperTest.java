package ru.kirsanov.mdbo.dumper;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CSVDumperTest {
    private IWriter writer;
    private PrintWriter mockedPrintWriter;

    @Before
    public void setUp() throws FileNotFoundException, UnsupportedEncodingException {
        mockedPrintWriter = mock(PrintWriter.class);
        writer = new CVSWriter(mockedPrintWriter);
    }

    @Test
    public void simpleWriteTest() {
        String[] line = {"a", "b", "c"};
        writer.write(line);
        writer.close();
        verify(mockedPrintWriter).write("a,b,c");
    }
}
