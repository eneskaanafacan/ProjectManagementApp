package view.DashBoardPage;

import Business.EmployeeController;
import Business.ProjectController;
import core.Helper;
import entity.Employee;
import entity.Project;

import javax.swing.*;
import java.sql.Date;

public class EmployeeView extends JFrame{
    private Employee employee;
    private EmployeeController employeeController;

    private JPanel container;
    private JLabel lbl_title;
    private JTextField fld_employee_name;
    private JTextField fld_employee_surname;
    private JTextField fld_employee_position;
    private JTextField fld_employee_email;
    private JButton btn_employee_save;
    private JLabel lbl_employee_name;
    private JLabel lbl_employee_surname;
    private JLabel lbl_employee_position;
    private JLabel lbl_employee_email;

    public EmployeeView(Employee employee){

        this.employee = employee;
        this.employeeController = new EmployeeController();

        //Klasik işlemlerimiz:
        this.add(container);
        this.setTitle("Çalışan ekle/Düzenle");
        this.setSize(300,500);
        this.setLocationRelativeTo(null);   // Ekranı ortalamaya yarar
        this.setVisible(true);              // Gözükebilmesi için Visible=True olmalı


        if (this.employee.getEmployeeID() == null) {
            this.employee.setEmployeeID(0); // Varsayılan bir değer atayın
        }
        if (employee.getEmployeeID() == 0) {  // Demekki ekleme işlemi yapıyorum.
            lbl_title.setText("Çalışan Ekle");
        } else {  // ID sıfırdan farklı. Düzenleme işlemi yapıyorum.
            lbl_title.setText("Çalışanı Düzenle");
            this.fld_employee_name.setText(this.employee.getName() != null ? this.employee.getName() : "");
            this.fld_employee_surname.setText(
                    this.employee.getSurname() != null ? this.employee.getSurname() : ""
            );
            this.fld_employee_position.setText(
                    this.employee.getPosition() != null ? this.employee.getPosition() : ""
            );
            this.fld_employee_email.setText(
                    this.employee.getEmail() != null ? this.employee.getEmail() : ""
            );
        }

        this.btn_employee_save.addActionListener(e ->{

            //Alanların doluluğunu kontrol et
            JTextField[] checkList = {this.fld_employee_name, this.fld_employee_surname};
            if (Helper.isFieldListEmpty(checkList)){
                Helper.showMessage("fill");
            }
            else {
                boolean result = false;
                this.employee.setName(this.fld_employee_name.getText());
                this.employee.setSurname(this.fld_employee_surname.getText());
                this.employee.setPosition(this.fld_employee_position.getText());
                this.employee.setEmail(this.fld_employee_email.getText());

                if (this.employee.getEmployeeID() == 0){    //employee boşsa(Demekki ekleme yapacağız)
                    result = this.employeeController.save(this.employee);
                } else{     //employee varsa(Demekki güncelleme yapacağız)
                    result = this.employeeController.update(employee);
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
