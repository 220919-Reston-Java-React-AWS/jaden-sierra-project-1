package com.revature.repository;

import io.github.cdimascio.dotenv.Dotenv;
import org.postgresql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection createConnection() throws SQLException{

        Driver postgresDriver = new Driver();

        DriverManager.registerDriver(postgresDriver);

//        Dotenv dotenv = Dotenv.load();


        String url = "jdbc:postgresql://localhost:5432/postgres";

        String username = "postgres";
        String password = "6118M@7d68";

        Connection connectionObject = DriverManager.getConnection(url, username, password);


        return connectionObject;
    }

}
