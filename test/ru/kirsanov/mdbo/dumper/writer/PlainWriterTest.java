package ru.kirsanov.mdbo.dumper.writer;

import org.junit.Before;
import org.junit.Test;
import ru.kirsanov.mdbo.dumper.exception.NoColumnForDumpException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlainWriterTest {
    private IPlainWriter writer;
    private PrintWriter mockedPrintWriter;

    @Before
    public void setUp() throws FileNotFoundException, UnsupportedEncodingException {
        mockedPrintWriter = mock(PrintWriter.class);
        writer = new PlainWriter(mockedPrintWriter);
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

    @Test
    public void textWithSpecialSymbolsWriteTest() {
        String[] line = {"a\\as", "b", "c"};
        writer.setDelimiter(';');
        writer.write(line);
        writer.close();
        verify(mockedPrintWriter).write("\"a\\as\";b;c");
    }

    @Test(expected = UnsupportedEncodingException.class)
    public void executeWithIncorrectEncodingShouldBeThrowExceptionTest() throws NoColumnForDumpException, FileNotFoundException, UnsupportedEncodingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        IWriter writer = new PlainWriter("text.txt", "Incorrect encoding");
    }

}
