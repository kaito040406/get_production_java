package model;

import javafx.beans.property.SimpleStringProperty;

public class textModel {
	public final SimpleStringProperty text1;
	public final SimpleStringProperty text2;
	public final SimpleStringProperty text3;
	public textModel(String text1, String text2, String text3) {
		this.text1 = new SimpleStringProperty(text1);
		this.text2 = new SimpleStringProperty(text2);
		this.text3 = new SimpleStringProperty(text3);
	}

	public String getText1() {
		return this.text1.get();
	}
	public void setText1(String text1) {
		this.text1.set(text1);
	}
	public String getText2() {
		return this.text2.get();
	}
	public void setText2(String text2) {
		this.text1.set(text2);
	}
	public String getText3() {
		return this.text3.get();
	}
	public void setText3(String text3) {
		this.text1.set(text3);
	}

}
