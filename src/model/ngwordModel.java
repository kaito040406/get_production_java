package model;

import javafx.beans.property.SimpleStringProperty;

public class ngwordModel {
	public final SimpleStringProperty word;
	public final SimpleStringProperty level;
	
	public ngwordModel(String word, String level) {
		this.word = new SimpleStringProperty(word);
		this.level = new SimpleStringProperty(level);
	}
	public String getWord() {
		return this.word.get();
	}
	public void setWord(String word) {
		this.word.set(word);
	}
	public String getLevel() {
		return this.level.get();
	}
	public void setLevel(String level) {
		this.level.set(level);
	}
}
