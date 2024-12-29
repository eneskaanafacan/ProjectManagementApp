package dao;

import core.DatabaseConnection;
import entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDao {
    private Connection connection;

    public EmployeeDao() {
        this.connection = DatabaseConnection.getInstance().getConnection(); // Singleton yapısı sayesinde sadece bağlanabilirsin dedik
    }

    // Filtreleme amaçlı (CustomerController içerisindeki filter metodu) oluşturduğum custom query'nin çalışmasını sağlayacak metod
    public ArrayList<Employee> query(String query) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                employees.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    // Veritabından veri silme işlemi
    public boolean delete(int id) {
        String query = "DELETE FROM employees WHERE employee_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Veritabanında veri güncelleme işlemi
    public boolean update(Employee employee) {
        String query = "UPDATE employees SET " +
                "name = ? , " +
                "surname = ? , " +
                "position = ? , " +
                "email = ? " +
                "WHERE employee_id = ? ";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getPosition());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getEmployeeID());
            return preparedStatement.executeUpdate() != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Veritabanına Kaydetme işlemi yazalım.
    public boolean save(Employee employee) {
        String query = "INSERT INTO employees " +
                "(name, surname, position, email)" +
                "VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setString(3, employee.getPosition());
            preparedStatement.setString(4, employee.getEmail());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Seçtiğim ID'ye göre kullanıcıyı getirme metodu
    public Employee getById(int id) {
        Employee employee = null;
        String query = "SELECT * FROM employees WHERE employee_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = this.match(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    // Bütün kullanıcıları getir
    public ArrayList<Employee> findAllProjects() {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT * FROM employees");
            while (resultSet.next()) {
                employees.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    public Employee match(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeID(resultSet.getInt("employee_id"));
        employee.setName(resultSet.getString("name"));
        employee.setSurname(resultSet.getString("surname"));
        employee.setPosition(resultSet.getString("position"));
        employee.setEmail(resultSet.getString("email"));
        return employee;
    }

    public ArrayList<Employee> findByName(String name) {
        ArrayList<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees WHERE name LIKE ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee employee = this.match(rs);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Çalışan ID'yi ad ve soyad ile al
    public int getEmployeeId(String name, String surname) {
        String sql = "SELECT employee_id FROM employees WHERE name = ? AND surname = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("employee_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Çalışan bulunamadı.
    }
}
