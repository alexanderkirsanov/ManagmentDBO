package ru.kirsanov.mdbo.synchronize.utility;

import java.util.Properties;

public class ConnectionData {

    private String dbName;
    private String dbms;
    private String userName;
    private String password;
    private String serverName = "localhost";
    private static final String USER_NAME = "userName";
    private static final String USER_PASS = "userPass";
    private static final String SERVER = "server";

    private ConnectionData() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        Properties configProperties = configurationManager.loadProperties();
        this.password = configProperties.getProperty(USER_PASS);
        this.userName = configProperties.getProperty(USER_NAME);
        this.serverName = configProperties.getProperty(SERVER, "localhost");
    }

    public ConnectionData(String dbName, String dbms) {
        this();
        this.dbName = dbName;
        this.dbms = dbms;
    }

    public ConnectionData(String dbms) {
        this();
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

    public static String getBaseName() {
        return "testbase";
    }


}
