
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class WindowsDao {
	
	public List<Windows> windowsList;
	public Connection conn;
	public Statement st;
	public PreparedStatement ps;
	public ResultSet rs;
	public String sql;
	
	public WindowsDao() {
		conn = null;
		st = null;
		ps = null;
		rs = null;
	}
	
	public List<Windows> getWindows() throws Exception {
		sql = "select * from windows";
		windowsList = new ArrayList<>();
		conn = SQLUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()){
			Windows windows = new Windows();
			windows.setId(rs.getInt(1));
			windows.setWindows(rs.getInt(2));
			windowsList.add(windows);
		}
		SQLUtil.closeAll(conn, st, rs);
		return windowsList;
	}
	
	public void addWindows(Windows windows) throws Exception {
		sql = "insert into windows (windows) values (?)";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, windows.getWindows());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void modWindows(Windows windows) throws Exception {
		sql = "update windows set windows = ? where id = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, windows.getWindows());
		ps.setInt(2, windows.getId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void delWindows(Windows windows) throws Exception {
		sql = "delete from windows where windows = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, windows.getWindows());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
}