package getPage;

public class TableData {
	private String asin;
	private String name;
	private String text;
	private String price;
	private String url;

	public TableData(String asin, String name, String text, String price, String url) {
		this.asin = asin;
		this.name = name;
		this.text = text;
		this.price = price;
		this.url = url;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getName() {
		return name;
	}

	public void setNamea(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
