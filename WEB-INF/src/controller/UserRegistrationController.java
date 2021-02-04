
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserRegistrationController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			UserRegistrationService userRegistration = new UserRegistrationService(request, response);
        userRegistration.judge();
		}catch(Exception e){}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}