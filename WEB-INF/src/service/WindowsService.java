
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

public class WindowsService {
	
	public WindowsDao wDao;
	public List<Windows> windowsList;
	public PrintWriter out;
    public HttpServletRequest request;
	public HttpServletResponse response;
    public int[] windowsFileList;
	
	public WindowsService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		windowsList = new ArrayList<>();
		this.response = response;
        this.request = request;
		wDao = new WindowsDao();
	}
	
	public WindowsService() {
		windowsList = new ArrayList<>();
		wDao = new WindowsDao();
	}
	
	public List<Windows> getWindows() throws Exception {
		return wDao.getWindows();
	}
	
	public void addWindows(Windows windows) throws Exception {
        wDao.addWindows(windows);
	}
	
	public void modWindows(Windows windows) throws Exception {
		wDao.modWindows(windows);
	}
	
	public void delWindows(Windows windows) throws Exception {
		wDao.delWindows(windows);
	}
    
    public void readFile() throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/WindowsSoftware.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
        List<String> results = new ArrayList<>();
        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {
            results.add(lineTxt);
        }
        windowsFileList = new int[results.size()];
        for(int i = 0; i < results.size(); i++) {
            windowsFileList[i] = Integer.parseInt(results.get(i));
        }
        br.close();
    }
    
    public void addWindowsFromFile() throws Exception {
        readFile();
        for(int i = windowsFileList.length - 1; i >= 0 ; i--) {
            Windows windows = new Windows();
            windows.setWindows(windowsFileList[i]);
            addWindows(windows);
        }
    }
	
	public void test() throws Exception {
		response.setCharacterEncoding("gbk");
        out = response.getWriter();
        addWindowsFromFile();
        out.write("Windows文章添加到数据库成功！");
	}
	
}