import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HomePageController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			HomePageService homePage = new HomePageService(request, response);
            homePage.judge();
		}catch(Exception e){}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}