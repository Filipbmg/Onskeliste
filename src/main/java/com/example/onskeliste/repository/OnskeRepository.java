package com.example.onskeliste.repository;

import com.example.onskeliste.model.User;
import com.example.onskeliste.utility.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import jakarta.servlet.http.HttpSession;
import java.sql.*;

@Repository
public class OnskeRepository {
    @Value("${spring.datasource.url}")
    private String dbURL;

    @Value("${spring.datasource.username}")
    private String uID;

    @Value("${spring.datasource.password}")
    private String pwd;

    public void addUser(User user){
        try{
            //connect to db
            Connection connection = ConnectionManager.getConnection(dbURL, uID, pwd);

                final String INSERT_QUERY = "INSERT INTO users(username, password) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);

                //set attributer i prepared statement
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());

                //execute
                preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fejl under oprettelse af bruger");
            e.printStackTrace();
        }
    }

    public boolean checkIfDup(User user){
        try{
            //connect to db
            Connection connection = ConnectionManager.getConnection(dbURL, uID, pwd);
            PreparedStatement usernameCheck = connection.prepareStatement("SELECT username FROM users WHERE username = ?");

            //set attribute in prepared statement
            usernameCheck.setString(1, user.getUsername());

            //execute
            ResultSet result = usernameCheck.executeQuery();

            //returner true hvis brugernavnet ikke er i databasen allerede
            return !result.next();
        } catch (SQLException e) {
            System.out.println("Fejl under oprettelse af bruger");
            e.printStackTrace();
        }
        return false;
    }
    public boolean verifyLoginInfo(User user) {
        try{
            //connect to db
            Connection connection = ConnectionManager.getConnection(dbURL, uID, pwd);
            PreparedStatement verifyUser = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");

            //set attribute in prepared statement
            verifyUser.setString(1, user.getUsername());
            verifyUser.setString(2, user.getPassword());

            //execute
            ResultSet result = verifyUser.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Fejl under login");
            e.printStackTrace();
        }
        return false;
    }




}
