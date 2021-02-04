
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CommentsDao {
	
	public List<Comments> commentsList;
	public Connection conn;
	public Statement st;
	public PreparedStatement ps;
	public ResultSet rs;
	public String sql;
	
	public CommentsDao() {
		conn = null;
		st = null;
		ps = null;
		rs = null;
	}
	
	public List<Comments> getComments() throws Exception {
		sql = "select * from comments";
		commentsList = new ArrayList<>();
		conn = SQLUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()){
			Comments comments = new Comments();
			comments.setcId(rs.getInt(1));
			comments.setcAuthor(rs.getInt(2));
			comments.setcEmail(rs.getString(3));
			comments.setcDate(rs.getString(4));
			comments.setcContent(rs.getString(5));
			comments.setcDisplay(rs.getInt(6));
			commentsList.add(comments);
		}
		SQLUtil.closeAll(conn, st, rs);
		return commentsList;
	}
	
	public void addComment(Comments comment) throws Exception {
		sql = "insert into comments (author, email, date, content, display) values (?, ?, CURRENT_TIMESTAMP, ?, ?)";
		conn = SQLUtil.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setInt(1, comment.getcAuthor());
		ps.setString(2, comment.getcEmail());
		ps.setString(3, comment.getcContent());
		ps.setInt(4, comment.getcDisplay());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void modComment(Comments comment) throws Exception {
		sql = "update comments set author = ?, email = ?, date = CURRENT_TIMESTAMP, content = ?, display = ? where id = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, comment.getcAuthor());
		ps.setString(2, comment.getcEmail());
		ps.setString(3, comment.getcContent());
		ps.setInt(4, comment.getcDisplay());
		ps.setInt(5, comment.getcId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void delComment(Comments comment) throws Exception {
		sql = "delete from comments where id = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, comment.getcId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
    
    public void delCommentByAuthor(Comments comment) throws Exception {
		sql = "delete from comments where author = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, comment.getcAuthor());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
}