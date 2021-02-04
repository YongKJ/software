
public class CentralController{
    
    public TestController test;
    public HomePageController homePage;
    public ContentDisplayController contentDisplay;
    public UserLoginController userLogin;
    public UserRegistrationController userRegistration;
    public BackgroundManagementController backgroundManagement;

    public CentralController(){
        test = new TestController();
        homePage = new HomePageController();
        contentDisplay = new ContentDisplayController();
        userLogin = new UserLoginController();
        userRegistration = new UserRegistrationController();
        backgroundManagement = new BackgroundManagementController();
    }

}

//javac -d ../classes/ -cp ../../../../lib/*:../lib/*:controller/:service/:dao/:entity/ controller/CentralController.java