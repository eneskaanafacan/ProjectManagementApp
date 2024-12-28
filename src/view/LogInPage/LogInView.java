package view.LogInPage;

import core.Helper;
import entity.User;
import business.UserController;
import view.DashBoardPage.DashBoardView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInView extends JFrame{
    private UserController userController;

    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_title;
    private JPanel pnl_bottom;
    private JTextField fld_mail;
    private JButton btn_login;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private JPasswordField fld_password;

    public LogInView(){
        this.userController = new UserController();

        this.add(container);
        this.setTitle("Proje Yönetim Sistemi");
        this.setSize(700,700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        btn_login.addActionListener(e ->{
            JTextField[] checkFieldList = {this.fld_password, this.fld_mail};

            if (!Helper.isEmailValid(this.fld_mail.getText())){
                Helper.showMessage("Geçerli Bir E-posta giriniz!");
            } else if (Helper.isFieldListEmpty(checkFieldList)){
                Helper.showMessage("fill");
            } else {
                User user = this.userController.findByLogin(this.fld_mail.getText(), this.fld_password.getText());
                if (user == null){
                    Helper.showMessage("Girdiğiniz bilgilerle eşleşen bir kullanıcı yok!");
                } else {
                    this.dispose();
                    DashBoardView dashBoardView = new DashBoardView(user);  //Arayüzü aç
                }
            }

        });
    }

}
