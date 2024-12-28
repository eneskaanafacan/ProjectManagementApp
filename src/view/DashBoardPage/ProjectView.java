package view.DashBoardPage;

import Business.ProjectController;
import core.Helper;
import entity.Project;

import javax.swing.*;
import java.sql.Date;

public class ProjectView extends JFrame{
    private Project project;
    private ProjectController projectController;

    private JPanel container;
    private JTextField fld_project_name;
    private JTextField fld_employee_id;
    private JTextField fld_start_date;
    private JTextField fld_end_date;
    private JTextField fld_delay_days;
    private JLabel lbl_project_name;
    private JLabel lbl_employee_id;
    private JLabel lbl_start_date;
    private JLabel lbl_end_date;
    private JLabel lbl_delay_days;
    private JButton btn_project_save;
    private JLabel lbl_title;

    public ProjectView(Project project){

        this.project = project;
        this.projectController = new ProjectController();

        //Klasik işlemlerimiz:
        this.add(container);
        this.setTitle("Proje ekle/Düzenle");
        this.setSize(300,500);
        this.setLocationRelativeTo(null);   // Ekranı ortalamaya yarar
        this.setVisible(true);              // Gözükebilmesi için Visible=True olmalı


        if (this.project.getProjectID() == null) {
            this.project.setProjectID(0); // Varsayılan bir değer atayın
        }
        if (project.getProjectID() == 0) {  // Demekki ekleme işlemi yapıyorum.
            lbl_title.setText("Proje Ekle");
        } else {  // ID sıfırdan farklı. Düzenleme işlemi yapıyorum.
            lbl_title.setText("Proje Düzenle");
            this.fld_project_name.setText(this.project.getName() != null ? this.project.getName() : "");
            this.fld_employee_id.setText(
                    this.project.getEmployeeID() != null ? this.project.getEmployeeID().toString() : ""
            );
            this.fld_start_date.setText(
                    this.project.getStartDate() != null ? this.project.getStartDate().toString() : ""
            );
            this.fld_end_date.setText(
                    this.project.getEndDate() != null ? this.project.getEndDate().toString() : ""
            );
            this.fld_delay_days.setText(
                    this.project.getDelayDays() != null ? this.project.getDelayDays().toString() : ""
            );
        }

        this.btn_project_save.addActionListener(e ->{

            //Alanların doluluğunu kontrol et
            JTextField[] checkList = {this.fld_project_name, this.fld_employee_id};
            if (Helper.isFieldListEmpty(checkList)){
                Helper.showMessage("fill");
            }
            else {
                boolean result = false;
                this.project.setName(this.fld_project_name.getText());
                this.project.setEmployeeID(Integer.valueOf(this.fld_employee_id.getText()));
                this.project.setStartDate(Date.valueOf(this.fld_start_date.getText()));
                this.project.setEndDate(Date.valueOf(this.fld_end_date.getText()));
                this.project.setDelayDays(Integer.valueOf(this.fld_delay_days.getText()));

                if (this.project.getProjectID() == 0){    //project boşsa(Demekki ekleme yapacağız)
                    result = this.projectController.save(this.project);
                } else{     //project varsa(Demekki güncelleme yapacağız)
                    result = this.projectController.update(project);
                }

                if (result){
                    Helper.showMessage("done");
                    dispose();
                } else {
                    Helper.showMessage("error");
                }
            }
        });
    }
}
