package bean;

public class UserInfoBean {

	private String uid;
	private int aid;
	private String fname;
	private String lname;
	private String email;
	private String password;
	private String userType;
	
	public UserInfoBean() {}
	
	public UserInfoBean(String uid, int aid, String fname, String lname, String email, String password, String userType) {
		super();
		setUid(uid);
		setAid(aid);
		setFname(fname);
		setLname(lname);
		setEmail(email);
		setPassword(password);
		setUserType(userType);
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
