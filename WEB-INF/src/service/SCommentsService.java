
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

public class SCommentsService {
	
	public SCommentsDao scDao;
	public List<SComments> scommentsList;
	public PrintWriter out;
    public HttpServletRequest request;
	public HttpServletResponse response;
    public int[][] scommentsFileList;
	
	public SCommentsService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		scommentsList = new ArrayList<>();
        this.request = request;
		this.response = response;
		scDao = new SCommentsDao();
	}
	
	public SCommentsService() {
		scommentsList = new ArrayList<>();
		scDao = new SCommentsDao();
	}
	
	public List<SComments> getSComments() throws Exception {
		return scDao.getSComments();
	}
	
	public void addSComments(SComments scomments) throws Exception {
        scDao.addSComments(scomments);
	}
	
	public void modSComments(SComments scomments) throws Exception {
		scDao.modSComments(scomments);
	}
	
	public void delSCommentsByCommentId(SComments scomments) throws Exception {
		scDao.delSCommentsByCommentId(scomments);
	}
	
	public void delSCommentsByUserId(SComments scomments) throws Exception {
		scDao.delSCommentsByUserId(scomments);
	}
	
	public void delSCommentsBySoftwareId(SComments scomments) throws Exception {
		scDao.delSCommentsBySoftwareId(scomments);
	}
	
	public void readFile() throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/SoftwareUserCommentTable.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
        List<String> results = new ArrayList<>();
        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {
            results.add(lineTxt);
        }
        writeToLog(results.size() + "  **********************");
        scommentsFileList = new int[results.size()][3];
        for(int i = 0; i < results.size(); i++) {
            
            writeToLog(results.get(i));
            
            String[] result = results.get(i).split(" ");
            scommentsFileList[i][0] = Integer.parseInt(result[0]);
            scommentsFileList[i][1] = Integer.parseInt(result[1]);
            scommentsFileList[i][2] = Integer.parseInt(result[2]);
            
        }
        br.close();
    }
    
    public void addSCommentsFromFile() throws Exception {
        readFile();
//         writeToLog("----------------------------------");
        for(int i = 0; i < scommentsFileList.length; i++) {
            SComments scomments = new SComments();
            scomments.setSoftwareId(scommentsFileList[i][0]);
            scomments.setUserId(scommentsFileList[i][1]);
            scomments.setCommentId(scommentsFileList[i][2]);
            
            writeToLog(scomments.getSoftwareId() + scomments.getSoftwareId() + scomments.getSoftwareId());
//             writeToLog("111111111111111111111111111");
            addSComments(scomments);
//             writeToLog("222222222222222222222222222");
        }
    }
    
    public void writeToLog(String i) throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/Log.txt";
        FileWriter writeFile = new FileWriter(fileName, true);
        BufferedWriter writer = new BufferedWriter(writeFile);
        writer.write(i + "\n");
        
        writer.flush();
        writeFile.close();
    }
    
    public void writeToLog(int i) throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/Log.txt";
        FileWriter writeFile = new FileWriter(fileName, true);
        BufferedWriter writer = new BufferedWriter(writeFile);
        writer.write(i + "\n");
        
        writer.flush();
        writeFile.close();
    }
	
	public void test() throws Exception {
		response.setCharacterEncoding("gbk");
        out = response.getWriter();
        addSCommentsFromFile();
        out.write("SComments文章添加到数据库成功！");
	}
	
}