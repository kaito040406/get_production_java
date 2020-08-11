package model;

import javafx.beans.property.SimpleStringProperty;

public class categoryModel {
	public final SimpleStringProperty categoryId;
	public final SimpleStringProperty category;
	public final SimpleStringProperty searchId;

	public categoryModel(String categoryId, String category, String searchId) {
		this.categoryId = new SimpleStringProperty(categoryId);
		this.category = new SimpleStringProperty(category);
		this.searchId = new SimpleStringProperty(searchId);
	}
	public String getCategoryId() {
		return this.categoryId.get();
	}
	public void setCategoryId(String categoryId) {
		this.categoryId.set(categoryId);
	}
	public String getCategory() {
		return this.category.get();
	}
	public void setCategory(String category) {
		this.category.set(category);
	}
	public String getSearchId() {
		return this.searchId.get();
	}
	public void setSearchId(String searchId) {
		this.searchId.set(searchId);
	}
}
