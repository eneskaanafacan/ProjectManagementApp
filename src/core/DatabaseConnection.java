/*  DatabaseConnection classını Singleton Design Pattern'a uygun şekilde oluşturmak istiyorum. Çünkü bana bir tane
Database connection'ı yetecek. O yüzden bir nesne oluşturacağım ve bunu da bu classta oluşturacağım.

Singleton Design Pattern: Singleton Design Pattern, bir sınıfın sadece bir tane örneğinin (instance) oluşturulmasını
ve bu örneğe global bir erişim noktası sağlanmasını garanti eden bir yazılım tasarım desenidir. Masaüstü(1) */

package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //Singleton Design Pattern

    public static DatabaseConnection instance = null;
    private Connection connection = null;
    public static final String DB_URL = "jdbc:mysql://localhost:3307/pmngmntdb";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "";

    //constructor
    public DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //getter method
    private Connection getConnection(){
        return connection;
    }

    public static Connection getInstance(){
        try {
            if (instance ==null || instance.getConnection().isClosed()) {   // I want to create the Database Connection just once
                instance = new DatabaseConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance.getConnection();
    }
}
