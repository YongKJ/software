
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

public class SoftwaresService {
	
	public Softwares software;
	public SoftwaresDao sDao;
	public List<Softwares> softwaresList;
	public List<Users> usersList;
	public UsersService uService;
	public PrintWriter out;
	public HttpServletResponse response;
	public int id;
	public int author;
	public String title;
	public String content;
	public String image;
	
	public SoftwaresService(HttpServletResponse response) throws Exception {
		software = new Softwares();
		sDao = new SoftwaresDao();
		softwaresList = new ArrayList<>();
		uService = new UsersService();
		usersList = new ArrayList<>();
		this.response = response;
		id = 12;
		author = 10;
// 		title = "Hello world!";
// 		content = "世界，你好！";
// 		image = "01.jpg";
		title = "Hello!";
		content = "你好！";
		image = "02.jpg";
	}
	
	public SoftwaresService() {
		software = new Softwares();
		sDao = new SoftwaresDao();
		softwaresList = new ArrayList<>();
		uService = new UsersService();
		usersList = new ArrayList<>();
	}
	
	public List<Softwares> getSoftwares() throws Exception {
		return sDao.getSoftwares();
	}
	
	public boolean judge_dis_exist(Softwares software) throws Exception {
		softwaresList = getSoftwares();
		for(int i = 0; i < softwaresList.size(); i++) {
			if(softwaresList.get(i).getsId() == software.getsId()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean judge_exist(Softwares software) throws Exception {
		softwaresList = getSoftwares();
		usersList = uService.getUsers();
		boolean flag = false;
		for(int i = 0; i < usersList.size(); i++) {
			if(usersList.get(i).getuId() == software.getsAuthor()) {
				flag = true;
				break;
			}
		}
		for(int i = 0; i < softwaresList.size(); i++) {
			if(softwaresList.get(i).getsId() == software.getsId() && flag) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addSoftware(Softwares software) throws Exception {
		if(judge_dis_exist(software)) {
			sDao.addSoftware(software);
			return true;
		}
		return false;
	}
	
	public boolean modSoftware(Softwares software) throws Exception {
		if(judge_exist(software)) {
			sDao.modSoftware(software);
			return true;
		}
		return false;
	}
	
	public boolean delSoftware(Softwares software) throws Exception {
		if(judge_exist(software)) {
			sDao.delSoftware(software);
			return true;
		}
		return false;
	}
	
	public void checkTest() throws Exception {
		softwaresList = getSoftwares();
		for(int i = 0; i < softwaresList.size(); i++){
			out.write(softwaresList.get(i).getsId() + " ");
			out.write(softwaresList.get(i).getsAuthor() + " ");
			out.write(softwaresList.get(i).getsDate() + " ");
			out.write(softwaresList.get(i).getsTitle() + " ");
			out.write(softwaresList.get(i).getsContent() + " ");
			out.write(softwaresList.get(i).getsImage() + (i == softwaresList.size() - 1 ? "\n\n" : "\n"));
		}
	}
	
	public void addTest() throws Exception {
		out.write("添加新文章：\n编号：" + id + "\n作者：" + author + "\n标题：" + title + "\n内容：" + content + "\n图片：" + image + "\n\n");
		if(addSoftware(software)) {
			out.write("添加文章成功！\n\n");
		}else{
			out.write("添加文章失败！\n\n");
		}
	}
	
	public void modTest() throws Exception {
		out.write("修改文章：\n编号：" + id + "\n作者：" + author + "\n标题：" + title + "\n内容：" + content + "\n图片：" + image + "\n\n");
		if(modSoftware(software)) {
			out.write("修改文章成功！\n\n");
		}else{
			out.write("修改文章失败！\n\n");
		}
	}
	
	public void delTest() throws Exception {
		out.write("删除文章：\n编号：" + id + "\n作者：" + author + "\n标题：" + title + "\n内容：" + content + "\n图片：" + image + "\n\n");
		if(delSoftware(software)) {
			out.write("删除文章成功！\n\n");
		}else{
			out.write("删除文章失败！\n\n");
		}
	}
	
	public void test() throws Exception {
		response.setCharacterEncoding("gbk");
		out = response.getWriter();
		software.setsId(id);
		software.setsAuthor(author);
		software.setsTitle(title);
		software.setsContent(content);
		software.setsImage(image);
		checkTest();
		//addTest();
		modTest();
		//delTest();
		checkTest();
		out.write("\n\n");
	}
	
}