package ru.kirsanov.mdbo.synchronize.utility;

public class ConnectionData {

    private String dbName;
    private String dbms;
    private String userName;
    private String password;
    private String serverName;

    public ConnectionData(String serverName, String dbName, String dbms, String userName, String password) {
        this.serverName = serverName;
        this.dbName = dbName;
        this.dbms = dbms;
        this.userName = userName;
        this.password = password;
    }

    public ConnectionData(String dbName, String dbms) {
        this.serverName = "localhost";
        this.dbName = dbName;
        this.dbms = dbms;
        this.userName = "lqip32";
        this.password = "4f3v6";
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


}
