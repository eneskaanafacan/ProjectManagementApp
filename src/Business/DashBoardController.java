package Business;

import dao.EmployeeDao;
import dao.TaskDao;
import entity.Task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashBoardController {
    private ArrayList<Task> tasks;
    private EmployeeDao employeeDao;
    private TaskDao taskDao;


    public DashBoardController() {
        this.employeeDao = new EmployeeDao();
        this.taskDao = new TaskDao();
        this.tasks = new ArrayList<>();
    }

    public boolean addTask(Task task) {
        try {
            taskDao.createTask(task);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Task> getTasksByProjectId(int projectId) {
        try {
            return new ArrayList<>(taskDao.getAllTasks().stream()
                    .filter(task -> task.getProjectID() == projectId)
                    .collect(Collectors.toList()));
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean deleteTask(int taskId) {
        try {
            taskDao.deleteTask(taskId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
