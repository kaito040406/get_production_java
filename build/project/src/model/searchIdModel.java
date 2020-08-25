package model;

import javafx.beans.property.SimpleStringProperty;

public class searchIdModel {
	public final SimpleStringProperty amazonCategory;
	public final SimpleStringProperty yahooCategory;
	public final SimpleStringProperty categoryId;
	public searchIdModel(String amazonCategory, String yahooCategory, String categoryId) {
		this.amazonCategory = new SimpleStringProperty(amazonCategory);
		this.yahooCategory = new SimpleStringProperty(yahooCategory);
		this.categoryId = new SimpleStringProperty(categoryId);
	}

	public String getAmazonCategory() {
		return this.amazonCategory.get();
	}

	public void setAmazonCategory(String amazonCategory) {
		this.amazonCategory.set(amazonCategory);
	}

	public String getYahooCategory() {
		return this.yahooCategory.get();
	}

	public void setYahooCategory(String yahooCategory) {
		this.yahooCategory.set(yahooCategory);
	}

	public String getCategoryId() {
		return this.categoryId.get();
	}

	public void setCategoryId(String categoryId) {
		this.categoryId.set(categoryId);
	}
}
