
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.lang.Runtime;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.io.WireFeedOutput;

public class BackgroundManagementService{

    public HttpServletRequest request;
    public HttpServletResponse response;
    public UsersService uService;
    public SoftwaresService sService;
    public CommentsService cService;
    public WindowsService wService;
    public AndroidService aService;
    public SCommentsService scService;
    public LinuxService lService;
    public List<Users> usersList;
    public List<Softwares> softwaresList;
    public List<Comments> commentsList;
    public List<Windows> windowsList;
    public List<Android> androidList;
    public List<Linux> linuxList;
    public Users user;
    public Softwares software;
    public Comments comment;
    public String tableName;
    public String tableOperation;
    public String[] images;
	public String baseIP;
	public String pageNum;
	public String searchStr;
	public int start;
	public int end;
	public int classificationSoftwareListSize;
	public int[] classificationSoftwareList;
    public RequestDispatcher dispatcher;
    
    public BackgroundManagementService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        uService = new UsersService();
        sService = new SoftwaresService();
        cService = new CommentsService();
        wService = new WindowsService();
        aService = new AndroidService();
        lService = new LinuxService();
        scService = new SCommentsService();
        usersList = new ArrayList<>();
        softwaresList = new ArrayList<>();
        cService = new CommentsService();
        user = new Users();
        software = new Softwares();
        comment = new Comments();
        tableName = "";
        tableOperation = "";
		baseIP = "software.yongkj.cn";
		pageNum = "";
		searchStr = "";
		start = -1;
		end = -1;
		classificationSoftwareListSize = -1;
    }
    
    public String getAuthorName(int id) throws Exception {
        usersList = uService.getUsers();
        for(int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).getuId() == id){
                return usersList.get(i).getuName();
            }
        }
        return "#";
    }
    
    public String getDisplayStr(String head, String body) {
        return "<table class=\"table table-bordered\"><thead>" + head + "</thead><tbody>" + body + "</tbody></table>";
    }
    
    public List<Users> getReverseUsersList(List<Users> usersList) throws Exception {
        List<Users> reverseUsersList = new ArrayList<>();
        for(int i = usersList.size() - 1; i >= 0; i--) {
            reverseUsersList.add(usersList.get(i));
        }
        return reverseUsersList;
    }
    
    public String getUsersDisplayStr(List<Users> usersList, String operate) throws Exception {
// 		writeToLog("11111");
		request.setAttribute("pageNumStr", getPageNumStr(pageNum, tableName, tableOperation));
// 		writeToLog("22222");
        String head = "<tr><th>编号</th><th>名称</th><th>密码</th><th>注册时间</th><th>操作</th></tr>";
        String body = "";
		String pageNum = request.getParameter("pageNum");
		int pageNumInt = (pageNum != null ? Integer.parseInt(pageNum) : 1);
        usersList = getReverseUsersList(usersList);
		if(searchStr != null) {
			usersList = getUsersSearchList(usersList);
		}
		if(end == 0) {
			return "<div class=\"container-fluid text-center mt-4 pt-4\"><br /><br /><br /><br /><br /><br /><h3><strong>ㄟ( ▔, ▔ )ㄏ<br /><br /><br />找不到任何内容~~</strong></h3></div>";
		}
        for(int i = start; i < end; i++) {
            if(usersList.get(i).getuName().equals("admin")){
                body += "<tr><td style=\"vertical-align: middle;\">" + usersList.get(i).getuId() + "</td>";
                body += "<td style=\"vertical-align: middle;\">" + usersList.get(i).getuName() + "</td>";
                body += "<td style=\"vertical-align: middle;\">" + usersList.get(i).getuPasswd() + "</td>";
                body += "<td style=\"vertical-align: middle;\">" + usersList.get(i).getregistrationDate() + "</td>";
                body += "<td style=\"vertical-align: middle;\">无修改权限</td></tr>";
            }else{
                body += "<tr><td style=\"vertical-align: middle;\">" + usersList.get(i).getuId() + "</td>";
                if(operate != null && operate.equals("mod")) {
                    body += "<td style=\"vertical-align: middle;\"><input id=\"userName" + usersList.get(i).getuId() + "\" type=\"text\" class=\"form-control\" placeholder=\"" + usersList.get(i).getuName() + "\"></td>";
                    body += "<td style=\"vertical-align: middle;\"><input id=\"userPasswd" + usersList.get(i).getuId() + "\" type=\"text\" class=\"form-control\" placeholder=\"" + usersList.get(i).getuPasswd() + "\"></td>";
                }else{
                    body += "<td style=\"vertical-align: middle;\">" + usersList.get(i).getuName() + "</td>";
                    body += "<td style=\"vertical-align: middle;\">" + usersList.get(i).getuPasswd() + "</td>";
                }
                body += "<td style=\"vertical-align: middle;\">" + usersList.get(i).getregistrationDate() + "</td>";
                if(operate == null) {
                    body += "<td style=\"vertical-align: middle;\">未选择</td></tr>";
                }else if(operate.equals("mod")) {
                    body += "<td style=\"vertical-align: middle;\"><a href=\"#\" onclick=\"getUserInputValue(" + usersList.get(i).getuId() + "," + pageNumInt + ")\">修改</a></td></tr>";
                }else{
                    body += "<td style=\"vertical-align: middle;\"><a href=\"manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Users&tableOperation=del&pageNum=" + pageNumInt + "&id=" + usersList.get(i).getuId() + "\">删除</a></td></tr>";
                }
            }
        }
        return getDisplayStr(head, body);
    }
    
    public List<Softwares> getResverseSoftwaresList(List<Softwares> softwaresList) throws Exception {
        List<Softwares> reverseSoftwaresList = new ArrayList<>();
        for(int i = softwaresList.size() - 1; i >= 0; i--) {
            reverseSoftwaresList.add(softwaresList.get(i));
        }
        return reverseSoftwaresList;
    }
	
	public String getSoftwaresContentBR(String content) {
		if(content.indexOf("<br />") != -1 || content.indexOf("<br>") != -1) {
			return content;
		}
		return content.replace("\n","\n<br>");
	}
    
    public String getSoftwaresDisplayStr(List<Softwares> softwaresList, String operate) throws Exception {
		request.setAttribute("pageNumStr", getPageNumStr(pageNum, tableName, tableOperation));
        String head = "<tr><th>编号</th><th>作者</th><th>日期</th><th>标题</th><th>内容</th><th>图片</th><th>操作</th></tr>";
        String body = "";
		String pageNum = request.getParameter("pageNum");
		int pageNumInt = (pageNum != null ? Integer.parseInt(pageNum) : 1);
        if(operate.equals("add")) {
            body += "<tr><td style=\"vertical-align: middle;\">系统自动分配编号</td>";
            body += "<form id=\"addSoftware\" method=\"post\" action=\"manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Softwares&tableOperation=add\"><td style=\"vertical-align: middle;\"><textarea name=\"softwareAuthor\" class=\"form-control\" placeholder=\"请输入作者名称\">admin</textarea></td>";
            body += "<td style=\"vertical-align: middle;\">系统自动确定当前时间</td>";
            body += "<td style=\"vertical-align: middle;\"><textarea name=\"softwareTitle\" class=\"form-control\" placeholder=\"请输入文章标题\"></textarea></td>";
            body += "<td style=\"vertical-align: middle;\"><textarea name=\"softwareContent\" class=\"form-control\" placeholder=\"请输入文章内容\"></textarea></td>";
            body += "<td style=\"vertical-align: middle;\"><textarea name=\"softwareImage\" class=\"form-control\" placeholder=\"请输入图片文件名\"></textarea></td><input type=\"hidden\" name=\"pageNum\" value=\"" + pageNumInt + "\"></form>";
            body += "<td style=\"vertical-align: middle;\"><a href=\"#\"  onclick=\"getAddSoftwareValue()\">添加</a></td></tr>";
        }
        softwaresList = getResverseSoftwaresList(softwaresList);
		if(searchStr != null) {
			softwaresList = getSoftwaresSearchList(softwaresList);
		}
		if(end == 0) {
			return "<div class=\"container-fluid text-center mt-4 pt-4\"><br /><br /><br /><br /><br /><br /><h3><strong>ㄟ( ▔, ▔ )ㄏ<br /><br /><br />找不到任何内容~~</strong></h3></div>";
		}
        for(int i = start; i < end; i++) {
            body += "<tr><td style=\"vertical-align: middle;\">" + softwaresList.get(i).getsId() + "</td>";
            body += "<td style=\"vertical-align: middle;\">" + getAuthorName(softwaresList.get(i).getsAuthor()) + "</td>";
            body += "<td style=\"vertical-align: middle;\">" + softwaresList.get(i).getsDate() + "</td>";
            if(operate.equals("mod")) {
                body += "<form id=\"modSoftware" + softwaresList.get(i).getsId() + "\" method=\"post\" action=\"manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Softwares&tableOperation=mod\"><td style=\"vertical-align: middle;\"><textarea name=\"softwareTitle\" class=\"form-control\">" + softwaresList.get(i).getsTitle() + "</textarea></td>";
                body += "<td class=\"w-50\"><textarea name=\"softwareContent\" style=\"overflow: auto;vertical-align: middle;\" class=\"form-control\">" + softwaresList.get(i).getsContent() + "</textarea></td>";//此处的placeholder用单引号比较合理，解决a标签显示不正常问题
                body += "<td style=\"vertical-align: middle;\"><textarea name=\"softwareImage\" class=\"form-control\">" + softwaresList.get(i).getsImage() + "</textarea></td><input type=\"hidden\" name=\"pageNum\" value=\"" + pageNumInt + "\"><input type=\"hidden\" name=\"id\" value=\"" + softwaresList.get(i).getsId() + "\"></form>";
            }else{
                body += "<td style=\"vertical-align: middle;\">" + softwaresList.get(i).getsTitle() + "</td>";
                body += "<td style=\"vertical-align: middle;\">" + getSoftwaresContentBR(softwaresList.get(i).getsContent()) + "</td>";
                body += "<td style=\"vertical-align: middle;\">" + softwaresList.get(i).getsImage() + "</td>";
            }
            if (operate.equals("add")){
                body += "<td style=\"vertical-align: middle;\">暂无</td></tr>";
            }else if(operate.equals("mod")) {
                body += "<td style=\"vertical-align: middle;\"><a href=\"#\" onclick=\"getSoftwareVaule(" + softwaresList.get(i).getsId() + ")\">修改</a></td></tr>";
            }else{
                body += "<td style=\"vertical-align: middle;\"><a href=\"manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Softwares&tableOperation=del&pageNum=" + pageNumInt + "&id=" + softwaresList.get(i).getsId() + "\">删除</a></td></tr>";
            }
        }
        return getDisplayStr(head, body);
    }
    
    public List<Comments> getResverseCommentsList(List<Comments> commentsList) throws Exception {
        List<Comments> reverseCommentsList = new ArrayList<>();
        for(int i = commentsList.size() - 1; i >= 0; i--) {
            reverseCommentsList.add(commentsList.get(i));
        }
        return reverseCommentsList;
    }
    
    public String getCommentsDisplayStr(List<Comments> commentsList, String operate) throws Exception {
		request.setAttribute("pageNumStr", getPageNumStr(pageNum, tableName, tableOperation));
        String head = "<tr><th>编号</th><th>作者</th><th>电子邮件</th><th>日期</th><th>评论内容</th><th>已审核</th><th>操作</th></tr>";
        String body = "";
		String pageNum = request.getParameter("pageNum");
		int pageNumInt = (pageNum != null ? Integer.parseInt(pageNum) : 1);
        commentsList = getResverseCommentsList(commentsList);
		if(searchStr != null) {
			commentsList = getCommentsSearchList(commentsList);
		}
		if(end == 0) {
			return "<div class=\"container-fluid text-center mt-4 pt-4\"><br /><br /><br /><br /><br /><br /><h3><strong>ㄟ( ▔, ▔ )ㄏ<br /><br /><br />找不到任何内容~~</strong></h3></div>";
		}
        for(int i = start; i < end; i++) {
            body += "<tr><td style=\"vertical-align: middle;\">" + commentsList.get(i).getcId() + "</td>";
            body += "<td style=\"vertical-align: middle;\">" + getAuthorName(commentsList.get(i).getcAuthor()) + "</td>";
            body += "<td style=\"vertical-align: middle;\">" + commentsList.get(i).getcEmail() + "</td>";
            body += "<td style=\"vertical-align: middle;\">" + commentsList.get(i).getcDate() + "</td>";
            body += "<td style=\"vertical-align: middle;\">" + commentsList.get(i).getcContent() + "</td>";
            body += "<td style=\"vertical-align: middle;\">" + (commentsList.get(i).getcDisplay() == 1 ? "是" : "否") + "</td>";
            if(operate.equals("mod")) {
                if(commentsList.get(i).getcDisplay() == 0) {
                    body += "<td style=\"vertical-align: middle;\"><a class=\"card-link\" href=\"manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Comments&tableOperation=mod&pageNum=" + pageNumInt + "&id=" + commentsList.get(i).getcId() + "\">审核</a></td></tr>";
                }else{
                   body += "<td style=\"vertical-align: middle;\">暂无</td></tr>"; 
                }
            }else{
            	body += "<td style=\"vertical-align: middle;\"><a class=\"card-link\" href=\"manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Comments&tableOperation=del&pageNum=" + pageNumInt + "&id=" + commentsList.get(i).getcId() + "\">删除</a></td></tr>";
            }
        }
        return getDisplayStr(head, body);
    }
    
    public void writeToLog(Softwares software) throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/Log.txt";
        FileWriter writeFile = new FileWriter(fileName);
        BufferedWriter writer = new BufferedWriter(writeFile);
//         writer.write("userName：");
//         writer.write(request.getParameter("userName") + "\n\n");
//         writer.write("userPasswd：");
//         writer.write(request.getParameter("userPasswd") + "\n");
//         writer.write(user.getuId() + "\n");
//         writer.write(user.getuName() + "\n");
//         writer.write(user.getuPasswd() + "\n");
//         writer.write(user.getregistrationDate() + "\n");
        writer.write(software.getsAuthor() + "\n");
        writer.write(software.getsTitle() + "\n");
        writer.write(software.getsContent() + "\n");
        writer.write(software.getsImage() + "\n");
        
        writer.flush();
        writeFile.close();
    }
	
	public void writeToLog(String str) throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/Log.txt";
        FileWriter writeFile = new FileWriter(fileName, true);
        BufferedWriter writer = new BufferedWriter(writeFile);
		
		writer.write(str + "\n");
        
        writer.flush();
        writeFile.close();
    }
	
	public void writeToLog(int str) throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/Log.txt";
        FileWriter writeFile = new FileWriter(fileName, true);
        BufferedWriter writer = new BufferedWriter(writeFile);
		
		writer.write(str + "\n");
        
        writer.flush();
        writeFile.close();
    }
	
	public void writeToLog(boolean str) throws Exception {
        String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/Log.txt";
        FileWriter writeFile = new FileWriter(fileName, true);
        BufferedWriter writer = new BufferedWriter(writeFile);
		
		writer.write(str + "\n");
        
        writer.flush();
        writeFile.close();
    }
    
    public void modSoftware(List<Softwares> softwaresList) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("softwareTitle");
        String content = request.getParameter("softwareContent");
        String image = request.getParameter("softwareImage");
        for(int i = 0; i < softwaresList.size(); i++) {
            if (softwaresList.get(i).getsId() == id) {
                softwaresList.get(i).setsTitle(title);
                softwaresList.get(i).setsContent(content);
                softwaresList.get(i).setsImage(image);
                sService.modSoftware(softwaresList.get(i));
                break;
            }
        }
    }
    
    public String getSoftwareAuthorName(int author) throws Exception {
        List<Users> usersList = uService.getUsers();
        for (int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).getuId() == author) {
                return usersList.get(i).getuName();
            }
        }
        return "#";
    }
    
    public int getSoftwareAuthorId(String authorName) throws Exception {
        List<Users> usersList = uService.getUsers();
        for (int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).getuName().equals(authorName)) {
                return usersList.get(i).getuId();
            }
        }
        return -1;
    }
    
    public void addSoftware(List<Softwares> softwaresList) throws Exception {
        int author = getSoftwareAuthorId(request.getParameter("softwareAuthor"));
        String title = request.getParameter("softwareTitle");
        String content = request.getParameter("softwareContent");
        String image = request.getParameter("softwareImage");
        Softwares software = new Softwares();
        software.setsAuthor(author);
        software.setsTitle(title);
        software.setsContent(content);
        software.setsImage(image);
        sService.addSoftware(software);
    }
    
    public void delSoftware(List<Softwares> softwaresList) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        for(int i = 0; i < softwaresList.size(); i++) {
           if(softwaresList.get(i).getsId() == id) {
               
               Windows windows = new Windows();
               windows.setWindows(softwaresList.get(i).getsId());
               wService.delWindows(windows);
               
               Android android = new Android();
               android.setAndroid(softwaresList.get(i).getsId());
               aService.delAndroid(android);
               
               Linux linux = new Linux();
               linux.setLinux(softwaresList.get(i).getsId());
               lService.delLinux(linux);
               
               SComments scomments = new SComments();
               scomments.setSoftwareId(softwaresList.get(i).getsId());
               scService.delSCommentsBySoftwareId(scomments);
               
               sService.delSoftware(softwaresList.get(i));

               break;
           }
        }
    }
    
    public void modUser(List<Users> usersList) throws Exception {
    	int id = Integer.parseInt(request.getParameter("id"));
        String userName = request.getParameter("userName");
        String userPasswd = request.getParameter("userPasswd");
        
//         writeToLog(id);
		for (int i = 0; i < usersList.size(); i++){
			if(usersList.get(i).getuId() == id && userName != null && userPasswd != null) {
				usersList.get(i).setuName(userName);
				usersList.get(i).setuPasswd(userPasswd);
//                 writeToLog(id);
				uService.modUser(usersList.get(i));
			}
		}
	}
	
	public void delUser(List<Users> usersList) throws Exception  {
		int id = Integer.parseInt(request.getParameter("id"));
		for (int i = 0; i < usersList.size(); i++){
			if(usersList.get(i).getuId() == id) {
                
                SComments scomments = new SComments();
                scomments.setUserId(usersList.get(i).getuId());
                scService.delSCommentsByUserId(scomments);
                
                Comments comments = new Comments();
                comments.setcAuthor(usersList.get(i).getuId());
                cService.delCommentByAuthor(comments);
                
				uService.delUser(usersList.get(i));
                break;
			}
		}
	}
    
    public void modComment(List<Comments> commentsList) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		for(int i = 0; i < commentsList.size(); i++) {
			if(commentsList.get(i).getcId() == id) {
				commentsList.get(i).setcDisplay(1);
				cService.modComment(commentsList.get(i));
				break;
			}
		}
	}
	
	public void delComment(List<Comments> commentsList) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		for(int i = 0; i < commentsList.size(); i++) {
			if(commentsList.get(i).getcId() == id) {
                
                SComments scomments = new SComments();
                scomments.setCommentId(commentsList.get(i).getcId());
                scService.delSCommentsByCommentId(scomments);
                
				cService.delComment(commentsList.get(i));
                
				break;
			}
		}
	}
    
    public String getImage() {
        images = new String[]{"01","10","11","12","13","14","15","16","17","18","19","20","21","22","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42"};
        Random random = new Random();
        int number = random.nextInt(images.length);
        return images[number] + ".jpg";
    }
	
	static class CompratorByLastModified implements Comparator<File> {
		public int compare(File f1, File f2) {
			long diff = f1.lastModified() - f2.lastModified();
			if (diff > 0)
				return 1;
			else if (diff == 0)
				return 0;
			else
				return -1;
		}
 
		public boolean equals(Object obj) {
			return true;
		}
	}
	
	public void addPicture() throws Exception {
		String pictureLink = request.getParameter("pictureLink");
// 		writeToLog(pictureLink == null);
		//writeToLog(pageNum);
		if(pictureLink != null) {
			String cmd = "aria2c -d " + request.getSession().getServletContext().getRealPath("img/") + " " + pictureLink;
// 			writeToLog(cmd);
			Process  p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		}else{
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload pictureUpload = new ServletFileUpload(factory);
			List<FileItem> fileItems = pictureUpload.parseRequest(request);
			for(FileItem item : fileItems) {
				if(!item.isFormField()) {
					String fileName = item.getName();
					InputStream is = item.getInputStream();
					String filePath = request.getSession().getServletContext().getRealPath("img/");
					File file = new File(filePath, fileName);
					FileOutputStream fos = new FileOutputStream(file);
					byte[] bytes = new byte[1024];
					int len = -1;
					while((len = is.read(bytes)) != -1) {
						fos.write(bytes, 0, len);
					}
				}
			}
		}
	}
	
	public void modPicture(String pictrueId, String pictureName) throws Exception {
		int pictrueId_int = Integer.parseInt(pictrueId);
		String basePath= request.getSession().getServletContext().getRealPath("img/");
		File file = new File(basePath);
		File[] filesList = file.listFiles();
		Arrays.sort(filesList, new CompratorByLastModified());
		//File theFile = new File(basePath + filesList[pictrueId_int]);
		filesList[pictrueId_int].renameTo(new File(basePath + pictureName));
// 		File theNewFile = new File(theFile.getParent() + File.separator + pictureName);
// 		File newFile = new File(file.getParent() + File.separator + newName);
// 		String c = theFile.getParent();
// 		File theNewFile = new File(c + File.pathSeparator + pictureName);
		writeToLog(basePath + filesList[pictrueId_int]);
		writeToLog(basePath + pictureName);
	}
	
	public void delPicture(String pictrueId) {
		int pictrueId_int = Integer.parseInt(pictrueId);
		String basePath = request.getSession().getServletContext().getRealPath("img/");
		File file = new File(basePath);
		File[] filesList = file.listFiles();
		Arrays.sort(filesList, new CompratorByLastModified());
		filesList[pictrueId_int].delete();
	}

	
	public String getPicturesDisplayStr(String operate) throws Exception {
		request.setAttribute("pageNumStr", getPageNumStr(pageNum, tableName, tableOperation));
		String head = "<tr><th>编号</th><th>日期</th><th>名称</th><th>预览</th><th>操作</th></tr>";
        String body = "";
		String pageNum = request.getParameter("pageNum");
		int pageNumInt = (pageNum != null ? Integer.parseInt(pageNum) : 1);
		String basePath = request.getSession().getServletContext().getRealPath("img/");
		File file = new File(basePath);
		File[] filesList = file.listFiles();
		Arrays.sort(filesList, new CompratorByLastModified());
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(operate.equals("add")) {
			body += "<tr><td style=\"vertical-align: middle;\">系统自动分配</td>";
			body += "<td style=\"vertical-align: middle;\">系统自动分配</td>";
			body += "<td style=\"vertical-align: middle;\"><form id=\"postPictureLink\" method=\"post\" action=\"manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Pictures&tableOperation=add\"><textarea name=\"pictureLink\" id=\"pictureLink\" rows=\"1\" class=\"form-control\" placeholder=\"请输入图片下载连接\"></textarea><input type=\"hidden\" name=\"pageNum\" value=\"" + pageNumInt + "\"></form></td>";
			body += "<td style=\"vertical-align: middle;\">暂无</td>";
			body += "<td style=\"vertical-align: middle;\"><a href=\"#\" onclick=\"getAddPictureContent(" + pageNumInt + ")\">下载</a></td></tr>";
			
            body += "<tr><td style=\"vertical-align: middle;\">系统自动分配</td>";
			body += "<td style=\"vertical-align: middle;\">系统自动分配</td>";
			body += "<td style=\"vertical-align: middle;\"><form id=\"postPicture\" method=\"post\" enctype=\"multipart/form-data\" action=\"manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Pictures&tableOperation=add&pictureName=default\"><div class=\"custom-file text-left\"><input type=\"file\" class=\"custom-file-input\" id=\"pictureName\" name=\"pictureName\"><label class=\"custom-file-label\" for=\"pictureName\">请选择文件</label></div></form></td>";
			body += "<td style=\"vertical-align: middle;\">暂无</td>";
			body += "<td style=\"vertical-align: middle;\"><a href=\"#\" onclick=\"getAddPictureContent(" + pageNumInt + ")\">上传</a></td></tr>";
        }
		File[] theFilesList = new File[filesList.length - 1];
		int[] theFilesNum = new int[filesList.length - 1];
		if(searchStr == null) {
			for(int i = filesList.length - 1, j = 0; i >= 0; i--) {
				if(filesList[i].isFile()) {
					theFilesList[j] = filesList[i];
					theFilesNum[j] = i;
					j++;
				}
			}
		}else{
			end = 0;
			for(int i = filesList.length - 1, j = 0; i >= 0; i--) {
				if(filesList[i].isFile() && filesList[i].getName().toUpperCase().indexOf(searchStr.toUpperCase()) != -1) {
					theFilesList[j] = filesList[i];
					theFilesNum[j] = i;
					j++;
					end = j;
				}
			}
			start = 0;
		}
		if(end == 0) {
			return "<div class=\"container-fluid text-center mt-4 pt-4\"><br /><br /><br /><br /><br /><br /><h3><strong>ㄟ( ▔, ▔ )ㄏ<br /><br /><br />找不到任何内容~~</strong></h3></div>";
		}
		for(int i = start; i < end; i++) {
			body += "<tr><td style=\"vertical-align: middle;\">" + (theFilesNum[i] + 1) + "</td>";
			cal.setTimeInMillis(theFilesList[i].lastModified());
			body += "<td style=\"vertical-align: middle;\">" + formatter.format(cal.getTime()) + ".0</td>";
			if (operate.equals("mod")) {
				body += "<td style=\"vertical-align: middle;\"><textarea id=\"pictureName" + theFilesNum[i]  + "\" rows=\"1\" class=\"form-control\">" + theFilesList[i].getName() + "</textarea></td>";
			}else{
				body += "<td style=\"vertical-align: middle;\">" + theFilesList[i].getName() + "</td>";
			}
			body += "<td class=\"w-25\" style=\"vertical-align: middle;\"><img src=\"img/" + theFilesList[i].getName() + "\" class=\"img-fluid rounded\"></td>";
			if (operate.equals("add")) {
                body += "<td style=\"vertical-align: middle;\">暂无</td></tr>";
            }else if(operate.equals("mod")) {
                body += "<td style=\"vertical-align: middle;\"><a href=\"#\" onclick=\"getPictureVaule(" + theFilesNum[i] + "," + pageNumInt + ")\">修改</a></td></tr>";
            }else{
                body += "<td style=\"vertical-align: middle;\"><a href=\"manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=Pictures&tableOperation=del&pageNum=" + pageNumInt + "&id=" + theFilesNum[i] + "\">删除</a></td></tr>";
            }
		}
		
// 		String[] filesList=new File(basePath).list();
		return getDisplayStr(head, body);
	}
	
	public String getPagePrevious(int pageNumInt, int pageNumSum, String tableName, String tableOperation) {
        if(pageNumInt == 1) {
            return "<li class=\"page-item disabled\"><a class=\"page-link bg-light\" href=\"#\">上一页</a></li>";
        }else{
			String adminInfo = "userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0";
        	String urlInfo = adminInfo + "&tableName=" + tableName + "&tableOperation=" + tableOperation + "&pageNum=" + (pageNumInt - 1);
            return "<li class=\"page-item\"><a class=\"page-link bg-light\" href=\"manage?" + urlInfo + "\">上一页</a></li>";
        }
    }
    
    public String getPageNext(int pageNumInt, int pageNumSum, String tableName, String tableOperation) {
        if(pageNumInt == pageNumSum) {
            return "<li class=\"page-item disabled\"><a class=\"page-link bg-light\" href=\"#\">下一页</a></li>";
        }else{
			String adminInfo = "userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0";
        	String urlInfo = adminInfo + "&tableName=" + tableName + "&tableOperation=" + tableOperation + "&pageNum=" + (pageNumInt + 1);
            return "<li class=\"page-item\"><a class=\"page-link bg-light\" href=\"manage?" + urlInfo + "\">下一页</a></li>";
        }
    }
    
    public String getPageNumOne(int pageNumInt, int pageNumSum, String tableName, String tableOperation) {
		String adminInfo = "userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0";
        String pageNumStr = "";
        String urlInfo = adminInfo + "&tableName=" + tableName + "&tableOperation=" + tableOperation + "&pageNum=" + pageNumSum;
        pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">1</a></li>";
        pageNumStr += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">…</a></li>";
        pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"manage?" + urlInfo + "\">" + pageNumSum + "</a></li>";
        return pageNumStr;
    }
    
    public String getPageNumLast(int pageNumInt, int pageNumSum, String tableName, String tableOperation) {
		String adminInfo = "userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0";
        String pageNumStr = "";
        String urlInfo = adminInfo + "&tableName=" + tableName + "&tableOperation=" + tableOperation + "&pageNum=1";
        pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"manage?" + urlInfo + "\">1</a></li>";
        pageNumStr += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">…</a></li>";
        pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + pageNumSum + "</a></li>";
        return pageNumStr;
    }
    
    public String getPageNumBothSides(int pageNumInt, int pageNumSum, String tableName, String tableOperation) {
		String adminInfo = "userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0";
        String pageNumStr = "";
        for(int i = 1; i <= pageNumSum; i++) {
            String urlInfo = adminInfo + "&tableName=" + tableName + "&tableOperation=" + tableOperation + "&pageNum=" + i;
            if(i == 1 || i == pageNumSum) {
                if(pageNumInt == i) {
                    pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + i + "</a></li>";
                }else{
                    pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"manage?" + urlInfo + "\">" + i + "</a></li>";
                }
            }else if(i == 2 && pageNumInt == 2) {
                pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + i + "</a></li>";
                pageNumStr += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">…</a></li>";
            }else if(i == pageNumSum - 1 && pageNumInt == pageNumSum - 1) {
                pageNumStr += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">…</a></li>";
                pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + i + "</a></li>";
            }
        }
        return pageNumStr;
    }
    
    public String getPageNumMiddle(int pageNumInt, int pageNumSum, String tableName, String tableOperation) {
		String adminInfo = "userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0";
        String pageNumStr = "";
        for(int i = 1; i <= pageNumSum; i++) {
            String urlInfo = adminInfo + "&tableName=" + tableName + "&tableOperation=" + tableOperation + "&pageNum=" + i;
            if(i == 1 || i == pageNumSum || i == pageNumInt) {
                if(pageNumInt == i) {
                    pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + i + "</a></li>";
                }else{
                    pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"manage?" + urlInfo + "\">" + i + "</a></li>";
                }
                if(i == 1 || i == pageNumInt) {
                    pageNumStr += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">…</a></li>";
                }
            }
        }
        return pageNumStr;
    }
    
    public String getPageNum(int pageNumInt, int pageNumSum, String tableName, String tableOperation) {
		String adminInfo = "userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0";
        String pageNumStr = "";
        if(pageNumSum <= 3) {
            for(int i = 1; i <= pageNumSum; i++) {
                String urlInfo = adminInfo + "&tableName=" + tableName + "&tableOperation=" + tableOperation + "&pageNum=" + i;
                if(pageNumInt == i) {
                    pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + i + "</a></li>";
                }else{
                    pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"manage?" + urlInfo + "\">" + i + "</a></li>";
                }
            }
        }else{
            if(pageNumInt == 1) {
                return getPageNumOne(pageNumInt, pageNumSum, tableName, tableOperation);
            }else if(pageNumInt == pageNumSum) {
                return getPageNumLast(pageNumInt, pageNumSum, tableName, tableOperation);
            }else{
                if(pageNumInt == 2 || pageNumInt == pageNumSum - 1) {
                    return getPageNumBothSides(pageNumInt, pageNumSum, tableName, tableOperation);
                }else{
                    return getPageNumMiddle(pageNumInt, pageNumSum, tableName, tableOperation);
                }
            }
        }
        return pageNumStr;
    }
	
	public int getListSum(String tableName) throws Exception {
		int listSum = -1;
		if(tableName == null || tableName.equals("Users")) {
			listSum = uService.getUsers().size();
		}else if(tableName.equals("Softwares")){
			listSum = sService.getSoftwares().size();
		}else if(tableName.equals("Comments")){
			listSum = cService.getComments().size();
		}else if(tableName.equals("Pictures")) {
			String basePath = request.getSession().getServletContext().getRealPath("img/");
			File file = new File(basePath);
			File[] filesList = file.listFiles();
			listSum = filesList.length;
			for(int i =  0; i < filesList.length; i++) {
				if(!filesList[i].isFile()) {
					listSum--;
				}
			}
		}else if(tableName.equals("Windows") || tableName.equals("Android") || tableName.equals("Linux")) {
			listSum = getClassificationSoftwareList(tableName);
		}
		return listSum;
	}
	
	public String getPageNumStr(String pageNum, String tableName, String tableOperation) throws Exception {
		int pageNumInt = 1;
        int pageNumSum = 1;
// 		writeToLog("33333");
		int listSum = getListSum(tableName);
// 		writeToLog("44444");
		
		if(searchStr != null) {
            return "<stong>以上是搜索结果</stong>";
        }
		
        if(pageNum == null) {
            start = 0;
            end = (start + 15 <= listSum ? start + 15 : listSum);
        }else{
            start = (Integer.parseInt(pageNum) - 1) * 15;
            end = (start + 15 <= listSum ? start + 15 : listSum);
        }
		
		if(listSum % 15 != 0) {
            pageNumSum = listSum / 15 + 1;
        }else{
            pageNumSum = listSum / 15;
        }
		if(pageNum != null) {
            pageNumInt = Integer.parseInt(pageNum);
        }
		
		return getPagePrevious(pageNumInt, pageNumSum, tableName, tableOperation) + getPageNum(pageNumInt, pageNumSum, tableName, tableOperation) + getPageNext(pageNumInt, pageNumSum, tableName, tableOperation);
	}
    
	public List<Users> getUsersSearchList(List<Users> usersList) {
		List<Users> searchUsersList = new ArrayList<>();
		for(int i = 0; i < usersList.size(); i++) {
			if(usersList.get(i).getuName().toUpperCase().indexOf(searchStr.toUpperCase()) != -1 || usersList.get(i).getuPasswd().toUpperCase().indexOf(searchStr.toUpperCase()) != -1) {
				searchUsersList.add(usersList.get(i));
			}
		}
		start = 0;
		end = searchUsersList.size();
		return searchUsersList;
	}
	
	public List<Softwares> getSoftwaresSearchList(List<Softwares> softwaresList) {
		List<Softwares> searchSoftwaresList = new ArrayList<>();
		for(int i = 0; i < softwaresList.size(); i++) {
			if(softwaresList.get(i).getsTitle().toUpperCase().indexOf(searchStr.toUpperCase()) != -1 || softwaresList.get(i).getsContent().toUpperCase().indexOf(searchStr.toUpperCase()) != -1 || softwaresList.get(i).getsImage().toUpperCase().indexOf(searchStr.toUpperCase()) != -1) {
				searchSoftwaresList.add(softwaresList.get(i));
			}
		}
		start = 0;
		end = searchSoftwaresList.size();
		return searchSoftwaresList;
	}
	
	public List<Comments> getCommentsSearchList(List<Comments> commentsList) throws Exception {
		List<Comments> searchCommentsList = new ArrayList<>();
		for(int i = 0; i < commentsList.size(); i++) {
			if(getAuthorName(commentsList.get(i).getcAuthor()).toUpperCase().indexOf(searchStr.toUpperCase()) != -1 || commentsList.get(i).getcContent().toUpperCase().indexOf(searchStr.toUpperCase()) != -1) {
				searchCommentsList.add(commentsList.get(i));
			}
		}
		start = 0;
		end = searchCommentsList.size();
		return searchCommentsList;
	}
	
	public List<Softwares> getClassificationSearchList(List<Softwares> softwaresList) throws Exception {
		List<Softwares> searchClassificationList = new ArrayList<>();
		for(int i = 0; i < softwaresList.size(); i++) {
			if(softwaresList.get(i).getsTitle().toUpperCase().indexOf(searchStr.toUpperCase()) != -1) {
				searchClassificationList.add(softwaresList.get(i));
			}
		}
		start = 0;
		end = searchClassificationList.size();
		return searchClassificationList;
	}
	
	public List<Softwares> getTheClassificationSoftwareList(List<Softwares> softwaresList) throws Exception {
		List<Softwares> theClassificationSoftwareList = new ArrayList<>();
// 		for(int i = 0; i < softwaresList.size(); i++) {
// 			for(int j = 0; j < classificationSoftwareListSize; j++) {
// 				if(softwaresList.get(i).getsId() == classificationSoftwareList[j]) {
// 					theClassificationSoftwareList.add(softwaresList.get(i));
// 					break;
// 				}
// 			}
// 		}
        
        for(int i = 0; i < softwaresList.size(); i++) {
            for(int j = 0; j < classificationSoftwareListSize; j++) {
                if(tableName.equals("Windows")) {
                    if(softwaresList.get(i).getsId() == windowsList.get(j).getWindows()) {
                        theClassificationSoftwareList.add(softwaresList.get(i));
                        break;
                    }
                }else if(tableName.equals("Android")) {
                    if(softwaresList.get(i).getsId() == androidList.get(j).getAndroid()) {
                        theClassificationSoftwareList.add(softwaresList.get(i));
                        break;
                    }
                }else if(tableName.equals("Linux")) {
                    if(softwaresList.get(i).getsId() == linuxList.get(j).getLinux()) {
                        theClassificationSoftwareList.add(softwaresList.get(i));
                        break;
                    }
                }
                
            }
        }
		return theClassificationSoftwareList;
	}
	
	public int getClassificationSoftwareList(String tableName) throws Exception {
// 		String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/" + tableName + "Software.txt";
// 		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
// 		List<String> results = new ArrayList<>();
// 		String lineTxt = null;
// 		while ((lineTxt = br.readLine()) != null) {
// 			results.add(lineTxt);
// 		}
// 		classificationSoftwareListSize = results.size();
// 		classificationSoftwareList = new int[classificationSoftwareListSize];
// 		for(int i = 0; i < results.size(); i++) {
// 			classificationSoftwareList[i] = Integer.parseInt(results.get(i));
// 		}
// 		br.close();
// 		return classificationSoftwareListSize;
        
        if(tableName.equals("Windows")) {
            windowsList = wService.getWindows();
            classificationSoftwareListSize = windowsList.size();
        }else if(tableName.equals("Android")) {
            androidList = aService.getAndroid();
            classificationSoftwareListSize = androidList.size();
        }else if(tableName.equals("Linux")) {
            linuxList = lService.getLinux();
            classificationSoftwareListSize = linuxList.size();
        }
        return classificationSoftwareListSize;
	}
	
	public void addClassification(List<Softwares> softwaresList, String tableName) throws Exception {
// 		getClassificationSoftwareList(tableName);
// 		softwaresList = getResverseSoftwaresList(softwaresList);
// 		String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/" + tableName + "Software.txt";
//         FileWriter writeFile = new FileWriter(fileName);
//         BufferedWriter writer = new BufferedWriter(writeFile);
// 		for(int i = 0; i < softwaresList.size(); i++) {
// 			if(softwaresList.get(i).getsTitle().toUpperCase().indexOf(request.getParameter("softwareTitle").toUpperCase()) != -1) {
// 				writer.write(softwaresList.get(i).getsId() + "\n");
// 				continue;
// 			}
// 			for(int j = 0; j < classificationSoftwareListSize; j++) {
// 				if(softwaresList.get(i).getsId() == classificationSoftwareList[j]){
// 					writer.write(softwaresList.get(i).getsId() + "\n");
// 					break;
// 				}
// 			}
// 		}
//         writer.flush();
//         writeFile.close();
        
        for(int i = 0; i < softwaresList.size(); i++) {
			if(softwaresList.get(i).getsTitle().toUpperCase().indexOf(request.getParameter("softwareTitle").toUpperCase()) != -1) {
                if(tableName.equals("Windows")) {
                    Windows windows = new Windows();
                    windows.setWindows(softwaresList.get(i).getsId());
                    wService.addWindows(windows);
                }else if(tableName.equals("Android")) {
                    Android android = new Android();
                    android.setAndroid(softwaresList.get(i).getsId());
                    aService.addAndroid(android);
                }else if(tableName.equals("Linux")) {
                    Linux linux = new Linux();
                    linux.setLinux(softwaresList.get(i).getsId());
                    lService.addLinux(linux);
                }
                break;
			}
        }
	}
	
	public void delClassification(List<Softwares> softwaresList, String tableName) throws Exception {
// 		getClassificationSoftwareList(tableName);
// 		softwaresList = getResverseSoftwaresList(softwaresList);
// 		String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/" + tableName + "Software.txt";
//         FileWriter writeFile = new FileWriter(fileName);
//         BufferedWriter writer = new BufferedWriter(writeFile);
// 		for(int i = 0; i < softwaresList.size(); i++) {
// 			if(softwaresList.get(i).getsId() == Integer.parseInt(request.getParameter("id"))) {
// 				continue;
// 			}
// 			for(int j = 0; j < classificationSoftwareListSize; j++) {
// 				if(softwaresList.get(i).getsId() == classificationSoftwareList[j]){
// 					writer.write(softwaresList.get(i).getsId() + "\n");
// 					break;
// 				}
// 			}
// 		}
//         writer.flush();
//         writeFile.close();
        
        for(int i = 0; i < softwaresList.size(); i++) {
			if(softwaresList.get(i).getsId() == Integer.parseInt(request.getParameter("id"))) {
                if(tableName.equals("Windows")) {
                    Windows windows = new Windows();
                    windows.setWindows(softwaresList.get(i).getsId());
                    wService.delWindows(windows);
                }else if(tableName.equals("Android")) {
                    Android android = new Android();
                    android.setAndroid(softwaresList.get(i).getsId());
                    aService.delAndroid(android);
                }else if(tableName.equals("Linux")) {
                    Linux linux = new Linux();
                    linux.setLinux(softwaresList.get(i).getsId());
                    lService.delLinux(linux);
                }
                break;
			}
        }
	}
	
	public String getClassificationDisplayStr(List<Softwares> softwaresList, String tableName) throws Exception {
		request.setAttribute("pageNumStr", getPageNumStr(pageNum, tableName, "default"));
		String head = "<tr><th>编号</th><th>作者</th><th>日期</th><th>标题</th><th>图片</th><th>操作</th></tr>";
        String body = "";
		String pageNum = request.getParameter("pageNum");
		int pageNumInt = (pageNum != null ? Integer.parseInt(pageNum) : 1);
		
		body += "<tr><td style=\"vertical-align: middle;\">系统自动查找编号</td>";
		body += "<td style=\"vertical-align: middle;\">系统自动查找作者</td>";
		body += "<td style=\"vertical-align: middle;\">系统自动查找时间</td>";
		body += "<td style=\"vertical-align: middle;\"><textarea id=\"softwareTitle\" class=\"form-control\" placeholder=\"请输入文章标题\"></textarea></td>";
		body += "<td style=\"vertical-align: middle;\">系统自动查找图片</td>";
		body += "<td style=\"vertical-align: middle;\"><a href=\"#\"  onclick=\"getAddClassificationValue(" + pageNumInt + ",'" + tableName + "')\">添加</a></td></tr>";
		
		softwaresList = getResverseSoftwaresList(softwaresList);
		softwaresList = getTheClassificationSoftwareList(softwaresList);
		if(searchStr != null) {
			softwaresList = getClassificationSearchList(softwaresList);
		}
		if(end == 0) {
			return "<div class=\"container-fluid text-center mt-4 pt-4\"><br /><br /><br /><br /><br /><br /><h3><strong>ㄟ( ▔, ▔ )ㄏ<br /><br /><br />找不到任何内容~~</strong></h3></div>";
		}
        for(int i = start; i < end; i++) {
            body += "<tr><td style=\"vertical-align: middle;\">" + softwaresList.get(i).getsId() + "</td>";
            body += "<td style=\"vertical-align: middle;\">" + getAuthorName(softwaresList.get(i).getsAuthor()) + "</td>";
            body += "<td style=\"vertical-align: middle;\">" + softwaresList.get(i).getsDate() + "</td>";
			body += "<td style=\"vertical-align: middle;\">" + softwaresList.get(i).getsTitle() + "</td>";
			body += "<td class=\"w-25\" style=\"vertical-align: middle;\"><img src=\"img/" + softwaresList.get(i).getsImage() + "\" class=\"img-fluid rounded\"></td>";
			body += "<td style=\"vertical-align: middle;\"><a href=\"manage?userId=1&theUserName=admin&theUserPasswd=1314520&registrationDate=2019-12-10 09:33:23.0&tableName=" + tableName + "&tableOperation=del&pageNum=" + pageNumInt + "&id=" + softwaresList.get(i).getsId() + "\">删除</a></td></tr>";
            }
        return getDisplayStr(head, body);
	}
	
	public String getRssItemDescription(String content, String image) throws Exception {
		String str = "";
		str += "<img src=\"http://software.yongkj.cn/software/img/" + image + "\" alt=\"\" />";
		if(content.length()  <= 100) {
            str += content;
        }else{
			str += content.substring(0, 100) + "...";
		}
		return str.replace("\n","<br />");
	}
	
	public List<Item> getRssItems() throws Exception {
// 		String itemsStr = "";
// 		softwaresList = sService.getSoftwares();
// 		softwaresList = getResverseSoftwaresList(softwaresList);
// 		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
// 		SimpleDateFormat _df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
// 		for(int i = 0; i < 20; i ++) {
// 			itemsStr += "<item>\n      ";
// 			itemsStr += "<title>" + softwaresList.get(i).getsTitle() + "</title>\n      ";
// 			itemsStr += "<author>ADMIN</author>\n      ";
// 			itemsStr += "<link>http://software.yongkj.cn/software/content?softwareId=" + softwaresList.get(i).getsId() + "</link>\n      ";
// 			itemsStr += "<guid>http://software.yongkj.cn/software/content?softwareId=" + softwaresList.get(i).getsId() + "</guid>\n      ";
// 			itemsStr += "<description>" + getRssItemDescription(softwaresList.get(i).getsContent(), softwaresList.get(i).getsImage()) + "</description>\n      ";
// 			itemsStr += "<pubDate>" + _df.format(df.parse(softwaresList.get(i).getsDate().replace(".0", ""))) + "</pubDate>\n    ";
// 			itemsStr += "</item>\n" + (i != 19 ? "    " : "  ");
// 		}
		
// 		return itemsStr;
		
		
		softwaresList = sService.getSoftwares();
		softwaresList = getResverseSoftwaresList(softwaresList);
		List<Item> itemsList = new ArrayList<Item>();// 这个list对应rss中的item列表
		for(int i = 0; i < 20; i ++) {
			Item item = new Item();// 新建Item对象，对应rss中的<item></item>
			item.setTitle(softwaresList.get(i).getsTitle());// 对应<item>中的<title></title>
			item.setAuthor("ADMIN");
			item.setLink("http://software.yongkj.cn/software/content?softwareId=" + softwaresList.get(i).getsId());  //对应 <item>中的具体标题
			Guid guid = new Guid();// 为当前新闻指定一个全球唯一标示，这个不是必须的
			guid.setValue("http://software.yongkj.cn/software/content?softwareId=" + softwaresList.get(i).getsId());
			item.setGuid(guid);
			// 新建一个Description，它是Item的描述部分
			Description description = new Description();
			description.setType("text/html");
			description.setValue(getRssItemDescription(softwaresList.get(i).getsContent(), softwaresList.get(i).getsImage()));// <description>中的内容
			item.setDescription(description);// 添加到item节点中
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			item.setPubDate(df.parse(softwaresList.get(i).getsDate().replace(".0", "")));// 这个<item>对应的发布时间
			itemsList.add(item);// 代表一个段落<item></item>，
		}
		return itemsList;
	}
	
	public void updateRssFile() throws Exception {
// 		String rssStr = "";
// 		SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
// 		rssStr += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
// 		rssStr += "<rss version=\"2.0\">\n  ";
// 		rssStr += "<channel>\n    ";
// 		rssStr += "<title>软件俱乐部</title>\n    ";
// 		rssStr += "<description>这是一个属于软件的舞台！在这里，你将看到它们是如何展现个性风采的。快来加入吧，为你欣赏的软件加油助威，留下最热烈的喝彩！</description>\n    ";
// 		rssStr += "<link>http://software.yongkj.cn/software/</link>\n    ";
// 		rssStr += "<lastBuildDate>" + df.format(new Date()) + "</lastBuildDate>\n    ";
// 		rssStr += "<ttl>720</ttl>\n    ";
// 		rssStr += "<image>\n      ";
// 		rssStr += "<url>http://software.yongkj.cn/software/img/cropped-动漫-43-192x192.jpg</url>\n      ";
// 		rssStr += "<title>软件俱乐部</title>\n      ";
// 		rssStr += "<link>http://software.yongkj.cn/software/</link>\n    ";
// 		rssStr += "</image>\n    ";
// 		rssStr += getRssItems();
// 		rssStr += "</channel>\n";
// 		rssStr += "</rss>";
		
// 		String fileName = request.getSession().getServletContext().getRealPath("") + "club.xml";
//         FileWriter writeFile = new FileWriter(fileName);
//         BufferedWriter writer = new BufferedWriter(writeFile);
		
// 		writer.write(rssStr);
        
//         writer.flush();
//         writeFile.close();
		
		SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
		Channel channel = new Channel("rss_2.0");
		channel.setTitle("软件俱乐部");// 网站标题
		channel.setDescription("这是一个属于软件的舞台！在这里，你将看到它们是如何展现个性风采的。快来加入吧，为你欣赏的软件加油助威，留下最热烈的喝彩！");// 网站描述
		channel.setLink("http://software.yongkj.cn/software/");// 网站主页链接
		channel.setEncoding("utf-8");// RSS文件编码
		channel.setLanguage("zh-cn");// RSS使用的语言
		channel.setTtl(720);// time to live的简写，在刷新前当前RSS在缓存中可以保存多长时间（分钟）
		channel.setCopyright("Copyright(C) 软件俱乐部");// 版权声明
		channel.setLastBuildDate(new Date());// RSS发布时间
		Image img = new Image();
		img.setUrl("http://software.yongkj.cn/software/img/club_icon.png");
		img.setTitle("软件俱乐部");
		img.setLink("http://software.yongkj.cn/software/");
		img.setWidth(528);
		img.setHeight(528);
		channel.setImage(img);
		channel.setItems(getRssItems());
		
		WireFeedOutput out = new WireFeedOutput();
		String fileName = request.getSession().getServletContext().getRealPath("") + "club.xml";
        FileWriter writeFile = new FileWriter(fileName);
        BufferedWriter writer = new BufferedWriter(writeFile);
		writer.write(out.outputString(channel));
		writer.flush();
        writeFile.close();
	}
	
    public void judge() throws Exception {
        
// 		writeToLog(request.getSession().getServletContext().getRealPath(""));
// 		writeToLog(request.getSession().getServletContext().getRealPath("/img"));
// 		writeToLog(request.getSession().getServletContext().getRealPath("img/"));
// 		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
        String userId = request.getParameter("userId");
        String userName = request.getParameter("theUserName");
        String userPasswd = request.getParameter("theUserPasswd");
        String registrationDate = request.getParameter("registrationDate");
        if((userId == null || userName == null || userPasswd == null) || !(userId.equals("1") && userName.equals("admin") && userPasswd.equals("1314520") && registrationDate.equals("2019-12-10 09:33:23.0"))) {
            request.setAttribute("skipUrl", "http://" + baseIP + "/software/login");
            request.setAttribute("image", getImage());
            request.setAttribute("displayStr1", "‼(•'╻'• )꒳ᵒ꒳ᵎᵎᵎ");
            request.setAttribute("displayStr2", "(⊙o⊙)，啧啧，你居然想偷偷进入后台管理！！！<br />不行的哦，你是坏孩子~~~");
            request.setAttribute("displayStr3", "无法进入后台管理！<br />正在返回登录页面。");
            dispatcher = request.getRequestDispatcher("/skip.jsp");
        	dispatcher.forward(request, response);
            return;
        }
        
        tableName = request.getParameter("tableName");
        tableOperation = request.getParameter("tableOperation");
		searchStr = request.getParameter("searchStr");
		pageNum = request.getParameter("pageNum");
		
		if(tableName == null) {
			request.setAttribute("search", "\"Users\", \"add\"");
		}else{
			if(tableName.equals("Windows") || tableName.equals("Android") || tableName.equals("Linux")) {
				request.setAttribute("search", "'" + tableName + "', 'default'");
			}else{
				request.setAttribute("search", "'" + tableName + "', '" + tableOperation + "'");
			}
		}
		
        if(tableName == null) {
// 			writeToLog("=====");
            request.setAttribute("displayStr", getUsersDisplayStr(uService.getUsers(), tableOperation));
//             request.setAttribute("displayStr", getSoftwaresDisplayStr(sService.getSoftwares()));
//             request.setAttribute("displayStr", getCommentsDisplayStr(cService.getComments()));
// 			writeToLog("~~~~~");
        }else if(tableName.equals("Users")){
        	if(request.getParameter("id") != null) {
				if(tableOperation.equals("mod")) {
					modUser(uService.getUsers());
				}else{
					delUser(uService.getUsers());
				}
			}
			request.setAttribute("displayStr", getUsersDisplayStr(uService.getUsers(), tableOperation));
        }else if(tableName.equals("Softwares")){
            if(request.getParameter("id") == null) {
                if(request.getParameter("softwareAuthor") != null){
                    addSoftware(sService.getSoftwares());
                    updateRssFile();
                }
            }else{
                if(tableOperation.equals("mod")) {
                    modSoftware(sService.getSoftwares());
                    updateRssFile();
                }else{
                    delSoftware(sService.getSoftwares());
                    updateRssFile();
                }
            }
            //updateRssFile();
			request.setAttribute("displayStr", getSoftwaresDisplayStr(sService.getSoftwares(), tableOperation));
        }else if(tableName.equals("Comments")){
        	if(request.getParameter("id") != null) {
				if(tableOperation.equals("mod")) {
					modComment(cService.getComments());
				}else{
					delComment(cService.getComments());
				}
			}
			request.setAttribute("displayStr", getCommentsDisplayStr(cService.getComments(), tableOperation));
        }else if(tableName.equals("Pictures")) {
			if(request.getParameter("id") == null) {
// 				writeToLog(request.getParameter("pictureLink") == null);
				if(request.getParameter("pictureName") != null || request.getParameter("pictureLink") != null) {
					addPicture();
				}
			}else{
				if(tableOperation.equals("mod")) {
					modPicture(request.getParameter("id"), request.getParameter("pictureName"));
				}else{
					delPicture(request.getParameter("id"));
				}
			}
			request.setAttribute("displayStr", getPicturesDisplayStr(tableOperation));
		}else if(tableName.equals("Windows") || tableName.equals("Android") || tableName.equals("Linux")) {
			if(request.getParameter("softwareTitle") != null) {
				addClassification(sService.getSoftwares(), tableName);
			}else if(request.getParameter("id") != null) {
				delClassification(sService.getSoftwares(), tableName);
			}
			request.setAttribute("displayStr", getClassificationDisplayStr(sService.getSoftwares(), tableName));
		}
        dispatcher = request.getRequestDispatcher("/manage.jsp");
        dispatcher.forward(request, response);
    }

}