package application;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import db.SqliteDB;
import dbj.SqliteDBJ;
import getPage.Detail;
import getPage.Scra;
import getPage.TableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.productionModel;

public class SampleController {
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

    private ObservableList<TableData> data;
//    private ObservableList<productionModel> data;

	@SuppressWarnings("unchecked")
	@FXML
	public void onClick(ActionEvent e) throws IOException, InterruptedException, ClassNotFoundException, SQLException {




		try {
	        SqliteDBJ.dataDelete();
	      } catch (SQLException | ClassNotFoundException ex) {
	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	      }




		String url = "https://www.amazon.co.jp/s?i=fashion-mens-shoes&bbn=2016926051&rh=n%3A2016926051%2Cp_76%3A2227292051&dc&fst=as%3Aoff&qid=1596547791&ref=sr_ex_n_1";
		int count = 1;
//
//		//実行前に前回のデータを削除
		SqliteDB.dbUpdate("delete from productionTbl;");
//
//		//ページのURLを取得
		Scra pageUrls = new Scra(url, count);
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

}
