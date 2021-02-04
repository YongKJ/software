
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

public class HomePageService{

    public HttpServletRequest request;
    public HttpServletResponse response;
    public SoftwaresService sService;
    public UsersService uService;
    public WindowsService wService;
    public AndroidService aService;
    public LinuxService lService;
    public List<Users> usersList;
    public List<Softwares> softwaresList;
    public List<Windows> windowsList;
    public List<Android> androidList;
    public List<Linux> linuxList;
    public String userId;
    public String searchStr;
    public String pageNum;
	public String chooseClassification;
	public String isMobilePhone;
	public int start;
	public int end;
	public int[] classificationSoftwareList;
	public int classificationSoftwareListSize;
    public RequestDispatcher dispatcher;
    
    public HomePageService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        sService = new SoftwaresService();
        uService = new UsersService();
        wService = new WindowsService();
        aService = new AndroidService();
        lService = new LinuxService();
        userId = "";
        searchStr = "";
        pageNum = "";
		chooseClassification = "";
		start = -1;
		end = -1;
		classificationSoftwareListSize = -1;
		isMobilePhone = null;
    }
    
    public String getDisplayStr(String SoftwaresDisplayStrOne, String SoftwaresDisplayStrTwo, String SoftwaresDisplayStrThree) {
        return "<div id=\"col_one\" class=\"col-sm-12 col-md-4 pt-3 pb-3 pl-0 pr-0\">" + SoftwaresDisplayStrOne + "</div><div id=\"col_two\" class=\"col-sm-12 col-md-4 pt-3 pb-3 pl-0 pr-0\">" + SoftwaresDisplayStrTwo + "</div><div id=\"col_three\" class=\"col-sm-12 col-md-4 pt-3 pb-3 pl-0 pr-0\">" + SoftwaresDisplayStrThree + "</div>";
    }
    
    public String getSoftwareDateStr(int author, String Date) throws Exception {
        String[] date = Date.split(" ");
        String[] year = date[0].split("-");
        return getUserName(author).toUpperCase() + "&nbsp;&nbsp;&#47;&nbsp;&nbsp;" + year[1] + "月&nbsp;" + year[2] + ",&nbsp;" + year[0];
    }
    
    public String getSoftwareContent(String content) {
        if(content.length() <= 20) {
            return content;
        }
        return content.substring(0,20) + "...";
    }
    
    public String getSoftwareStr(Softwares software) throws Exception {
        int userIdInt = -1;
        if(userId != null){
            userIdInt = Integer.parseInt(userId);
        }
        return "<div onclick=\"getSoftwareId(" + userIdInt + "," + software.getsId() + ",'" + (chooseClassification == null ? "default" : chooseClassification) + "')\" class=\"container-fluid pt-3 pb-3\"><div class=\"card m-auto\" style=\"cursor:pointer;\"><div class=\"card-header font-weight-bold\"><h5>" + software.getsTitle() + "</h5></div><div class=\"card-body text-center p-0\"><img src=\"img/" + software.getsImage() + "\" class=\"img-fluid\"></div><div class=\"card-footer pt-3 pb-3\"><div class=\"container-fluid p-0 mb-3\">" + getSoftwareContent(software.getsContent()) + "</div>" + getSoftwareDateStr(software.getsAuthor(), software.getsDate()) + "</div></div></div>";
    }
    
    public List<Softwares> getResverseSoftwaresList(List<Softwares> softwaresList) throws Exception {
        List<Softwares> reverseSoftwaresList = new ArrayList<>();
        for(int i = softwaresList.size() - 1; i >= 0; i--) {
            reverseSoftwaresList.add(softwaresList.get(i));
        }
        return reverseSoftwaresList;
    }
    
    public String getSoftwaresDisplayStr(String searchStr) throws Exception {
        if(searchStr != null) {
            return getSearchSoftwaresDisplayStr(searchStr);
        }
// 		writeToLog("99999");
        softwaresList = getResverseSoftwaresList(sService.getSoftwares());
// 		writeToLog("88888");
		if(chooseClassification != null) {
			softwaresList = getclassificationSoftwareList(softwaresList);
		}
// 		writeToLog("77777");
        String SoftwaresDisplayStrOne = "";
        String SoftwaresDisplayStrTwo = "";
        String SoftwaresDisplayStrThree = "";
// 		writeToLog("66666    " + start + "    55555     " + end + "          44444     " + softwaresList.size());
		int flag = -1;
        for(int i = start; i < end; i++) {
            int num = i % 3;
			if(isMobilePhone != null) {
				num = num == 0 ? ++flag : flag;
			}
            if(num == 0) {
                SoftwaresDisplayStrOne += getSoftwareStr(softwaresList.get(i));
            }else if(num == 1) {
                SoftwaresDisplayStrTwo += getSoftwareStr(softwaresList.get(i));
            }else if(num == 2) {
                SoftwaresDisplayStrThree += getSoftwareStr(softwaresList.get(i));
            }
        }
        return getDisplayStr(SoftwaresDisplayStrOne, SoftwaresDisplayStrTwo, SoftwaresDisplayStrThree);
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
            userNameStr += getUserNameDisplayStr("游客", "register", "注册", "login", "登录");
        }else{
            int id = Integer.parseInt(userId);
            request.setAttribute("userId", id);
            userNameStr += getUserNameDisplayStr(getUserName(id), ("home?userId=" + id), "首页", "home", "退出");
        }
        return userNameStr;
    }
    
    public String getSearchSoftwaresDisplayStr(String searchStr) throws Exception {
        softwaresList = sService.getSoftwares();
		if(chooseClassification != null) {
			softwaresList = getclassificationSoftwareList(softwaresList);
		}
        List<Softwares> searchSoftwaresList = new ArrayList<>();
        String SoftwaresDisplayStrOne = "";
        String SoftwaresDisplayStrTwo = "";
        String SoftwaresDisplayStrThree = "";
        for(int i = softwaresList.size() - 1; i >= 0; i--) {
            if(softwaresList.get(i).getsTitle().toUpperCase().indexOf(searchStr.toUpperCase()) != -1 || softwaresList.get(i).getsContent().toUpperCase().indexOf(searchStr.toUpperCase()) != -1) {
                searchSoftwaresList.add(softwaresList.get(i));
            }
        }
		int flag1 = searchSoftwaresList.size() / 3;
		int flag2 = flag1 * 2;
        for(int i = 0; i < searchSoftwaresList.size(); i++) {
            int num = i % 3;
			if(isMobilePhone != null) {
				if(i < flag1) {
					num = 0;
				}else if(flag1 <= i && i < flag2) {
					num = 1;
				}else if(i >= flag2) {
					num = 2;
				}
			}
            if(num == 0) {
                SoftwaresDisplayStrOne += getSoftwareStr(searchSoftwaresList.get(i));
            }else if(num == 1) {
                SoftwaresDisplayStrTwo += getSoftwareStr(searchSoftwaresList.get(i));
            }else if(num == 2) {
                SoftwaresDisplayStrThree += getSoftwareStr(searchSoftwaresList.get(i));
            }
        }
        if(searchSoftwaresList.size() == 0) {
            return "<div class=\"col-12 pt-3 pb-3\"><div class=\"container-fluid text-center\"><h3><strong>ㄟ( ▔, ▔ )ㄏ<br /><br /><br />找不到任何文章~~</strong></h3></div></div>";
        }
        return getDisplayStr(SoftwaresDisplayStrOne, SoftwaresDisplayStrTwo, SoftwaresDisplayStrThree);
    }
    
    public String getPagePrevious(int userId, int pageNumInt, int pageNumSum) {
        if(pageNumInt == 1) {
            return "<li class=\"page-item disabled\"><a class=\"page-link bg-light\" href=\"#\">上一页</a></li>";
        }else{
//         	String a = "userId=" + userId + "&pageNum=" + (pageNumInt - 1);
//         	String b = "pageNum=" + (pageNumInt - 1);
			String a = "userId=" + userId + (chooseClassification == null ? "&pageNum=" : "&chooseClassification=" + chooseClassification + "&pageNum=") + (pageNumInt - 1);
			String b = (chooseClassification == null ? "pageNum=" : "chooseClassification=" + chooseClassification + "&pageNum=") + (pageNumInt - 1);
			if(isMobilePhone != null) {
				return "<li class=\"page-item\"><a class=\"page-link bg-light\" href=\"home?isMobilePhone=yes&" + (userId == -1 ? b : a) + "\">上一页</a></li>";
			}
            return "<li class=\"page-item\"><a class=\"page-link bg-light\" href=\"home?" + (userId == -1 ? b : a) + "\">上一页</a></li>";
        }
    }
    
    public String getPageNext(int userId, int pageNumInt, int pageNumSum) { 	
        if(pageNumInt == pageNumSum) {
            return "<li class=\"page-item disabled\"><a class=\"page-link bg-light\" href=\"#\">下一页</a></li>";
        }else{
//         	String a = "userId=" + userId + "&pageNum=" + (pageNumInt + 1);
//         	String b = "pageNum=" + (pageNumInt + 1);
			String a = "userId=" + userId + (chooseClassification == null ? "&pageNum=" : "&chooseClassification=" + chooseClassification + "&pageNum=") + (pageNumInt + 1);
			String b = (chooseClassification == null ? "pageNum=" : "chooseClassification=" + chooseClassification + "&pageNum=") + (pageNumInt + 1);
			if(isMobilePhone != null) {
				return "<li class=\"page-item\"><a class=\"page-link bg-light\" href=\"home?isMobilePhone=yes&" + (userId == -1 ? b : a) + "\">下一页</a></li>";
			}
            return "<li class=\"page-item\"><a class=\"page-link bg-light\" href=\"home?" + (userId == -1 ? b : a) + "\">下一页</a></li>";
        }
    }
    
    public String getPageNumOne(int userId, int pageNumInt, int pageNumSum) {
        String pageNumStr = "";
//         String a = "userId=" + userId + "&pageNum=" + pageNumSum;
//         String b = "pageNum=" + pageNumSum;
		String a = "userId=" + userId + (chooseClassification == null ? "&pageNum=" : "&chooseClassification=" + chooseClassification + "&pageNum=") + pageNumSum;
        String b = (chooseClassification == null ? "pageNum=" : "chooseClassification=" + chooseClassification + "&pageNum=") + pageNumSum;
        pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">1</a></li>";
        pageNumStr += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">…</a></li>";
		if(isMobilePhone != null) {
			pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"home?isMobilePhone=yes&" + (userId == -1 ? b : a) + "\">" + pageNumSum + "</a></li>";
		}else{
			pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"home?" + (userId == -1 ? b : a) + "\">" + pageNumSum + "</a></li>";
		}
        return pageNumStr;
    }
    
    public String getPageNumLast(int userId, int pageNumInt, int pageNumSum) {
        String pageNumStr = "";
//         String a = "userId=" + userId + "&pageNum=1";
//         String b = "pageNum=1";
		String a = "userId=" + userId + (chooseClassification == null ? "&pageNum=1" : "&chooseClassification=" + chooseClassification + "&pageNum=1");
        String b = (chooseClassification == null ? "pageNum=1" : "chooseClassification=" + chooseClassification + "&pageNum=1");
		if(isMobilePhone != null) {
			pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"home?isMobilePhone=yes&" + (userId == -1 ? b : a) + "\">1</a></li>";
		}else{
			pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"home?" + (userId == -1 ? b : a) + "\">1</a></li>";
		}
        pageNumStr += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">…</a></li>";
        pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + pageNumSum + "</a></li>";
        return pageNumStr;
    }
    
    public String getPageNumBothSides(int userId, int pageNumInt, int pageNumSum) {
        String pageNumStr = "";
        for(int i = 1; i <= pageNumSum; i++) {
			String a = "userId=" + userId + (chooseClassification == null ? "&pageNum=" : "&chooseClassification=" + chooseClassification + "&pageNum=") + i;
            String b = (chooseClassification == null ? "pageNum=" : "chooseClassification=" + chooseClassification + "&pageNum=") + i;
            if(i == 1 || i == pageNumSum) {
                if(pageNumInt == i) {
                    pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + i + "</a></li>";
                }else{
					if(isMobilePhone != null) {
						pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"home?isMobilePhone=yes&" + (userId == -1 ? b : a) + "\">" + i + "</a></li>";
					}else{
						pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"home?" + (userId == -1 ? b : a) + "\">" + i + "</a></li>";
					}
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
    
    public String getPageNumMiddle(int userId, int pageNumInt, int pageNumSum) {
        String pageNumStr = "";
        for(int i = 1; i <= pageNumSum; i++) {
//             String a = "userId=" + userId + "&pageNum=" + i;
//             String b = "pageNum=" + i;
			String a = "userId=" + userId + (chooseClassification == null ? "&pageNum=" : "&chooseClassification=" + chooseClassification + "&pageNum=") + i;
            String b = (chooseClassification == null ? "pageNum=" : "chooseClassification=" + chooseClassification + "&pageNum=") + i;
            if(i == 1 || i == pageNumSum || i == pageNumInt) {
                if(pageNumInt == i) {
                    pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + i + "</a></li>";
                }else{
					if(isMobilePhone != null) {
						pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"home?isMobilePhone=yes&" + (userId == -1 ? b : a) + "\">" + i + "</a></li>";
					}else{
						pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"home?" + (userId == -1 ? b : a) + "\">" + i + "</a></li>";
					}
                }
                if(i == 1 || i == pageNumInt) {
                    pageNumStr += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">…</a></li>";
                }
            }
        }
        return pageNumStr;
    }
    
    public String getPageNum(int userId, int pageNumInt, int pageNumSum) {
        String pageNumStr = "";
        if(pageNumSum <= 3) {
            for(int i = 1; i <= pageNumSum; i++) {
                String a = "userId=" + userId + (chooseClassification == null ? "&pageNum=" : "&chooseClassification=" + chooseClassification + "&pageNum=") + i;
                String b = (chooseClassification == null ? "pageNum=" : "chooseClassification=" + chooseClassification + "&pageNum=") + i;
                if(pageNumInt == i) {
                    pageNumStr += "<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + i + "</a></li>";
                }else{
					if(isMobilePhone != null) {
						pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"home?isMobilePhone=yes&" + (userId == -1 ? b : a) + "\">" + i + "</a></li>";
					}else{
						pageNumStr += "<li class=\"page-item\"><a class=\"page-link\" href=\"home?" + (userId == -1 ? b : a) + "\">" + i + "</a></li>";
					}
                }
            }
        }else{
            if(pageNumInt == 1) {
                return getPageNumOne(userId, pageNumInt, pageNumSum);
            }else if(pageNumInt == pageNumSum) {
                return getPageNumLast(userId, pageNumInt, pageNumSum);
            }else{
                if(pageNumInt == 2 || pageNumInt == pageNumSum - 1) {
                    return getPageNumBothSides(userId, pageNumInt, pageNumSum);
                }else{
                    return getPageNumMiddle(userId, pageNumInt, pageNumSum);
                }
            }
        }
        return pageNumStr;
    }
	
	public List<Softwares> getclassificationSoftwareList(List<Softwares> softwaresList) throws Exception {
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
	
	public int getListSum() throws Exception {
		if(chooseClassification == null) {
			return sService.getSoftwares().size();
		}else{
// 			String fileName = request.getSession().getServletContext().getRealPath("") + "WEB-INF/src/" + chooseClassification + "Software.txt";
// 			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
// 			List<String> results = new ArrayList<>();
// 			String lineTxt = null;
// 			while ((lineTxt = br.readLine()) != null) {
// 				results.add(lineTxt);
// 			}
//             classificationSoftwareListSize = results.size();
// 			classificationSoftwareList = new int[classificationSoftwareListSize];
// 			for(int i = 0; i < results.size(); i++) {
// 				classificationSoftwareList[i] = Integer.parseInt(results.get(i));
// 			}
// 			br.close();
// 			return classificationSoftwareListSize;
            if(chooseClassification.equals("Windows")) {
                windowsList = wService.getWindows();
                classificationSoftwareListSize = windowsList.size();
            }else if(chooseClassification.equals("Android")) {
                androidList = aService.getAndroid();
                classificationSoftwareListSize = androidList.size();
            }else if(chooseClassification.equals("Linux")) {
                linuxList = lService.getLinux();
                classificationSoftwareListSize = linuxList.size();
            }
            return classificationSoftwareListSize;
            
		}
	}
    
    public String getPageNumStr(String pageNum) throws Exception {
//         softwaresList = sService.getSoftwares();
        searchStr = request.getParameter("searchStr");
        int pageNumInt = 1;
        int pageNumSum = 1;
        int userIdInt = -1;
		int listSum = getListSum();
		
		start = (pageNum == null ? 0 : (Integer.parseInt(pageNum) - 1) * 9);
		end = (start + 9 <= listSum ? start + 9 : listSum);
        
//         writeToLog("start:" + start + "    end:" + end);
		
        if(searchStr != null) {
            request.setAttribute("pageNum", "搜索结果");
            return "<stong>以上是搜索结果</stong>";
        }
        if(listSum % 9 != 0) {
            pageNumSum = listSum / 9 + 1;
//             writeToLog(pageNumInt, pageNumSum, softwaresList.size());
        }else{
            pageNumSum = listSum / 9;
//                 writeToLog(pageNumInt, pageNumSum, softwaresList.size());
        }
        if(pageNum != null) {
            pageNumInt = Integer.parseInt(pageNum);
        }
        if(userId != null){
            userIdInt = Integer.parseInt(userId);
        }
        if(pageNumInt == 1) {
            request.setAttribute("pageNum", "首页");
        }else{
            request.setAttribute("pageNum", "第" + pageNumInt + "页");
        }
        return getPagePrevious(userIdInt, pageNumInt, pageNumSum) + getPageNum(userIdInt, pageNumInt, pageNumSum) + getPageNext(userIdInt, pageNumInt, pageNumSum);
    }
    
    public void writeToLog(int a, int b, int c) throws Exception {
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
//         writer.write(software.getsAuthor() + "\n");
//         writer.write(software.getsTitle() + "\n");
//         writer.write(software.getsContent() + "\n");
//         writer.write(software.getsImage() + "\n");
        writer.write(a + "\n");
        writer.write(b + "\n");
        writer.write(c + "\n");
        
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
    
    public void judge() throws Exception {
        userId = request.getParameter("userId");
        searchStr = request.getParameter("searchStr");
        pageNum = request.getParameter("pageNum");
		chooseClassification = request.getParameter("chooseClassification");
		
		String ua = request.getHeader("User-Agent");
		//writeToLog(ua);
		if(ua.toUpperCase().indexOf("Android".toUpperCase()) != -1) {
			isMobilePhone = "yes";
			request.setAttribute("isMobilePhone", "&isMobilePhone=yes");
		}
		
		if(chooseClassification == null) {
			request.setAttribute("browseChoose", "分类浏览");
			request.setAttribute("classify", "default");
		}else{
			request.setAttribute("browseChoose", chooseClassification + "软件");
			request.setAttribute("classify", chooseClassification);
		}
        request.setAttribute("userName", getTheUserName(userId));
        request.setAttribute("pageNumStr", getPageNumStr(pageNum));
        request.setAttribute("SoftwaresDisplayStr", getSoftwaresDisplayStr(searchStr));
        dispatcher = request.getRequestDispatcher("/home.jsp");
        dispatcher.forward(request, response);
    }

}