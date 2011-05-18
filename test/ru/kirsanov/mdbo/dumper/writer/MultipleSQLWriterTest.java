package ru.kirsanov.mdbo.dumper.writer;

public class MultipleSQLWriterTest {

//    private PrintWriter mockedPrintWriter;
//    private ArrayList<String> columns;
//    private String tableName;
//    private MultipleSQLWriter writer;
//
//    @Before
//    public void setUp() throws FileNotFoundException, UnsupportedEncodingException {
//        mockedPrintWriter = mock(PrintWriter.class);
//        columns = new ArrayList<String>();
//        columns.add("id");
//        columns.add("name");
//        tableName = "table";
//        writer = new MultipleSQLWriter(mockedPrintWriter, tableName, columns);
//    }
//
//    @Test
//    public void writeMultipleLineTest() throws Exception {
//        writer.write(new String[]{"1", "Alexandr"});
//        writer.write(new String[]{"2", "Andrey"});
//        String firstLine = "INSERT INTO " + tableName + "(id, name) VALUES('1','Alexandr');";
//        String secondLine = "INSERT INTO " + tableName + "(id, name) VALUES('2','Andrey');";
//        verify(mockedPrintWriter).write(firstLine);
//        verify(mockedPrintWriter).write(secondLine);
//    }
}
