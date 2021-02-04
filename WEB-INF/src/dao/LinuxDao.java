
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class LinuxDao {
	
	public List<Linux> linuxList;
	public Connection conn;
	public Statement st;
	public PreparedStatement ps;
	public ResultSet rs;
	public String sql;
	
	public LinuxDao() {
		conn = null;
		st = null;
		ps = null;
		rs = null;
	}
	
	public List<Linux> getLinux() throws Exception {
		sql = "select * from linux";
		linuxList = new ArrayList<>();
		conn = SQLUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()){
			Linux Linux = new Linux();
			Linux.setId(rs.getInt(1));
			Linux.setLinux(rs.getInt(2));
			linuxList.add(Linux);
		}
		SQLUtil.closeAll(conn, st, rs);
		return linuxList;
	}
	
	public void addLinux(Linux linux) throws Exception {
		sql = "insert into linux (linux) values (?)";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, linux.getLinux());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void modLinux(Linux linux) throws Exception {
		sql = "update linux set linux = ? where id = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, linux.getLinux());
		ps.setInt(2, linux.getId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void delLinux(Linux linux) throws Exception {
		sql = "delete from linux where linux = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, linux.getLinux());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
}