package attributes;

public abstract class Item {
	
	private String id;
	private String name;
	private String type;
	private int price;
	public Item(String id,String name, String type, int price) {
		this.setId(id);
		this.setName(name);
		this.setType(type);
		this.setPrice(price);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
