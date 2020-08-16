package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ngwordModel {
	public final SimpleIntegerProperty id;
	public final SimpleStringProperty word;
	public final SimpleStringProperty level;

	public ngwordModel(Integer id, String word, String level) {
		this.id = new SimpleIntegerProperty(id);
		this.word = new SimpleStringProperty(word);
		this.level = new SimpleStringProperty(level);
	}
	public int getId() {
		return this.id.get();
	}
	public void setId(int id) {
		this.id.set(id);
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
