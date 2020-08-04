package model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class productionModel {
	public final SimpleIntegerProperty id;
	public final SimpleStringProperty asin;
	public final SimpleStringProperty name;
	public final SimpleStringProperty memo;
	public final SimpleStringProperty url;
	public final SimpleStringProperty price;
	public final SimpleStringProperty category;
	public final SimpleStringProperty date;

	public productionModel(int id, String asin, String name, String memo, String url, String price, String category, String date) {
		this.id = new SimpleIntegerProperty(id);
		this.asin = new SimpleStringProperty(asin);
		this.name = new SimpleStringProperty(name);
		this.memo = new SimpleStringProperty(memo);
		this.url = new SimpleStringProperty(url);
		this.price = new SimpleStringProperty(price);
		this.category = new SimpleStringProperty(category);
		this.date = new SimpleStringProperty(date);
	}

	public int getId() {
		return this.id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public String getAsin() {
		return this.asin.get();
	}
	public void setAsin(String asin) {
		this.asin.set(asin);
	}
	public String getName() {
		return this.name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public String getUrl() {
		return this.url.get();
	}
	public void setUrl(String url) {
		this.url.set(url);
	}
	public String getMemo() {
		return this.memo.get();
	}
	public void setMemo(String memo) {
		this.memo.set(memo);
	}
	public String getPrice() {
		return this.price.get();
	}
	public void setPrice(String price) {
		this.price.set(price);
	}
	public String getCategory() {
		return this.category.get();
	}
	public void setCategory(String category) {
		this.category.set(category);
	}
	public String getDate() {
		return this.date.get();
	}
	public void setDate(String date) {
		this.date.set(date);
	}


}
