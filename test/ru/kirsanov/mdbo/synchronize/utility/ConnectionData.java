package ru.kirsanov.mdbo.synchronize.utility;

public class ConnectionData {

    private String dbName = "testbase";
    private String dbms;
    private String userName = "lqip32";
    private String password = "4f3v6";
    private String serverName = "localhost";

    public ConnectionData(String serverName, String dbName, String dbms, String userName, String password) {
        this.serverName = serverName;
        this.dbName = dbName;
        this.dbms = dbms;
        this.userName = userName;
        this.password = password;
    }

    public ConnectionData(String dbName, String dbms) {
        this.dbName = dbName;
        this.dbms = dbms;
    }

    public ConnectionData(String dbms) {
        this.dbms = dbms;
    }

    public String getDbms() {
        return dbms;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getServerName() {
        return serverName;
    }

    public String getDbName() {
        return dbName;
    }

    public static String getBaseName(){
        return "testbase";
    }


}
