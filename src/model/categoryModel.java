package model;

import javafx.beans.property.SimpleStringProperty;

public class categoryModel {
	public final SimpleStringProperty categoryId;
	public final SimpleStringProperty category;
	
	public categoryModel(String categoryId, String category) {
		this.categoryId = new SimpleStringProperty(categoryId);
		this.category = new SimpleStringProperty(category);
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
}
