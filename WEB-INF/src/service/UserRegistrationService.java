
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class UserRegistrationService{

    public HttpServletRequest request;
    public HttpServletResponse response;
    public UsersService uService;
    public List<Users> usersList;
    public String userName;
    public String userPasswd;
    public String[] images;
	public String baseIP;
    public RequestDispatcher dispatcher;
    
    public UserRegistrationService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        uService = new UsersService();
        userName = "";
        userPasswd = "";
		baseIP = "software.yongkj.cn";
    }
    
    public int getUserId() throws Exception {
        int id = -1;
        usersList = uService.getUsers();
        for(int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).getuName().equals(userName)) {
                return usersList.get(i).getuId();
            }
        }
        return id;
    }
    
    public String getImage() {
        images = new String[]{"01","10","11","12","13","14","15","16","17","18","19","20","21","22","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42"};
        Random random = new Random();
        int number = random.nextInt(images.length);
        return images[number] + ".jpg";
    }
    
    public void judge() throws Exception {
        response.setCharacterEncoding("gbk");
        userName = request.getParameter("userName");
        userPasswd = request.getParameter("userPasswd");
        int userId = -1;
        if(userName != null && userPasswd != null) {
            userId = getUserId();
        }else{
            dispatcher = request.getRequestDispatcher("/register.jsp");
            dispatcher.forward(request, response);
            return;
        }
        if(userId == -1) {
            Users user = new Users();
            user.setuName(userName);
            user.setuPasswd(userPasswd);
            uService.addUser(user);
            userId = getUserId();
            request.setAttribute("skipUrl", "http://" + baseIP + "/software/login");
            request.setAttribute("image", getImage());
            request.setAttribute("displayStr1", "٩(๑^o^๑)۶");
            request.setAttribute("displayStr2", "（＾Ｏ＾），欢迎加入我们，激动~~");
            request.setAttribute("displayStr3", "注册成功，正在进入登录页面！");
            dispatcher = request.getRequestDispatcher("/skip.jsp");
        	dispatcher.forward(request, response);
        }else{
            request.setAttribute("skipUrl", "http://" + baseIP + "/software/register");
            request.setAttribute("image", getImage());
            request.setAttribute("displayStr1", "˚‧º·(˚ ˃̣̣̥᷄⌓˂̣̣̥᷅ )‧º·˚");
            request.setAttribute("displayStr2", "(╥﹏╥)，很遗憾，没有注册成功！！<br />请检查一下用户名和用户密码");
            request.setAttribute("displayStr3", "注册失败，正在返回注册页面！");
            dispatcher = request.getRequestDispatcher("skip.jsp");
        	dispatcher.forward(request, response);
        }
    }

}