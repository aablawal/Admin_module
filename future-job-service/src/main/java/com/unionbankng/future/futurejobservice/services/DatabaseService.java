package com.unionbankng.future.futurejobservice.services;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
@NoArgsConstructor
public class DatabaseService {

    @Value("${sidekiq.auth.database.url}")
    private String authDBURL;
    @Value("${sidekiq.auth.database.username}")
    private String authDBUsername;
    @Value("${sidekiq.auth.database.password}")
    private String authDBPassword;

    public Connection connect(){
        String connectionUrl =authDBURL
                + "user="+authDBUsername+";"
                + "password="+authDBPassword+";";
        try (Connection connection = DriverManager.getConnection(connectionUrl);) {
            return connection;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
