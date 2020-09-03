package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dbj.SqliteDBJ;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.ngwordModel;

public class SettingController {
	@FXML Button load;
	@FXML Button price;
    @FXML
    private TableView<ngwordModel> ngwordtable;
    @FXML
    private TableColumn word;
    @FXML
    private TableColumn setting;
    @FXML
    private TableColumn check;
    @FXML
    private ObservableList<ngwordModel> ngwords;
    @FXML private TextField addWord;
    @FXML private ComboBox<String> combo;
    @FXML private TextField delWord;
    @FXML private TextField price1000;

	public void onLoad(ActionEvent e) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		Logger logger = Logger.getLogger("GB");
		logger.log(Level.INFO,"設定ページの表示");
		ngwords = FXCollections.observableArrayList();
		ngwordtable.itemsProperty().setValue(ngwords);
		ngwordtable.setItems(ngwords);
		ngwordtable.setEditable(true);
		word.setEditable(true);

		setting.setCellFactory(ChoiceBoxTableCell.forTableColumn("タイトル判定削除", "テキスト判定削除", "一文だけ削除"));
		word.setCellFactory(TextFieldTableCell.forTableColumn());
		check.setCellValueFactory(new PropertyValueFactory<ngwordModel, String>("id"));
		word.setCellValueFactory(new PropertyValueFactory<ngwordModel, String>("word"));
		setting.setCellValueFactory(new PropertyValueFactory<ngwordModel, String>("level"));



		ObservableList<ngwordModel> ngwordDatas = SqliteDBJ.searchAllDataNg();
		String levelWord = "";
		for(ngwordModel ngwordData :ngwordDatas) {
//			table.getColumns().addAll(searchData.getAsin(), searchData.getAsin(), addresscolumn, memocolumn, datecolumn);
			if(ngwordData.getLevel().equals("0")) {
				levelWord = "タイトル判定削除";
			}else if(ngwordData.getLevel().equals("1")) {
				levelWord = "テキスト判定削除";
			}else {
				levelWord = "一文だけ削除";
			}
			ngwords.addAll(new ngwordModel(ngwordData.getId(),ngwordData.getWord(),levelWord));
		}
	}
	public void add(ActionEvent e) throws ClassNotFoundException, SQLException {
		Logger logger = Logger.getLogger("GB");
		
		String word = addWord.getText();
//		System.out.println(word);
		String setting = combo.getValue();
//		System.out.println(setting);
		String settingId = "";
		if(setting != null && !word.equals("")) {
			if(setting.equals("タイトル判定削除")) {
				settingId = "0";
			}else if(setting.equals("テキスト判定削除")) {
				settingId = "1";
			}else if(setting.equals("一文だけ削除")) {
				settingId = "2";
			}else {
				settingId = "1";
			}
			SqliteDBJ.insertDataNg(word,settingId);
			logger.log(Level.INFO,"保存成功");
		}
	}
	public void del(ActionEvent e) throws ClassNotFoundException, SQLException {
		Logger logger = Logger.getLogger("GB");
		String regex_num = "^[0-9]+$";
		Pattern p1 = Pattern.compile(regex_num);
		Matcher m1 = p1.matcher(delWord.getText());
		boolean result = m1.matches();
		if(!delWord.getText().equals("") && result) {
			int delId = Integer.parseInt(delWord.getText());
			SqliteDBJ.deleteDataNg(delId);
			logger.log(Level.INFO,"削除成功");
		}
	}
	@FXML
	public void price(ActionEvent e) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("Price.fxml"));
		Scene scene = new Scene(parent,370,650);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
}
