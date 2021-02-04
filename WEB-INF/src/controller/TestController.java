
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class TestController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
// 			UsersService uService = new UsersService(response);
// 			uService.test();
// 			SoftwaresService sService = new SoftwaresService(response);
// 			sService.test();
// 			CommentsService cService = new CommentsService(response);
// 			cService.test();
//             AndroidService aService = new AndroidService(request, response);
//             aService.test();
//             WindowsService wService = new WindowsService(request, response);
//             wService.test();
//             LinuxService lService = new LinuxService(request, response);
//             lService.test();
//             SCommentsService scService = new SCommentsService(request, response);
//             scService.test();
		}catch(Exception e){}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}