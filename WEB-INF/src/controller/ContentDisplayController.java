import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ContentDisplayController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			ContentDisplayService contentDisplay = new ContentDisplayService(request, response);
        contentDisplay.judge();
		}catch(Exception e){}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}