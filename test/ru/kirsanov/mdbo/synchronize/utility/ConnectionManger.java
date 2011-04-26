package ru.kirsanov.mdbo.synchronize.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManger {
    private String userName = "lqip32";
    private String password = "4f3v6";
    private String dbms;
    private String serverName;
    private String dbName;

    public ConnectionManger(ConnectionData connectionData) {
        this.dbms = connectionData.getDbms();
        this.userName = connectionData.getUserName();
        this.password = connectionData.getPassword();
        this.serverName = connectionData.getServerName();
        this.dbName = connectionData.getDbName();
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
          Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

            conn = DriverManager.
                    getConnection("jdbc:" + this.dbms + "://" + this.serverName + "/" + this.dbName, connectionProps);

        return conn;
    }
}
