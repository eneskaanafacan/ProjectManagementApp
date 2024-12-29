package entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Project extends ArrayList<String> {
    private Integer projectID;
    private Integer employeeID;
    private String name;
    private Date startDate;
    private Date endDate;
    private Integer delayDays;

    // Constructor
    public Project() {
    }

    // Getter and Setter methods
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

    public Integer getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(Integer delayDays) {
        this.delayDays = delayDays;
    }

    // Override toString method
    @Override
    public String toString() {
        return "Project{" +
                "projectID=" + projectID +
                ", employeeID=" + employeeID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", delayDays=" + delayDays +
                '}';
    }

    @Override
    public List<String> reversed() {
        return super.reversed();
    }
}
