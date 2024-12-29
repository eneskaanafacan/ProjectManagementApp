package core;

import javax.swing.*;

public class Helper {

    //Dialog panel to "Are You sure?" question
    public static boolean isConfirm(String str){
        String message;
        if (str.equals("sure?")){
            message = "Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
        } else {
            message = str;
        }
        return JOptionPane.showConfirmDialog(null,message,"Emin Misin?", JOptionPane.YES_NO_OPTION) == 0;
    }

    //Tema belirle
    public static void setTheme(){
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if (info.getName().equals("Nimbus")){
                try {
                    UIManager.setLookAndFeel(info.getClassName());  //Swingin temasını Nimbus olarak ayarla
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    //Email and Password Control
    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fields){
        for (JTextField field: fields){
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    public static boolean isEmailValid(String mail){
        /*  Bir şeyin mail olup olmadığını nasıl anlarsın?
        @ olmak zorunda, @'ten önce bir değer olmalı, @'ten sonra değer ve nokta olacak. */
        if (mail == null ||mail.trim().isEmpty()) return false;

        if (!mail.contains("@")) return false;

        //Şimdi @ işaretini referans alarak maili 2'ye bölelim. Bütün işlemleri @ öncesi ve @ sonrası olarak yapıcaz.
        String[] parts = mail.split("@");
        if (parts.length !=2) return false;
        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) return false;
        if (!parts[1].contains("."))    return false;   // @'den sonra . yoksa false
        return true;
    }

    //Show message in the Dialog Panel
    public static void showMessage(String message){
        String msg;
        String title;

        switch (message){
            case "fill":
                msg = "Lütfen Tüm Alanları Doldurunuz!";
                title = "HATA!";
                break;
            case "done":
                msg = "İşlem Başarılı!";
                title = "Sonuç";
                break;
            case "error":
                msg = "Bir hata oluştu!";
                title = "HATA";
                break;
            default:
                msg = message;
                title = "Mesaj";
        }

        // Mesaj kutusunun yapısı. Bu yapı sabittir. msg ve title ı parametre olarak verdik.
        JOptionPane.showMessageDialog(null, msg,title,JOptionPane.INFORMATION_MESSAGE);
    }
}
