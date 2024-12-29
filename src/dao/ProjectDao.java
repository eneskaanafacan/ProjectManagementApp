package dao;

import core.DatabaseConnection;
import entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectDao {
    private Connection connection;

    public ProjectDao() {
        this.connection = DatabaseConnection.getInstance().getConnection();       //Singleton yapısı sayesinde sadece bağlanabilirsin dedik
    }

    //Filtreleme amaçlı (CustomerController içerisindeki filter metodu) oluşturduğum custom query'nin çalışmasını sağlayacak metod
    public ArrayList<Project> query(String query){
        ArrayList<Project> projects = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()){
                projects.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projects;
    }

    //Veritabından veri silme işlemi
    public boolean delete(int id){
        //Burada parametre olarak customer değil id. Çünkü silmek yeterli, customer'ın name, mail vs.sini istemiyorum.
        String query = "DELETE FROM projects WHERE project_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Veritabanında veri güncelleme işlemi
    public boolean update(Project project){
        String query = "UPDATE projects SET " +
                "employee_id = ? , " +
                "name = ? , " +
                "start_date = ? , " +
                "end_date = ? , " +
                "delay_days = ? " +
                "WHERE project_id = ? ";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, project.getEmployeeID());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setDate(3, project.getStartDate());
            preparedStatement.setDate(4, project.getEndDate());
            preparedStatement.setInt(5, project.getDelayDays());
            preparedStatement.setInt(6, project.getProjectID());
            return preparedStatement.executeUpdate() != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Veritabanına Kaydetme işlemi yazalım.
    public boolean save(Project project){
        //Boolean dönecek. Kaydedildi, Kaydedilmedi kontrolünü yapmak için.
        //Buradaki mevzu: Bir customer nesneli alıp objeyi kaydetmekle iş dönecek.
        String query = "INSERT INTO projects " +
                "(employee_id, name, start_date, end_date, delay_days)" +
                "VALUES(?,?,?,?,?)";

        try {
            //project nesnesinden gelen değerleri veritabanına kaydedelim
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, project.getEmployeeID());
            preparedStatement.setString(2, project.getName());   //ENUM'ları String olarak kullanmalısın.
            preparedStatement.setDate(3, project.getStartDate());
            preparedStatement.setDate(4, project.getEndDate());
            preparedStatement.setInt(5, project.getDelayDays());

            //executeUpdate hata varsa geriye -1 döndürür. Bu işlem: -1 değilse true döndür. Metoddan direkt çıkar
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Seçtiğim ID'ye göre kullanıcıyı getirme metodu
    public Project getById(int id){
        Project project = null;    //Bu Customer nesnesini daha sonra matchleyip döndüreceğiz. Şimdilik null
        String query = "SELECT * FROM projects WHERE project_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){      //ResultSet varsa
                project = this.match(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return project;
    }

    //Bütün kullanıcıları getir
    public ArrayList<Project> findAllProjects(){
        ArrayList<Project> projects = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery("SELECT * FROM projects");
            while (resultSet.next()){
                projects.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projects;
    }

    public Project match(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setProjectID(resultSet.getInt("project_id"));
        project.setEmployeeID(resultSet.getInt("employee_id"));
        project.setName(resultSet.getString("name"));
        project.setStartDate(resultSet.getDate("start_date"));
        project.setEndDate(resultSet.getDate("end_date"));
        project.setDelayDays(resultSet.getInt("delay_days"));
        return project;
    }

    public ArrayList<Project> findByName(String name) {
        ArrayList<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects WHERE name LIKE ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%"); // Proje adı içinde geçenleri arar
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Project project = new Project();
                project.setProjectID(rs.getInt("project_id"));
                project.setEmployeeID(rs.getInt("employee_id"));
                project.setName(rs.getString("name"));
                project.setStartDate(rs.getDate("start_date"));
                project.setEndDate(rs.getDate("end_date"));
                project.setDelayDays(rs.getInt("delay_days"));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
