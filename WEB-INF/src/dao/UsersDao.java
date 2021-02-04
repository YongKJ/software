
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {
	
	public List<Users> userList;
	public Connection conn;
	public Statement st;
	public PreparedStatement ps;
	public ResultSet rs;
	public String sql;
	
	public UsersDao() {
		conn = null;
		st = null;
		ps = null;
		rs = null;
	}
	
	public List<Users> getUsers() throws Exception {
		sql = "select * from user";
		userList = new ArrayList<>();
		conn = SQLUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()){
			Users users = new Users();
			users.setuId(rs.getInt(1));
			users.setuName(rs.getString(2));
			users.setuPasswd(rs.getString(3));
			users.setregistrationDate(rs.getString(4));
			userList.add(users);
		}
		SQLUtil.closeAll(conn, st, rs);
		return userList;
	}
	
	public void addUser(Users user) throws Exception {
		sql = "insert into user (username, password, registration_date) values (?, ?, CURRENT_TIMESTAMP)";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setString(1, user.getuName());
		ps.setString(2, user.getuPasswd());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void modUser(Users user) throws Exception {
		sql = "update user set username = ?, password = ?, registration_date = CURRENT_TIMESTAMP where id = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setString(1, user.getuName());
		ps.setString(2, user.getuPasswd());
		ps.setInt(3, user.getuId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void delUser(Users user) throws Exception {
		sql = "delete from user where id = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, user.getuId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
}