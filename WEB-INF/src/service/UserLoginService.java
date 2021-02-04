
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class UserLoginService{

    public HttpServletRequest request;
    public HttpServletResponse response;
    public UsersService uService;
    public List<Users> usersList;
    public String userName;
    public String userPasswd;
    public String[] images;
	public String baseIP;
    public RequestDispatcher dispatcher;
    
    public UserLoginService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			if(usersList.get(i).getuName().equals(userName) && usersList.get(i).getuPasswd().equals(userPasswd)) {
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
        userName = request.getParameter("usenameText");
        userPasswd = request.getParameter("passwordText");
        int userId = -1;
        if(userName != null && userPasswd != null) {
			userId = getUserId();
		}else{
			dispatcher = request.getRequestDispatcher("/login.jsp");
        	dispatcher.forward(request, response);
//             response.sendRedirect("login.jsp");
        	return;
		}
		if(userId == 1) {
            request.setAttribute("skipUrl", "http://" + baseIP + "/software/manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0");
            request.setAttribute("image", getImage());
            request.setAttribute("displayStr1", "‼(•'╻'• )꒳ᵒ꒳ᵎᵎᵎ");
            request.setAttribute("displayStr2", "(⊙o⊙)，wow，后台管理员来了！！！<br />欢迎，欢迎，热烈欢迎~~~");
            request.setAttribute("displayStr3", "后台管理员登录成功！<br />正在进入后台管理。");
            dispatcher = request.getRequestDispatcher("/skip.jsp");
        	dispatcher.forward(request, response);
//             response.sendRedirect("skip.jsp");
        	return;
		}else if(userId == -1){
            request.setAttribute("skipUrl", "http://" + baseIP + "/software/login");
            request.setAttribute("image", getImage());
            request.setAttribute("displayStr1", "( •̥́ ˍ •̀ू )");
            request.setAttribute("displayStr2", "T_T，很遗憾，没有登录成功！！<br />请检查一下用户名和用户密码");
            request.setAttribute("displayStr3", "登录失败，正在返回登录页面！");
            dispatcher = request.getRequestDispatcher("skip.jsp");
        	dispatcher.forward(request, response);
		}else {
			request.setAttribute("skipUrl", "http://" + baseIP + "/software/home?userId=" + userId);
            request.setAttribute("image", getImage());
            request.setAttribute("displayStr1", "(￣y▽￣)~*捂嘴偷笑");
            request.setAttribute("displayStr2", "O(∩_∩)O，哈哈，恭喜，恭喜~~");
            request.setAttribute("displayStr3", "登录成功，正在进入首页！");
            dispatcher = request.getRequestDispatcher("/skip.jsp");
        	dispatcher.forward(request, response);
//             response.sendRedirect("skip.jsp");
        }
    }

}