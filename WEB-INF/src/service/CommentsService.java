
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

public class CommentsService {
	
	public List<Comments> commentsList;
	public CommentsDao cDao;
	public Comments comment;
	public List<Users> usersList;
	public UsersService uService;
	public PrintWriter out;
	public HttpServletResponse response;
	public int id;
	public int author;
	public String email;
	public String content;
	public int display;
	
	public CommentsService(HttpServletResponse response) throws Exception {
		comment = new Comments();
		cDao = new CommentsDao();
		uService = new UsersService();
		commentsList = new ArrayList<>();
		commentsList = new ArrayList<>();
		this.response = response;
		id = 3;
		author = 1;
// 		email = "1718874198@qq.com";
// 		content = "Well done!";
// 		display = 0;
		email = "1718874198@qq.com";
		content = "Wonderful!";
		display = 0;
	}
	
	public CommentsService() {
		comment = new Comments();
		cDao = new CommentsDao();
		uService = new UsersService();
		commentsList = new ArrayList<>();
		commentsList = new ArrayList<>();
	}
	
	public List<Comments> getComments() throws Exception {
		return cDao.getComments();
	}
	
	public boolean judge_dis_exist(Comments comment) throws Exception {
		commentsList = getComments();
		usersList = uService.getUsers();
		boolean flag = false;
		for(int i = 0; i < usersList.size(); i++) {
			if(usersList.get(i).getuId() == comment.getcAuthor()) {
				flag = true;
				break;
			}
		}
		for(int i = 0; i < commentsList.size(); i++) {
			if(commentsList.get(i).getcId() == comment.getcId()) {
				return false;
			}
		}
		if(flag){
			return true;
		}
		return false;
	}
	
	public boolean judge_exist(Comments comment) throws Exception {
		commentsList = getComments();
		for(int i = 0; i < commentsList.size(); i++) {
			if(commentsList.get(i).getcId() == comment.getcId()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addComment(Comments comment) throws Exception {
		if(judge_dis_exist(comment)) {
			cDao.addComment(comment);
			return true;
		}
		return false;
	}
	
	public boolean modComment(Comments comment) throws Exception {
		if(judge_exist(comment)) {
			cDao.modComment(comment);
			return true;
		}
		return false;
	}
	
	public boolean delComment(Comments comment) throws Exception {
		if(judge_exist(comment)) {
			cDao.delComment(comment);
			return true;
		}
		return false;
	}
    
    public boolean delCommentByAuthor(Comments comment) throws Exception {
		if(judge_exist(comment)) {
			cDao.delCommentByAuthor(comment);
			return true;
		}
		return false;
	}
	
	public void checkTest() throws Exception {
		commentsList = getComments();
		for(int i = 0; i < commentsList.size(); i++){
			out.write(commentsList.get(i).getcId() + " ");
			out.write(commentsList.get(i).getcAuthor() + " ");
			out.write(commentsList.get(i).getcEmail() + " ");
			out.write(commentsList.get(i).getcDate() + " ");
			out.write(commentsList.get(i).getcContent() + " ");
			out.write(commentsList.get(i).getcDisplay() + (i == commentsList.size() - 1 ? "\n\n" : "\n"));
		}
	}
	
	public void addTest() throws Exception {
		out.write("添加新评论：\n编号：" + id + "\n评论用户：" + author + "\n用户电子邮箱：" + email + "\n评论内容：" + content + "\n是否审核：" + display + "\n\n");
		if(addComment(comment)) {
			out.write("添加评论成功！\n\n");
		}else{
			out.write("添加评论失败！\n\n");
		}
	}
	
	public void modTest() throws Exception {
		out.write("修改评论：\n编号：" + id + "\n评论用户：" + author + "\n用户电子邮箱：" + email + "\n评论内容：" + content + "\n是否审核：" + display + "\n\n");
		if(modComment(comment)) {
			out.write("修改评论成功！\n\n");
		}else{
			out.write("修改评论失败！\n\n");
		}
	}
	
	public void delTest() throws Exception {
		out.write("删除新评论：\n编号：" + id + "\n评论用户：" + author + "\n用户电子邮箱：" + email + "\n评论内容：" + content + "\n是否审核：" + display + "\n\n");
		if(delComment(comment)) {
			out.write("删除评论成功！\n\n");
		}else{
			out.write("删除评论失败！\n\n");
		}
	}
	
	public void test() throws Exception {
		response.setCharacterEncoding("gbk");
		out = response.getWriter();
		comment.setcId(id);
		comment.setcAuthor(author);
		comment.setcEmail(email);
		comment.setcContent(content);
		comment.setcDisplay(display);
		checkTest();
		addTest();
		//modTest();
		//delTest();
		checkTest();
		out.write("\n\n");
	}
	
}