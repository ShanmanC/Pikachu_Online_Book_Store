package bean;

public class StatisticsBean {
	private String uid;
	private String zip;
	private int spent;
	public StatisticsBean() {
		
	}
	
	public StatisticsBean(String uid, String zip, int spent) {
		setUid(uid);
		setZip(zip);
		setSpent(spent);
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public int getSpent() {
		return spent;
	}

	public void setSpent(int spent) {
		this.spent = spent;
	}

	
	
	

}
