import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserLoginController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			UserLoginService userLogin = new UserLoginService(request, response);
            userLogin.judge();
		}catch(Exception e){}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}