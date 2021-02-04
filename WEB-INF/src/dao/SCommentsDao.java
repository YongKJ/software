
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class SCommentsDao {
	
	public List<SComments> scommentsList;
	public Connection conn;
	public Statement st;
	public PreparedStatement ps;
	public ResultSet rs;
	public String sql;
	
	public SCommentsDao() {
		conn = null;
		st = null;
		ps = null;
		rs = null;
	}
	
	public List<SComments> getSComments() throws Exception {
		sql = "select * from software_comment";
		scommentsList = new ArrayList<>();
		conn = SQLUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()){
			SComments SComments = new SComments();
			SComments.setId(rs.getInt(1));
			SComments.setSoftwareId(rs.getInt(2));
			SComments.setUserId(rs.getInt(3));
			SComments.setCommentId(rs.getInt(4));
			scommentsList.add(SComments);
		}
		SQLUtil.closeAll(conn, st, rs);
		return scommentsList;
	}
	
	public void addSComments(SComments scomments) throws Exception {
		sql = "insert into software_comment (softwareId, userId, commentId) values (?, ?, ?)";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, scomments.getSoftwareId());
		ps.setInt(2, scomments.getUserId());
		ps.setInt(3, scomments.getCommentId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void modSComments(SComments scomments) throws Exception {
		sql = "update software_comment set softwareId = ?, userId = ?, commentId = ?  where id = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, scomments.getSoftwareId());
		ps.setInt(2, scomments.getUserId());
		ps.setInt(3, scomments.getCommentId());
		ps.setInt(4, scomments.getId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void delSCommentsByCommentId(SComments scomments) throws Exception {
		sql = "delete from software_comment where commentId = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, scomments.getCommentId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
    
    public void delSCommentsByUserId(SComments scomments) throws Exception {
		sql = "delete from software_comment where userId = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, scomments.getUserId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
    
    public void delSCommentsBySoftwareId(SComments scomments) throws Exception {
		sql = "delete from software_comment where softwareId = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, scomments.getSoftwareId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
}