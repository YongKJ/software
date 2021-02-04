
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ContentDisplayService{

    public HttpServletRequest request;
    public HttpServletResponse response;
    public UsersService uService;
    public SoftwaresService sService;
    public CommentsService cService;
    public SCommentsService scService;
    public WindowsService wService;
    public AndroidService aService;
    public LinuxService lService;
    public List<SComments> scommentsList;
    public List<Users> usersList;
    public List<Softwares> softwaresList;
    public List<Comments> commentsList;
    public List<Windows> windowsList;
    public List<Android> androidList;
    public List<Linux> linuxList;
    public String userId;
    public String softwareId;
    public String commentId;
    public String searchStr;
	public String chooseClassification;
	public int[] classificationSoftwareList;
    public int[][] SoftwareUserCommentTable;
	public int classificationSoftwareListSize;
    public int SoftwareUserCommentTableSize;
    public RequestDispatcher dispatcher;
    
    public ContentDisplayService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        this.request = request;
        this.response = response;
        uService = new UsersService();
        sService = new SoftwaresService();
        cService = new CommentsService();
        scService = new SCommentsService();
        wService = new WindowsService();
        aService = new AndroidService();
        lService = new LinuxService();
        usersList = new ArrayList<>();
        softwaresList = new ArrayList<>();
        commentsList = new ArrayList<>();
        windowsList = wService.getWindows();
        androidList = aService.getAndroid();
        linuxList = lService.getLinux();
        userId = "";
        softwareId = "";
        commentId = "";
        searchStr = "";
		chooseClassification = "";
		classificationSoftwareListSize = -1;
    }
    
    public String getUserName(int id) throws Exception {
        usersList = uService.getUsers();
        for(int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).getuId() == id) {
                return usersList.get(i).getuName();
            }
        }
        return "#";
    }
    
    public String getUserNameDisplayStr(String name, String link1, String choose1, String link2, String choose2) {
        return "<div class=\"nav-link dropdown-toggle\" data-toggle=\"dropdown\"><strong>" + name + "</strong></div><div style=\"min-width:100px;\" class=\"dropdown-menu position-absolute text-center\"><a class=\"dropdown-item\" href=\"" + link1 + "\">" + choose1 + "</a><div class=\"dropdown-divider\"></div><a class=\"dropdown-item\" href=\"" + link2 + "\">" + choose2 + "</a></div>";
    }
    
    public String getTheUserName(String userId) throws Exception {
        String userNameStr = "";
        if(userId == null || userId.equals("-1")) {
            request.setAttribute("userId", -1);
            request.setAttribute("theUserName", "游客");
            userNameStr += getUserNameDisplayStr("游客", "register", "注册", "login", "登录");
        }else{
            int id = Integer.parseInt(userId);
            request.setAttribute("userId", id);
            request.setAttribute("theUserName", getUser(Integer.parseInt(userId)).getuName());
            userNameStr += getUserNameDisplayStr(getUserName(id), ("home?userId=" + id), "首页", "home", "退出");
        }
        return userNameStr;
    }
    
    public String getSoftwareDateStr(String Date) throws Exception {
        String[] date = Date.split(" ");
        String[] year = date[0].split("-");
        return year[1] + "月&nbsp;" + year[2] + ",&nbsp;" + year[0];
    }
    
    public void setPrevSoftware(String softwareImage, String softwareTitle, int softwareId) throws Exception {
        if(softwareTitle.length() > 20) {
            softwareTitle = softwareTitle.substring(0,20) + "...";
        }
        String softwareImageStr = "<img src=\"img/" + softwareImage + "\" class=\"img-fluid rounded\">";
        String softwareTitleStr = "<a href=\"content?userId=" + (this.userId == null ? "-1" : this.userId) + (chooseClassification == null ? "" : "&chooseClassification=" + chooseClassification) + "&softwareId=" + softwareId + "\">上一篇</a><br /><br /><strong> " + softwareTitle + "</strong>";
        request.setAttribute("prevSoftwareImage", softwareImageStr);
        request.setAttribute("prevSoftwareTitle", softwareTitleStr);
    }
    
    public void setNextSoftware(String softwareImage, String softwareTitle, int softwareId) throws Exception {
        if(softwareTitle.length() > 20) {
            softwareTitle = softwareTitle.substring(0,20) + "...";
        }
        String softwareImageStr = "<img src=\"img/" + softwareImage + "\" class=\"img-fluid rounded\">";
        String softwareTitleStr = "<a href=\"content?userId=" + (this.userId == null ? "-1" : this.userId) + (chooseClassification == null ? "" : "&chooseClassification=" + chooseClassification) + "&softwareId=" + softwareId + "\">下一篇</a><br /><br /><strong> " + softwareTitle + "</strong>";
        request.setAttribute("nextSoftwareImage", softwareImageStr);
        request.setAttribute("nextSoftwareTitle", softwareTitleStr);
    }
	
	public String getSoftwaresContentBR(String content) {
		if(content.indexOf("<br />") != -1 || content.indexOf("<br>") != -1) {
			return content;
		}
		return content.replace("\n","\n<br>");
	}
    
    public String getTheLink(String content) {
    	//if(content.indexOf("https://") != -1 && content.indexOf("</a>") == -1) {
            //String[] contents = content.split("https://");
            //return getSoftwaresContentBR(contents[0] + "<a href=\"https://" + contents[1] + "\" target=\"_blank\">" + "https://" + contents[1] + "</a>");
        //}else if(content.indexOf("http://") != -1 && content.indexOf("</a>") == -1){
			//String[] contents = content.split("http://");
            //return getSoftwaresContentBR(contents[0] + "<a href=\"http://" + contents[1] + "\" target=\"_blank\">" + "http://" + contents[1] + "</a>");
		//}
		String regex = "((https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|])";
		String replacement = "<a href=\"$1\" target=\"_blank\">$1</a>";
		if(content.indexOf("</a>") == -1) {
			content = content.replaceAll(regex, replacement);
		}
        return getSoftwaresContentBR(content);
    }
    
    public void getsoftwareContentStr() throws Exception {
        softwaresList = sService.getSoftwares();
		if(chooseClassification != null) {
			softwaresList = getTheClassificationSoftwareList(softwaresList);
		}
        int id = Integer.parseInt(softwareId);
        for(int i = 0; i < softwaresList.size() ; i++) {
            if(softwaresList.get(i).getsId() == id) {
                request.setAttribute("softwareTitle", softwaresList.get(i).getsTitle());
                request.setAttribute("softwareContent", getTheLink(softwaresList.get(i).getsContent()));
                request.setAttribute("softwareAuthor", getUserName(softwaresList.get(i).getsAuthor()).toUpperCase());
                request.setAttribute("softwareDate", getSoftwareDateStr(softwaresList.get(i).getsDate()));
                request.setAttribute("softwareImage", "img/" + softwaresList.get(i).getsImage());
                if(i == 0) {
                    setNextSoftware(softwaresList.get(i + 1).getsImage(), softwaresList.get(i + 1).getsTitle(), softwaresList.get(i + 1).getsId());
                    break;
                }else if(i == softwaresList.size() - 1) {
                    setPrevSoftware(softwaresList.get(i - 1).getsImage(), softwaresList.get(i - 1).getsTitle(), softwaresList.get(i - 1).getsId());
                    break;
                }else{
                    setNextSoftware(softwaresList.get(i + 1).getsImage(), softwaresList.get(i + 1).getsTitle(), softwaresList.get(i + 1).getsId());
                    setPrevSoftware(softwaresList.get(i - 1).getsImage(), softwaresList.get(i - 1).getsTitle(), softwaresList.get(i - 1).getsId());
                    break;
                }
            }
        }
    }
	
	public List<Softwares> getTheClassificationSoftwareList(List<Softwares> softwaresList) throws Exception {
// 		getClassificationSoftwareList();
		List<Softwares> theClassificationSoftwareList = new ArrayList<>();
// 		for(int i = 0; i < softwaresList.size(); i++) {
// 			for(int j = 0; j < classificationSoftwareListSize; j++) {
// 				if(softwaresList.get(i).getsId() == classificationSoftwareList[j]) {
// 					theClassificationSoftwareList.add(softwaresList.get(i));
// 					break;
// 				}
// 			}
// 		}
        if(chooseClassification.equals("Windows")) {
            for(int i = 0; i < softwaresList.size(); i++) {
                for(int j = 0; j < windowsList.size(); j++) {
                    if(softwaresList.get(i).getsId() == windowsList.get(j).getWindows()) {
                        theClassificationSoftwareList.add(softwaresList.get(i));
                        break;
                    }
                }
            }
        }else if(chooseClassification.equals("Android")) {
            for(int i = 0; i < softwaresList.size(); i++) {
                for(int j = 0; j < androidList.size(); j++) {
                    if(softwaresList.get(i).getsId() == androidList.get(j).getAndroid()) {
                        theClassificationSoftwareList.add(softwaresList.get(i));
                        break;
                    }
                }
            }
        }else if(chooseClassification.equals("Linux")) {
            for(int i = 0; i < softwaresList.size(); i++) {
                for(int j = 0; j < linuxList.size(); j++) {
                    if(softwaresList.get(i).getsId() == linuxList.get(j).getLinux()) {
                        theClassificationSoftwareList.add(softwaresList.get(i));
                        break;
                    }
                }
            }
        }
		return theClassificationSoftwareList;
	}
	
	public void getClassificationSoftwareList() throws Exception {
		String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/" + chooseClassification + "Software.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
		List<String> results = new ArrayList<>();
		String lineTxt = null;
		while ((lineTxt = br.readLine()) != null) {
			results.add(lineTxt);
		}
		classificationSoftwareListSize = results.size();
		classificationSoftwareList = new int[classificationSoftwareListSize];
		for(int i = 0; i < results.size(); i++) {
			classificationSoftwareList[i] = Integer.parseInt(results.get(i));
		}
		br.close();
	}
    
    public void readFromSoftwareUserCommentTable() throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/SoftwareUserCommentTable.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
        List<String> results = new ArrayList<>();
        String lineTxt = null;
        while ((lineTxt = br.readLine()) != null) {
            results.add(lineTxt);
        }
        SoftwareUserCommentTableSize = results.size();
        SoftwareUserCommentTable = new int[SoftwareUserCommentTableSize][3];
        for(int i = 0; i < results.size(); i++) {
            String[] result = results.get(i).split(" ");
            SoftwareUserCommentTable[i][0] = Integer.parseInt(result[0]);
            SoftwareUserCommentTable[i][1] = Integer.parseInt(result[1]);
            SoftwareUserCommentTable[i][2] = Integer.parseInt(result[2]);
        }
        br.close();
    }
    
    public void writeToLog() throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/Log.txt";
        FileWriter writeFile = new FileWriter(fileName, true);
        BufferedWriter writer = new BufferedWriter(writeFile);
        
        for(int i = 0; i < SoftwareUserCommentTableSize; i++) {
            int softwareId = SoftwareUserCommentTable[i][0];
            int userId = SoftwareUserCommentTable[i][1];
            int commentId = SoftwareUserCommentTable[i][2];
            writer.write(softwareId + " " + userId + " " + commentId + "\n");
        }
        
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
    
    public void writeToLog(boolean i) throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/Log.txt";
        FileWriter writeFile = new FileWriter(fileName, true);
        BufferedWriter writer = new BufferedWriter(writeFile);
        writer.write(i + "\n");
        
        writer.flush();
        writeFile.close();
    }
    
    public void writeToLog(String i) throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/Log.txt";
        FileWriter writeFile = new FileWriter(fileName, true);
        BufferedWriter writer = new BufferedWriter(writeFile);
        writer.write(i + "\n");
        
        writer.flush();
        writeFile.close();
    }
    
    public void updateToSoftwareUserCommentTable() throws Exception {
        readFromSoftwareUserCommentTable();
    	String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/SoftwareUserCommentTable.txt";
    	FileWriter writeFile = new FileWriter(fileName);
        BufferedWriter writer = new BufferedWriter(writeFile);
        commentsList = cService.getComments();
		for(int i = 0; i < SoftwareUserCommentTableSize; i++) {
            int softwareId = SoftwareUserCommentTable[i][0];
            int userId = SoftwareUserCommentTable[i][1];
            int commentId = SoftwareUserCommentTable[i][2];
            boolean flag = false;
            for(int j = 0; j < commentsList.size(); j++) {
				if(commentId == commentsList.get(j).getcId()) {
					flag = true;
					break;
				}
			}
			if(flag) {
            	writer.write(softwareId + " " + userId + " " + commentId + "\n");
            }else{
				SoftwareUserCommentTable[i][2] = -1;
			}
        }
        writer.flush();
        writeFile.close();
	}
    
    public void writeToSoftwareUserCommentTable(int softwareId, int userId, int commentId) throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/SoftwareUserCommentTable.txt";
        FileWriter writeFile = new FileWriter(fileName, true);
        BufferedWriter writer = new BufferedWriter(writeFile);
        boolean flag = true;
        for(int i = 0; i < SoftwareUserCommentTableSize; i++) {
            int softwareIdInt = SoftwareUserCommentTable[i][0];
            int userIdInt = SoftwareUserCommentTable[i][1];
            int commentIdInt = SoftwareUserCommentTable[i][2];
            if(softwareId == softwareIdInt && userId == userIdInt && commentId == commentIdInt) {
            	flag = false;
				break;		
        	}
        }
		if(flag) {
			writer.write(softwareId + " " + userId + " " + commentId + "\n");
		}      
        writer.flush();
        writeFile.close();
    }
    
    public String getcommentDisplayStr(Users user, Comments comment) throws Exception {
        return "<div class=\"row p-3\"><div class=\"col-md-1 p-0\"><img src=\"img/user.png\" class=\"img-fluid\" style=\"border-radius:50%;\" width=\"60\"></div><div class=\"col-md-11 pr-0\"><div class=\"container-fluid p-0 m-0\"><strong>" + user.getuName() + "</strong><span class=\"float-right pt-1\" style=\"font-size:12px;\">" + getCommentDate(comment.getcDate()) + "</span></div><br /><span>" + comment.getcContent() + "</span></div></div><div class=\"dropdown-divider\"></div>";
    }
    
    public String getcommentDisplayStr(String userName, String commentContent) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String commentDate = df.format(new Date());
//         return "<div class=\"row p-3\"><div class=\"col-md-1 p-1\"><img src=\"img/user.png\" class=\"img-fluid\" style=\"border-radius:50%;\" width=\"60\"></div><div class=\"col-md-7 pl-2\"><strong>" + userName + "</strong><br /><br />" + commentContent + "</div><div class=\"col-md-4 pt-2 text-right\" style=\"font-size:12px;\">" + getCommentDate(commentDate) + ".0</div></div><div class=\"dropdown-divider\"></div>";
		return "<div class=\"row p-3\"><div class=\"col-md-1 p-0\"><img src=\"img/user.png\" class=\"img-fluid\" style=\"border-radius:50%;\" width=\"60\"></div><div class=\"col-md-11 pr-0\"><div class=\"container-fluid p-0 m-0\"><strong>" + userName + "</strong><span class=\"float-right pt-1\" style=\"font-size:12px;\">" + getCommentDate(commentDate) + "</span></div><br /><span>" + commentContent + "</span></div></div><div class=\"dropdown-divider\"></div>";
    }
    
    public String getCommentDate(String commentDate) throws Exception {
        String[] date = commentDate.split(" ");
        String[] year = date[0].split("-");
        return year[1] + "月&nbsp;" + year[2] + ",&nbsp;" + year[0] + "&nbsp;&#47;&nbsp;" + date[1];
    }
    
    public int getUserId(String userName) throws Exception {
        int id = -1;
        usersList = uService.getUsers();
        for( int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).getuName().equals(userName)) {
                return usersList.get(i).getuId();
            }
        }
        return id;
        
    }
    
    public Users getUser(int userId) throws Exception {
        Users user = new Users();
        usersList = uService.getUsers();
        for( int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).getuId() == userId) {
                return usersList.get(i);
            }
        }
        return user;
    }
    
    public Comments getComment(int commentId) throws Exception {
        Comments comment = new Comments();
        commentsList = cService.getComments();
        for(int i = 0; i < commentsList.size(); i++) {
            if(commentsList.get(i).getcId() == commentId) {
                return commentsList.get(i);
            }
        }
        return comment;
    }
    
    public int getCommentId(String commentContent) throws Exception {
        int id = -1;
        commentsList = cService.getComments();
        if(commentsList.get(commentsList.size() - 1).getcContent().equals(commentContent)) {
            return commentsList.get(commentsList.size() - 1).getcId();
        }
        return id;
    }
    
    public int getcommentDisplay(int commentId) throws Exception {
        int id = -1;
        commentsList = cService.getComments();
        for(int i = 0; i < commentsList.size(); i++) {
            if(commentsList.get(i).getcId() == commentId) {
                return commentsList.get(i).getcDisplay();
            }
        }
        return id;
    }
    
    public int getSecondTime(String Date) throws Exception {
        String[] date = Date.split(" ");
        String[] hour = date[1].split(":");
        String[] sec = hour[2].split(".0");//String[] sec = hour[2].split(".");是错误的，用“.”无法切割字符串
        int mins = Integer.parseInt(hour[1]);
        int secs = Integer.parseInt(sec[0]);
        return mins * 60 + secs;
    }
    
    public boolean judgeSameHour(String Date1, String Date2) {
        String[] date1 = Date1.split(" ");
        String[] hour1 = date1[1].split(":");
        String[] date2 = Date2.split(" ");
        String[] hour2 = date2[1].split(":");
        if(date1[0].equals(date2[0]) && hour1[0].equals(hour2[0])) {
            return true;
        }
        return false;
    }
    
    public boolean judgeTheCommentTime() throws Exception {
        Comments comment = new Comments();
        int softwareIdInt = Integer.parseInt(softwareId);
//         int theLastsoftwareId = SoftwareUserCommentTable[SoftwareUserCommentTableSize - 1][0];
//         int commentId = SoftwareUserCommentTable[SoftwareUserCommentTableSize - 1][2];
        int theLastsoftwareId = scommentsList.get(scommentsList.size() - 1).getSoftwareId();
        int commentId = scommentsList.get(scommentsList.size() - 1).getCommentId();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = df.format(new Date());
        nowDate += ".0";
        for(int i = 0; i < commentsList.size(); i++) {
            if(commentsList.get(i).getcId() == commentId) {
                comment = commentsList.get(i);
            }
        }
//         writeToLog("----------------------------");
//         writeToLog(nowDate);
//         writeToLog(getSecondTime(nowDate));
//         writeToLog(comment.getcDate());
//         writeToLog(getSecondTime(comment.getcDate()));
//         writeToLog(judgeSameHour(nowDate, comment.getcDate()));
//         writeToLog(getSecondTime(nowDate) - getSecondTime(comment.getcDate()));
//         writeToLog("++++++++++++++++++++++++++++++");
        if(softwareIdInt != theLastsoftwareId || !judgeSameHour(nowDate, comment.getcDate()) || getSecondTime(nowDate) - getSecondTime(comment.getcDate()) > 15 * 60) {
            return true;
        }
        return false;
    }
    
    public String getcommentStr() throws Exception {
//     	updateToSoftwareUserCommentTable();
//         readFromSoftwareUserCommentTable();
        scommentsList = scService.getSComments();
        int commentsSum = 0;
        String commentDisplayStr = "";
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String commentContent = request.getParameter("commentContent");
        commentsList = cService.getComments();
		int softwareIdInt = Integer.parseInt(this.softwareId);
		for(int i = 0; i < commentsList.size(); i++) {
			for(int j = 0; j < scommentsList.size(); j++) {
				int softwareId = scommentsList.get(j).getSoftwareId();
            	int userId = scommentsList.get(j).getUserId();
            	int commentId = scommentsList.get(j).getCommentId();
				if(softwareIdInt == softwareId && commentsList.get(i).getcId() == commentId && commentsList.get(i).getcDisplay() == 1) {
					commentDisplayStr += getcommentDisplayStr(getUser(userId), getComment(commentId));
                    commentsSum++;
                    break;
				}
			}
		}   
        
        if(userName != null && userEmail != null && commentContent != null) {
            if(!userName.equals("游客") && getUserId(userName) != -1 && judgeTheCommentTime()) {
                Comments comment = new Comments();
                comment.setcAuthor(getUserId(userName));
                comment.setcEmail(userEmail);
                comment.setcContent(commentContent);
                comment.setcDisplay(0);
                cService.addComment(comment);
//                 writeToSoftwareUserCommentTable(Integer.parseInt(softwareId), getUserId(userName), getCommentId(commentContent));
                SComments scomments = new SComments();
                scomments.setSoftwareId(Integer.parseInt(softwareId));
                scomments.setUserId(getUserId(userName));
                scomments.setCommentId(getCommentId(commentContent));
                scService.addSComments(scomments);
            }
            commentDisplayStr += getcommentDisplayStr(userName, commentContent);
            commentsSum++;
        }
        request.setAttribute("commentsSum", commentsSum);
        return commentDisplayStr;
    }
    
    public void judge() throws Exception {
        userId = request.getParameter("userId");
        softwareId = request.getParameter("softwareId");
        commentId = request.getParameter("commentId");
		chooseClassification = request.getParameter("chooseClassification");
		
		if(chooseClassification == null) {
			request.setAttribute("browseChoose", "分类浏览");
			request.setAttribute("classify", "default");
		}else{
			request.setAttribute("browseChoose", chooseClassification + "软件");
			request.setAttribute("classify", chooseClassification);
		}
        
        if(softwareId != null) {
            request.setAttribute("softwareId", softwareId);
        }
        
        request.setAttribute("userName", getTheUserName(userId));
        getsoftwareContentStr();
        request.setAttribute("commentStr", getcommentStr());
        
        dispatcher = request.getRequestDispatcher("/content.jsp");
        dispatcher.forward(request, response);
    }

}