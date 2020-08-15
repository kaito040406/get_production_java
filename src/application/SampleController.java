package application;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import csvOutput.csvOutput;
import db.SqliteDB;
import dbj.SqliteDBJ;
import fileDelete.imageDelete;
import getPage.Detail;
import getPage.Scra;
import getPage.TableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.productionModel;

public class SampleController {
	@FXML private TextField urlData;

	@FXML private TextField page;

	@FXML private TextField number;

	@FXML Button button;

    @FXML
    private TableView<TableData> productiontable;
    @FXML
    private TableColumn asin;
    @FXML
    private TableColumn title;
    @FXML
    private TableColumn text;
    @FXML
    private TableColumn price;
    @FXML
    private TableColumn accessurl;
    @FXML
    private ObservableList<TableData> data;
//    private ObservableList<productionModel> data;

	@SuppressWarnings("unchecked")



	@FXML
	public void onClick(ActionEvent e) throws IOException, InterruptedException, ClassNotFoundException, SQLException {



		//前回分のデータを削除
		try {
	        SqliteDBJ.dataDelete();
	        imageDelete fileDel = new imageDelete();
			fileDel.delete();
	      } catch (SQLException | ClassNotFoundException ex) {
	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	      }

//		String url = "https://www.amazon.co.jp/s?i=fashion-mens-shoes&bbn=2016926051&rh=n%3A2016926051%2Cp_76%3A2227292051&dc&fst=as%3Aoff&qid=1596547791&ref=sr_ex_n_1";
//		int count = 1;
		String url = urlData.getText();
		int count = Integer.parseInt(page.getText());
		int getNumber = Integer.parseInt(number.getText());
		System.out.println(url);
		System.out.println(count);
//		System.exit(0);
//
//		//実行前に前回のデータを削除
		SqliteDB.dbUpdate("delete from productionTbl;");
//
//		//ページのURLを取得
		Scra pageUrls = new Scra(url, count, getNumber);
//
////		System.out.println(pageUrls.accessUrl);
//		//詳細ページを取得
		Detail purodDetail = new Detail(pageUrls.accessUrl);

		data = FXCollections.observableArrayList();
		productiontable.itemsProperty().setValue(data);
		productiontable.setItems(data);

		asin.setCellValueFactory(new PropertyValueFactory<TableData, String>("asin"));
		title.setCellValueFactory(new PropertyValueFactory<TableData, String>("name"));
		text.setCellValueFactory(new PropertyValueFactory<TableData, String>("text"));
		price.setCellValueFactory(new PropertyValueFactory<TableData, String>("price"));
		accessurl.setCellValueFactory(new PropertyValueFactory<TableData, String>("url"));
//
//		//詳細データの取得
		ObservableList<productionModel> searchDatas = SqliteDBJ.searchAllData();
		System.out.println(searchDatas.size());
		for(productionModel searchData :searchDatas) {
			System.out.println(searchData.getAsin());
//			table.getColumns().addAll(searchData.getAsin(), searchData.getAsin(), addresscolumn, memocolumn, datecolumn);
			data.addAll(new TableData(searchData.getAsin(),searchData.getName(),searchData.getMemo(),searchData.getPrice(),searchData.getUrl()));
		}
	}

	@FXML
	void nextPage(ActionEvent event) {
		try {
			settingWindow();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	void settingWindow() throws IOException, ClassNotFoundException, SQLException{
		Parent parent = FXMLLoader.load(getClass().getResource("Setting.fxml"));
		Scene scene = new Scene(parent,400,650);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void exportData(ActionEvent event) throws IOException, ClassNotFoundException, SQLException{
		csvOutput.output();
		System.out.println("出力完了");
	}

}
