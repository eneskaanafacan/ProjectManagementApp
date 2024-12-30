package entity;

import java.sql.Date;

public class Task {
    private Integer taskID;
    private Integer projectID;
    private Integer employeeID;
    private String name;
    private Date startDate;
    private Date endDate;
    private Double workDays;
    private Status status;

    // Enum for task status
    public enum Status {
        TAMAMLANDI("TAMAMLANDI"),
        DEVAM_EDIYOR("DEVAM_EDIYOR"),
        TAMAMLANACAK("TAMAMLANACAK");

        private final String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    // Constructor
    public Task(Integer taskID, Integer projectID, Integer employeeID, String name, Date startDate, Date endDate, Double workDays, Status status) {
        this.taskID = taskID;
        this.projectID = projectID;
        this.employeeID = employeeID;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workDays = workDays;
        this.status = status;
    }

    // Getter and Setter methods
    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Double workDays) {
        this.workDays = workDays;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Override toString method
    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", projectID=" + projectID +
                ", employeeID=" + employeeID +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", workDays=" + workDays +
                ", status=" + status.getStatus() +
                '}';
    }
}
