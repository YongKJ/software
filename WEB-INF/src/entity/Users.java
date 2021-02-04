
public class Users {
	
	private int uId;
	private String uName;
	private String uPasswd;
	private String registrationDate;
	
	public int getuId(){
		return uId;
	}
	
	public void setuId(int uId){
		this.uId = uId;
	}
	
	public String getuName(){
		return uName;
	}
	
	public void setuName(String uName){
		this.uName = uName;
	}
	
	public String getuPasswd(){
		return uPasswd;
	}
	
	public void setuPasswd(String uPasswd){
		this.uPasswd = uPasswd;
	}
	
	public String getregistrationDate(){
		return registrationDate;
	}
	
	public void setregistrationDate(String registrationDate){
		this.registrationDate = registrationDate;
	}
	
}