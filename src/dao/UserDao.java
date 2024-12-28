package dao;

import core.DatabaseConnection;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    //Singleton
    private Connection connection;

    public UserDao() {
        this.connection = DatabaseConnection.getInstance();
    }

    //Bağlantıyı sağladık. Peki kullanıcı database'de var mı?
    public User findByLogin(String mail, String password){
        //Veritabanında bu kullanıcı var mı? varsa bir User, Entitiy, Nesne olarak kullanıcıyı geri gönder

        User user = null;
        String query = "SELECT * FROM users WHERE email = ? AND passwordd = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = this.match(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    //Bütün kullanıcıları getir
    public ArrayList<User> findAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT * FROM users");
            while (resultSet.next()){
                users.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    //Veritabanındaki bilgilerden bir user nesnesi oluştur
    public User match(ResultSet resultSet) throws SQLException {

        User user = new User("admin", "admin");
        user.setUserId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("passwordd"));
        return user;
    }
}
