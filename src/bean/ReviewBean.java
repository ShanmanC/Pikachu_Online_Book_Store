package bean;

public class ReviewBean {
	private String uid;
	private String bid;
	private int rating;
	private String content;
	
	public ReviewBean() {}
	public ReviewBean(String uid, String bid, int rating, String content) {
		super();
		setUid(uid);
		setBid(bid);
		setRating(rating);
		setContent(content);
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	

}
