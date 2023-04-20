package com.example.onskeliste.repository;

import com.example.onskeliste.model.User;
import com.example.onskeliste.utility.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;

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

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)");

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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM users WHERE username = ?");

            //set attribute in prepared statement
            preparedStatement.setString(1, user.getUsername());

            //execute
            ResultSet result = preparedStatement.executeQuery();

            //returner true hvis brugernavnet ikke er i databasen allerede
            return !result.next();
        } catch (SQLException e) {
            System.out.println("Fejl under check for duplicates");
            e.printStackTrace();
        }
        return false;
    }
    public boolean verifyLoginInfo(User user) {
        try{
            //connect to db
            Connection connection = ConnectionManager.getConnection(dbURL, uID, pwd);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");

            //set attribute in prepared statement
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            //execute
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Fejl under login");
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getWishlist(User user){
        ArrayList<String> wishlist = new ArrayList<>();
        try{
            //connect to db
            Connection connection = ConnectionManager.getConnection(dbURL, uID, pwd);

            //Hent userID
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM users WHERE username = ?");
            preparedStatement.setString(1, user.getUsername());
            ResultSet result = preparedStatement.executeQuery();
            int userId;
            result.next();
            userId = result.getInt("id");

            //Brug userID til at hente ønskeliste links og initialiser ArrayList
            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT link FROM links WHERE user_id = ?");
            preparedStatement2.setInt(1, userId);
            ResultSet result2 = preparedStatement2.executeQuery();
            while (result2.next()) {
                String link = result2.getString("link");
                wishlist.add(link);
            }
            return wishlist;
        } catch (SQLException e) {
            System.out.println("Fejl under oprettelse af ønskeliste attribute");
            e.printStackTrace();
        }
        return wishlist;
    }

    public void removeWishFromDB(String byeLink, HttpSession session) {
        try{
            //connect to db
            Connection connection = ConnectionManager.getConnection(dbURL, uID, pwd);

            //Hent userID
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM users WHERE username = ?");
            preparedStatement.setString(1, (String) session.getAttribute("username"));
            ResultSet result = preparedStatement.executeQuery();
            int userId;
            result.next();
            userId = result.getInt("id");

            //Brug userID og link til at fjerne link fra databasen.
            PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM links WHERE user_id = ? AND link = ?");
            preparedStatement2.setInt(1, userId);
            preparedStatement2.setString(2, byeLink);
            preparedStatement2.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Fejl under slettelse af link i DB");
            e.printStackTrace();
        }
    }

    public void addWishtoDB(String addLink, HttpSession session) {
        try{
            //connect to db
            Connection connection = ConnectionManager.getConnection(dbURL, uID, pwd);

            //Hent userID
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM users WHERE username = ?");
            preparedStatement.setString(1, (String) session.getAttribute("username"));
            ResultSet result = preparedStatement.executeQuery();
            int userId;
            result.next();
            userId = result.getInt("id");

            //Tilføj til DB ved hjælp af user_id.
            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO links(user_id, link) VALUES (?, ?)");
            preparedStatement2.setInt(1, userId);
            preparedStatement2.setString(2, addLink);
            preparedStatement2.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Fejl under tilføjelse til DB");
            e.printStackTrace();
        }
    }
}
