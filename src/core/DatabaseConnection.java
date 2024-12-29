/*  DatabaseConnection classını Singleton Design Pattern'a uygun şekilde oluşturmak istiyorum. Çünkü bana bir tane
Database connection'ı yetecek. O yüzden bir nesne oluşturacağım ve bunu da bu classta oluşturacağım.

Singleton Design Pattern: Singleton Design Pattern, bir sınıfın sadece bir tane örneğinin (instance) oluşturulmasını
ve bu örneğe global bir erişim noktası sağlanmasını garanti eden bir yazılım tasarım desenidir. Masaüstü(1) */

package core;

import entity.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance = null;
    private Connection connection = null;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pmngmntdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DatabaseConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    // Task status update method
    public boolean updateTaskStatus(int taskId, Task.Status newStatus) {
        String sql = "UPDATE tasks SET status = ? WHERE task_id = ?";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, newStatus.getStatus());
            pstmt.setInt(2, taskId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
