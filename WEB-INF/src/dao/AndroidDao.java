
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class AndroidDao {
	
	public List<Android> androidList;
	public Connection conn;
	public Statement st;
	public PreparedStatement ps;
	public ResultSet rs;
	public String sql;
	
	public AndroidDao() {
		conn = null;
		st = null;
		ps = null;
		rs = null;
	}
	
	public List<Android> getAndroid() throws Exception {
		sql = "select * from android";
		androidList = new ArrayList<>();
		conn = SQLUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()){
			Android Android = new Android();
			Android.setId(rs.getInt(1));
			Android.setAndroid(rs.getInt(2));
			androidList.add(Android);
		}
		SQLUtil.closeAll(conn, st, rs);
		return androidList;
	}
	
	public void addAndroid(Android android) throws Exception {
		sql = "insert into android (android) values (?)";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, android.getAndroid());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void modAndroid(Android android) throws Exception {
		sql = "update android set android = ? where id = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, android.getAndroid());
		ps.setInt(2, android.getId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void delAndroid(Android android) throws Exception {
		sql = "delete from android where android = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, android.getAndroid());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
}