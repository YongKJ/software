
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

public class LinuxService {
	
	public LinuxDao lDao;
	public List<Linux> linuxList;
	public PrintWriter out;
    public HttpServletRequest request;
	public HttpServletResponse response;
    public int[] linuxFileList;
	
	public LinuxService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		linuxList = new ArrayList<>();
        this.request = request;
		this.response = response;
		lDao = new LinuxDao();
	}
	
	public LinuxService() {
		linuxList = new ArrayList<>();
		lDao = new LinuxDao();
	}
	
	public List<Linux> getLinux() throws Exception {
		return lDao.getLinux();
	}
	
	public void addLinux(Linux linux) throws Exception {
        lDao.addLinux(linux);
	}
	
	public void modLinux(Linux linux) throws Exception {
		lDao.modLinux(linux);
	}
	
	public void delLinux(Linux linux) throws Exception {
		lDao.delLinux(linux);
	}
	
	public void readFile() throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/LinuxSoftware.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
        List<String> results = new ArrayList<>();
        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {
            results.add(lineTxt);
        }
        linuxFileList = new int[results.size()];
        for(int i = 0; i < results.size(); i++) {
            linuxFileList[i] = Integer.parseInt(results.get(i));
        }
        br.close();
    }
    
    public void addLinuxFromFile() throws Exception {
        readFile();
        for(int i = linuxFileList.length - 1; i >= 0 ; i--) {
            Linux linux = new Linux();
            linux.setLinux(linuxFileList[i]);
            addLinux(linux);
        }
    }
	
	public void test() throws Exception {
		response.setCharacterEncoding("gbk");
        out = response.getWriter();
        addLinuxFromFile();
        out.write("Linux文章添加到数据库成功！");
	}
	
}