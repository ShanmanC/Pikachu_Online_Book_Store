package bean;

public class BookBean {

	private String bid;
	private String title;
	private int price;
	private String category;
	private String artwork_url;
	
	
	public BookBean () {}
	
	public BookBean(String bid, String title, int price, String category, String artwork_url) {
		super();
		this.bid = bid;
		this.title = title;
		this.price = price;
		this.category = category;
		this.artwork_url = artwork_url;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getArtwork_url() {
		return artwork_url;
	}

	public void setArtwork_url(String artwork_url) {
		this.artwork_url = artwork_url;
	}

	@Override
	public boolean equals(Object o) {
		if((o instanceof BookBean) && ((BookBean)o).getBid().equals(this.getBid()))
			return true;
		else 
			return false;
	}
	
	@Override
	public int hashCode(){
		String id = bid.substring(1);
		return Integer.parseInt(id);
	}
	
}
