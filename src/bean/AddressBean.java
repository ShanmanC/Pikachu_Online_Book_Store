package bean;

public class AddressBean {
	private int id;
	private String street;
	private String province;
	private String country;
	private String zip;
	private String phone;
	
	public AddressBean( ) { }
	
	public AddressBean(int id, String street, String province, String country, String zip, String phone) {
		super();
		setId(id);
		setStreet(street);
		setProvince(province);
		setCountry(country);
		setZip(zip);
		setPhone(phone);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public boolean equals(Object o) {
		if((o instanceof AddressBean) && ((AddressBean)o).getStreet().equals(this.getStreet())
			&& ((AddressBean)o).getProvince().equals(this.getProvince())
		&& ((AddressBean)o).getCountry().equals(this.getCountry())
		&& ((AddressBean)o).getZip().equals(this.getZip())
		&& ((AddressBean)o).getPhone().equals(this.getPhone()))		
			return true;
		else 
			return false;
	}
	

}
