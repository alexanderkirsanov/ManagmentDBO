package ru.kirsanov.mdbo.dumper.writer;

public class SingleSQLWriterTest {
//    private PrintWriter mockedPrintWriter;
//    private SingleSQLWriter writer;
//    private List<String> columns;
//    private String tableName;
//
//    @Before
//    public void setUp() throws FileNotFoundException, UnsupportedEncodingException {
//        mockedPrintWriter = mock(PrintWriter.class);
//        columns = new ArrayList<String>();
//        columns.add("id");
//        columns.add("name");
//        tableName = "table";
//        writer = new SingleSQLWriter(mockedPrintWriter, tableName, columns);
//    }
//
//    @Test
//    public void writeMultipleLineTest() throws Exception {
//        writer.write(new String[]{"1", "Alexandr"});
//        writer.setEnd();
//        writer.write(new String[]{"2", "Andrey"});
//        String firstLine = "INSERT INTO " + tableName + "(id, name) ";
//        String secondLine = " VALUES";
//        String thirdLine = "('1','Alexandr'),";
//        String fourthLine = "('2','Andrey');";
//        verify(mockedPrintWriter).write(firstLine);
//        verify(mockedPrintWriter).write(secondLine);
//        verify(mockedPrintWriter).write(thirdLine);
//        verify(mockedPrintWriter).write(fourthLine);
//    }
//
//    @Test
//    public void writeOneLineTest() throws Exception {
//        writer.setEnd();
//        writer.write(new String[]{"1", "Alexandr"});
//        String firstLine = "INSERT INTO " + tableName + "(id, name) ";
//        String secondLine = " VALUES";
//        String thirdLine = "('1','Alexandr');";
//        verify(mockedPrintWriter).write(firstLine);
//        verify(mockedPrintWriter).write(secondLine);
//        verify(mockedPrintWriter).write(thirdLine);
//    }
//
//    @Test
//    public void writeOneLineWithSpecialCharacterTest() throws Exception {
//        writer.setEnd();
//        String name = "Alexandr\" ";
//        writer.write(new String[]{"1", name});
//        String firstLine = "INSERT INTO " + tableName + "(id, name) ";
//        String secondLine = " VALUES";
//        String thirdLine = "('1','Alexandr\\\" ');";
//        writer.close();
//        verify(mockedPrintWriter).write(firstLine);
//        verify(mockedPrintWriter).write(secondLine);
//        verify(mockedPrintWriter).write(thirdLine);
//    }
}
