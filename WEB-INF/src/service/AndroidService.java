
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

public class AndroidService {
	
	public AndroidDao aDao;
	public List<Android> androidList;
	public PrintWriter out;
    public HttpServletRequest request;
	public HttpServletResponse response;
    public int[] androidFileList;
	
	public AndroidService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		androidList = new ArrayList<>();
        this.request = request;
		this.response = response;
		aDao = new AndroidDao();
	}
	
	public AndroidService() {
		androidList = new ArrayList<>();
		aDao = new AndroidDao();
	}
	
	public List<Android> getAndroid() throws Exception {
		return aDao.getAndroid();
	}
	
	public void addAndroid(Android android) throws Exception {
        aDao.addAndroid(android);
	}
	
	public void modAndroid(Android android) throws Exception {
		aDao.modAndroid(android);
	}
	
	public void delAndroid(Android android) throws Exception {
		aDao.delAndroid(android);
	}
	
	public void readFile() throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/AndroidSoftware.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
        List<String> results = new ArrayList<>();
        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {
            results.add(lineTxt);
        }
        androidFileList = new int[results.size()];
        for(int i = 0; i < results.size(); i++) {
            androidFileList[i] = Integer.parseInt(results.get(i));
        }
        br.close();
    }
    
    public void addAndroidFromFile() throws Exception {
        readFile();
        for(int i = androidFileList.length - 1; i >= 0 ; i--) {
            Android android = new Android();
            android.setAndroid(androidFileList[i]);
            addAndroid(android);
        }
    }
	
	public void test() throws Exception {
		response.setCharacterEncoding("gbk");
        out = response.getWriter();
        addAndroidFromFile();
        out.write("Android文章添加到数据库成功！");
	}
	
}