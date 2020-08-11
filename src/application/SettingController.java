package application;

import java.io.IOException;
import java.sql.SQLException;

import dbj.SqliteDBJ;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ngwordModel;

public class SettingController {
	@FXML Button load;
    @FXML
    private TableView<ngwordModel> ngwordtable;
    @FXML
    private TableColumn word;
    @FXML
    private TableColumn setting;
    @FXML
    private ObservableList<ngwordModel> ngwords;
	public void onLoad(ActionEvent e) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
		ngwords = FXCollections.observableArrayList();
		ngwordtable.itemsProperty().setValue(ngwords);
		ngwordtable.setItems(ngwords);
		word.setCellValueFactory(new PropertyValueFactory<ngwordModel, String>("word"));
		setting.setCellValueFactory(new PropertyValueFactory<ngwordModel, String>("level"));

		ObservableList<ngwordModel> ngwordDatas = SqliteDBJ.searchAllDataNg();
		for(ngwordModel ngwordData :ngwordDatas) {
			System.out.println(ngwordData.getWord());
//			table.getColumns().addAll(searchData.getAsin(), searchData.getAsin(), addresscolumn, memocolumn, datecolumn);
			ngwords.addAll(new ngwordModel(ngwordData.getWord(),ngwordData.getLevel()));
		}
	}
}
