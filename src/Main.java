import core.Helper;
import entity.User;
import view.DashBoardPage.DashBoardView;

public class Main {
    public static void main(String[] args) {

        Helper.setTheme();
        //Kolay erişim için:
        //LogInView logInView = new LogInView();
        Business.UserController userController = new Business.UserController();
        User user = userController.findByLogin("user1@example.com", "password123");   //Varsayılan olarak mail ve paswword direkt bu
        DashBoardView dashBoardView = new DashBoardView(user);    //Dashboardı aç

    }
}