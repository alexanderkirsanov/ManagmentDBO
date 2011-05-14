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

    @Test
    public void textWithDelimiterWriteTest() {
        String[] line = {"a,as", "b", "c"};
        writer.write(line);
        writer.close();
        verify(mockedPrintWriter).write("\"a,as\",b,c");
    }

    @Test
    public void textWithSpaceWriteTest() {
        String[] line = {"a as", "b", "c"};
        writer.write(line);
        writer.close();
        verify(mockedPrintWriter).write("a as,b,c");
    }

        @Test
    public void textWithSpaceAndSpaceDelimiterWriteTest() {
        String[] line = {"a as", "b", "c"};
        writer.setDelimiter(' ');
        writer.write(line);
        writer.close();
        verify(mockedPrintWriter).write("\"a as\" b c");
    }
}
