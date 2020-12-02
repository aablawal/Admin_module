package com.unionbankng.future.futurejobservice.services;
import com.unionbankng.future.futurejobservice.pojos.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.*;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    @Value("${sidekiq.auth.database.url}")
    private String authDBURL;
    @Value("${sidekiq.auth.database.username}")
    private String authDBUsername;
    @Value("${sidekiq.auth.database.password}")
    private String authDBPassword;

    public User getUserById(Long userId){
        String connectionUrl =authDBURL
                + "user="+authDBUsername+";"
                + "password="+authDBPassword+";";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT TOP 1 * from app_user where id=" + userId;
                ResultSet rs = statement.executeQuery(query);
                User user=new User();
                if(rs.next()) {
                    user.setId(rs.getLong("id"));
                    user.setFullName(rs.getString("first_name")+" "+rs.getString("last_name"));
                    user.setAccountNumber(rs.getString("account_number"));
                    user.setAccountName(rs.getString("account_name"));
                    user.setUsername(rs.getString("username"));
                    user.setAddress(rs.getString("user_address"));
                    user.setCountry(rs.getString("country"));
                    user.setEmail(rs.getString("email"));
                    user.setImg(rs.getString("img"));
                    user.setUuid(rs.getString("uuid"));
                    user.setIsEnabled(rs.getBoolean("is_enabled"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setCreatedAt(rs.getString("created_at"));
                    user.setStateOfResidence(rs.getString("state_of_residence"));
                }else{
                    user=null;
                }
                return  user;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
