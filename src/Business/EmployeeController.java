package Business;

import core.Helper;
import dao.EmployeeDao;
import dao.ProjectDao;
import entity.Employee;
import entity.Project;

import java.util.ArrayList;

public class EmployeeController {
    private final EmployeeDao employeeDao = new EmployeeDao();

    public ArrayList<Employee> findAll(){
        return this.employeeDao.findAllProjects();
    }

    //Veritabanına kaydetme işlemini bu katmanda da sağlıyorum.
    public boolean save(Employee employee){
        return this.employeeDao.save(employee);
    }

    public Employee getById(int id){
        return this.employeeDao.getById(id);
    }

    public boolean update(Employee employee){
        if (this.getById(employee.getEmployeeID()) == null){
            Helper.showMessage(employee.getEmployeeID() + " ID Kayıtlı çalışan bulunamadı");
            return false;
        }
        return this.employeeDao.update(employee);
    }

    public boolean delete(int id){
        if (this.getById(id) == null){
            Helper.showMessage(id + " ID kayıtlı çalışan bulunamadı");
            return false;
        }
        return this.employeeDao.delete(id);
    }

    public ArrayList<Employee> findByName(String name) {
        return this.employeeDao.findByName(name);
    }
}
