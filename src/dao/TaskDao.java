package dao;

import core.DatabaseConnection;
import entity.Task;
import entity.Task.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {

    private Connection connection;

    // Constructor to initialize the connection
    public TaskDao() { this.connection = DatabaseConnection.getInstance().getConnection(); }

    // Create a new task in the database
    public void createTask(Task task) throws SQLException {
        String query = "INSERT INTO tasks (task_id, project_id, employee_id, name, start_date, end_date, work_days, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, task.getTaskID());
            stmt.setInt(2, task.getProjectID());
            stmt.setInt(3, task.getEmployeeID());
            stmt.setString(4, task.getName());
            stmt.setDate(5, task.getStartDate());
            stmt.setDate(6, task.getEndDate());
            stmt.setDouble(7, task.getWorkDays());
            stmt.setString(8, task.getStatus().getStatus());
            stmt.executeUpdate();
        }
    }

    // Read a task by its ID
    public Task getTaskById(int taskID) throws SQLException {
        String query = "SELECT * FROM tasks WHERE task_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, taskID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToTask(rs);
                }
            }
        }
        return null;
    }

    // Get all tasks
    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tasks.add(mapToTask(rs));
            }
        }
        return tasks;
    }


    // Delete a task by its ID
    public void deleteTask(int taskID) throws SQLException {
        String query = "DELETE FROM tasks WHERE task_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, taskID);
            stmt.executeUpdate();
        }
    }

    // Helper method to map ResultSet to Task object
    private Task mapToTask(ResultSet rs) throws SQLException {
        int taskID = rs.getInt("task_id");
        int projectID = rs.getInt("project_id");
        int employeeID = rs.getInt("employee_id");
        String name = rs.getString("name");
        Date startDate = rs.getDate("start_date");
        Date endDate = rs.getDate("end_date");
        double workDays = rs.getDouble("work_days");
        Status status = Status.valueOf(rs.getString("status").toUpperCase().replace(" ", "_").replace("İ", "I"));
        return new Task(taskID, projectID, employeeID, name, startDate, endDate, workDays, status);
    }


}
