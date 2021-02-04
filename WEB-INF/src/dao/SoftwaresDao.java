
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class SoftwaresDao {
	
	public List<Softwares> softwaresList;
	public Connection conn;
	public Statement st;
	public PreparedStatement ps;
	public ResultSet rs;
	public String sql;
	
	public SoftwaresDao() {
		conn = null;
		st = null;
		ps = null;
		rs = null;
	}
	
	public List<Softwares> getSoftwares() throws Exception {
		sql = "select * from software";
		softwaresList = new ArrayList<>();
		conn = SQLUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()){
			Softwares softwares = new Softwares();
			softwares.setsId(rs.getInt(1));
			softwares.setsAuthor(rs.getInt(2));
			softwares.setsDate(rs.getString(3));
			softwares.setsTitle(rs.getString(4));
			softwares.setsContent(rs.getString(5));
			softwares.setsImage(rs.getString(6));
			softwaresList.add(softwares);
		}
		SQLUtil.closeAll(conn, st, rs);
		return softwaresList;
	}
	
	public void addSoftware(Softwares software) throws Exception {
		sql = "insert into software (author, date, title, content, image_path) values (?, CURRENT_TIMESTAMP, ?, ?, ?)";
		conn = SQLUtil.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setInt(1, software.getsAuthor());
		ps.setString(2, software.getsTitle());
		ps.setString(3, software.getsContent());
		ps.setString(4, software.getsImage());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void modSoftware(Softwares software) throws Exception {
		sql = "update software set author = ?, date = CURRENT_TIMESTAMP, title = ?, content = ?, image_path = ?  where id = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, software.getsAuthor());
		ps.setString(2, software.getsTitle());
		ps.setString(3, software.getsContent());
		ps.setString(4, software.getsImage());
		ps.setInt(5, software.getsId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
	public void delSoftware(Softwares software) throws Exception {
		sql = "delete from software where id = ?";
		conn = SQLUtil.getConnection();
		ps  = conn.prepareStatement(sql);
		ps.setInt(1, software.getsId());
		ps.executeUpdate();
		SQLUtil.closeAll(conn, ps);
	}
	
}