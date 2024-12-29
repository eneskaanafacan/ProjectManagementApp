package Business;

import core.Helper;
import dao.ProjectDao;
import entity.Project;

import java.util.ArrayList;

public class ProjectController {
    private final ProjectDao projectDao = new ProjectDao();

    public ArrayList<Project> findAll(){
        return this.projectDao.findAllProjects();
    }

    //Veritabanına kaydetme işlemini bu katmanda da sağlıyorum.
    public boolean save(Project project){
        return this.projectDao.save(project);
    }

    public Project getById(int id){
        return this.projectDao.getById(id);
    }

    public boolean update(Project project){
        if (this.getById(project.getProjectID()) == null){
            Helper.showMessage(project.getProjectID() + " ID Kayıtlı proje bulunamadı");
            return false;
        }
        return this.projectDao.update(project);
    }

    public boolean delete(int id){
        if (this.getById(id) == null){
            Helper.showMessage(id + " ID kayıtlı proje bulunamadı");
            return false;
        }
        return this.projectDao.delete(id);
    }

    public ArrayList<Project> findByName(String name) {
        return this.projectDao.findByName(name); // DAO katmanından proje listesini alır
    }

    public ArrayList<String> getProjectTasks(int selectedId) {
        return this.projectDao.getById(selectedId); // Adjust this based on actual method in ProjectDao
    }

}
