
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

public class UsersService {
	
	public UsersDao uDao;
	public List<Users> usersList;
	public Users user;
    public List<Softwares> softwaresList;
    public SoftwaresService sService;
    public List<Comments> commentsList;
    public CommentsService cService;
	public PrintWriter out;
	public HttpServletResponse response;
	public int id;
	public String name;
	public String passwd;
	
	public UsersService(HttpServletResponse response) throws Exception {
		usersList = new ArrayList<>();
		this.response = response;
		uDao = new UsersDao();
		user = new Users();
        softwaresList = new ArrayList<>();
        sService = new SoftwaresService();
        commentsList = new ArrayList<>();
        cService = new CommentsService();
		id = 41;
		name = "dxj";
		passwd = "654321";
	}
	
	public UsersService() {
		usersList = new ArrayList<>();
		uDao = new UsersDao();
		user = new Users();
	}
	
	public List<Users> getUsers() throws Exception {
		return uDao.getUsers();
	}
	
	public boolean judge_dis_exist(Users user) throws Exception {
// 		usersList = getUsers();
// 		for(int i = 0; i < usersList.size(); i++) {
// 			if(usersList.get(i).getuId() == user.getuId()) {
// 				return false;
// 			}
// 		}
		return true;
	}
	
	public boolean judge_exist(Users user) throws Exception {
		for(int i = 0; i < usersList.size(); i++) {
			if(usersList.get(i).getuId() == user.getuId()) {
				return true;
			}
		}
		return false;
	}
    
    public boolean judge_del_exist(Users user) throws Exception {
        softwaresList = sService.getSoftwares();
        commentsList = cService.getComments();
        boolean flag1 = true;
        boolean flag2 = true;
        for(int i = 0; i < softwaresList.size(); i++) {
            if(softwaresList.get(i).getsAuthor() == user.getuId()){
                flag1 = false;
                break;
            }
        }
        for(int i = 0; i < commentsList.size(); i++) {
            if(commentsList.get(i).getcAuthor() == user.getuId()){
                flag2 = false;
                break;
            }
        }
		for(int i = 0; i < usersList.size(); i++) {
			if(usersList.get(i).getuId() == user.getuId() && flag1 && flag2) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addUser(Users user) throws Exception {
		if(judge_dis_exist(user)) {
			uDao.addUser(user);
			return true;
		}
		return false;
	}
	
	public boolean modUser(Users user) throws Exception {
// 		if(judge_exist(user)) {
		uDao.modUser(user);
// 			return true;
// 		}
		return true;
	}
	
	public boolean delUser(Users user) throws Exception {
// 		if(judge_del_exist(user)) {
		uDao.delUser(user);
// 			return true;
// 		}
		return true;
	}
	
	public void checkTest() throws Exception {
		usersList = getUsers();
		for(int i = 0; i < usersList.size(); i++){
			out.write(usersList.get(i).getuId() + " ");
			out.write(usersList.get(i).getuName() + " ");
			out.write(usersList.get(i).getuPasswd() + " ");
			out.write(usersList.get(i).getregistrationDate() + (i == usersList.size() - 1 ? "\n\n" : "\n"));
		}
	}
	
	public void addTest() throws Exception {
		out.write("添加新用户：\n编号：" + id + "\n用户名：" + name + "\n密码：" + passwd + "\n\n");
		if(addUser(user)) {
			out.write("添加用户成功！\n\n");
		}else{
			out.write("添加用户失败，用户已存在！\n\n");
		}
	}
	
	public void modTest() throws Exception {
		out.write("修改用户：\n编号：" + id + "\n用户名：" + name + "\n密码：" + passwd + "\n\n");
		if(modUser(user)) {
			out.write("修改用户成功！\n\n");
		}else{
			out.write("修改用户失败，用户不存在！\n\n");
		}
	}
	
	public void delTest() throws Exception {
		out.write("删除用户：\n编号：" + id + "\n用户名：" + name + "\n密码：" + passwd + "\n\n");
		if(delUser(user)) {
			out.write("删除用户成功！\n\n");
		}else{
			out.write("删除用户失败！\n\n");
		}
	}
	
	public void test() throws Exception {
		response.setCharacterEncoding("gbk");
		out = response.getWriter();
		user.setuId(id);
		user.setuName(name);
		user.setuPasswd(passwd);
		checkTest();
// 		addTest();
// 		modTest();
// 		delTest();
// 		checkTest();
		out.write("\n\n");
	}
	
}