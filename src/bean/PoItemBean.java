package bean;

public class PoItemBean {

	private int id;
	private String bid;
	private int price;
	private int quantity;
	
	public PoItemBean() {}
	
	public PoItemBean(int id, String bid, int price, int quantity) {
		super();
		this.id = id;
		this.bid = bid;
		this.price = price;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
}

